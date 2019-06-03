package pt.ipg.SmartFarmAPP.Service.API.Tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedOutput;


public class SigningClient implements Client {
    private static HttpClient createDefaultClient() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 1000);
        HttpConnectionParams.setSoTimeout(params,1000);
        return new DefaultHttpClient(params);
    }

    private final HttpClient client;

    public SigningClient() {
        this(createDefaultClient());
    }

    public SigningClient(HttpClient client) {
        this.client = client;
    }

    @Override public Response execute(Request request) throws IOException {
        HttpUriRequest apacheRequest = createRequest(request);
        HttpResponse apacheResponse = execute(client, apacheRequest);
        return parseResponse(request.getUrl(), apacheResponse);
    }

    /** Execute the specified {@code request} using the provided {@code client}. */
    protected HttpResponse execute(HttpClient client, HttpUriRequest request) throws IOException {
        return client.execute(request);
    }

    static HttpUriRequest createRequest(Request request) {
        if (request.getBody() != null) {
            //post with body
            return new GenericEntityHttpRequest(request);
        }
        //get or other method without body
        return new GenericHttpRequest(request);
    }

    static Response parseResponse(String url, HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        int status = statusLine.getStatusCode();
        String reason = statusLine.getReasonPhrase();

        List<Header> headers = new ArrayList<Header>();
        String contentType = "application/octet-stream";
        for (org.apache.http.Header header : response.getAllHeaders()) {
            String name = header.getName();
            String value = header.getValue();
            if ("Content-Type".equalsIgnoreCase(name)) {
                contentType = value;
            }
            headers.add(new Header(name, value));
        }

        TypedByteArray body = null;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            byte[] bytes = EntityUtils.toByteArray(entity);
            body = new TypedByteArray(contentType, bytes);
        }

        return new Response(url, status, reason, headers, body);
    }

    private static class GenericHttpRequest extends HttpRequestBase {
        private final String method;

        public GenericHttpRequest(Request request) {
            method = request.getMethod();
            setURI(URI.create(request.getUrl()));

            // Add all headers.
            for (Header header : request.getHeaders()) {
                addHeader(new BasicHeader(header.getName(), header.getValue()));
            }

        }

        @Override public String getMethod() {
            return method;
        }
    }

    private static class GenericEntityHttpRequest extends HttpEntityEnclosingRequestBase {
        private final String method;

        GenericEntityHttpRequest(Request request) {
            super();
            method = request.getMethod();
            setURI(URI.create(request.getUrl()));

            // Add all headers.
            for (Header header : request.getHeaders()) {
                addHeader(new BasicHeader(header.getName(), header.getValue()));
            }

            TypedOutputEntity bodyContent = new TypedOutputEntity(request.getBody());
            // Add the content body.
            setEntity(bodyContent);


            byte[] bodyBytes;
            String checksum = "";
            InputStream is = null;
            //first we read the body and convert it into bytearray
            try {
                is = bodyContent.getContent();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int nRead;
                byte[] data = new byte[16384];

                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                buffer.flush();
                bodyBytes = buffer.toByteArray();

                // after we have our bytearray we can generate the cheksum
                checksum = HMAC.hmacDigest(bodyBytes,"PRIVATE KEY onde guardar","HmacSHA256");
            } catch (IOException e) {
                e.printStackTrace();
            }


            //here we create the cheksum header
            // Note even though we add it and it is confirmed we do it does not show up in the default retrofit debugging
            BasicHeader header = new BasicHeader("x-api-secret",""+checksum);
            addHeader(header);
        }

        @Override public String getMethod() {
            return method;
        }
    }


    /** Container class for passing an entire {@link TypedOutput} as an {@link HttpEntity}. */
    static class TypedOutputEntity extends AbstractHttpEntity {
        final TypedOutput typedOutput;
        ByteArrayOutputStream out;

        TypedOutputEntity(TypedOutput typedOutput) {
            this.typedOutput = typedOutput;
            setContentType(typedOutput.mimeType());
        }


        @Override public boolean isRepeatable() {
            return true;
        }

        @Override public long getContentLength() {
            return typedOutput.length();
        }

        @Override public InputStream getContent() throws IOException {
            out = new ByteArrayOutputStream();
            typedOutput.writeTo(out);
            return new ByteArrayInputStream(out.toByteArray());
        }

        @Override public void writeTo(OutputStream out) throws IOException {
            typedOutput.writeTo(out);
        }

        @Override public boolean isStreaming() {
            return false;
        }
    }
}