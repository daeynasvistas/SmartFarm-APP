package pt.ipg.SmartFarmAPP.Service.API;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.Database.AppDatabase;
import pt.ipg.SmartFarmAPP.Entity.NodeDao;
import pt.ipg.SmartFarmAPP.Entity.NodeRepository;
import pt.ipg.SmartFarmAPP.MainActivity;
import pt.ipg.SmartFarmAPP.Service.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Model.NodeModel;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class API {

    private API() {}

    public static final String BASE_URL = "https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/";
    public static JsonOracleAPI getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(JsonOracleAPI.class);
    }











    public static void getOracleAPI(final TextView text, final ProgressBar progressbar) {


        // problema con certificado IPG
        // https://futurestud.io/tutorials/retrofit-2-how-to-trust-unsafe-ssl-certificates-self-signed-expired
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        // problema com IPG certificado .. não utilizar produção!!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonOracleAPI jsonOracleAPI = retrofit.create(JsonOracleAPI.class);

        Call<NodeModel.MyNodes> call = jsonOracleAPI.getNodesModel();
        call.enqueue(new Callback<NodeModel.MyNodes>() {

            @Override
            public void onResponse(Call<NodeModel.MyNodes> call, Response<NodeModel.MyNodes> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                }

                //Value.MyValues values = response.body();

               List<NodeModel> nodes = response.body().items;
                for (NodeModel node : nodes) {
                    String content = "";
                    content += "ID: " + node.getId() + "\n";
                    content += "iot_person_id: " + node.getIot_person_id() + "\n";

                    content += "Model: " + node.getModel() + "\n";
                    content += "firm_vers: " + node.getFirm_vers() + "\n";
                    content += "mac: " + node.getMac() + "\n";
                    content += "longitude: " + node.getLongitude() + "\n";
                    content += "latitude: " + node.getLatitude() + "\n";
                    content += "altitude: " + node.getAltitude() + "\n";
                    content += "IP: " + node.getIp() + "\n";
                    content += "\n";

                    text.append(content);
                }

                progressbar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<NodeModel.MyNodes> call, Throwable t) {
                text.setText(t.getMessage());
                progressbar.setVisibility(GONE);
            }
        });

    }




    public static void syncOracleAPI(final NodeViewModel nodeViewModel, final ProgressBar progressbar) {
        // problema con certificado IPG
        // https://futurestud.io/tutorials/retrofit-2-how-to-trust-unsafe-ssl-certificates-self-signed-expired
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        // problema com IPG certificado .. não utilizar produção!!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonOracleAPI jsonOracleAPI = retrofit.create(JsonOracleAPI.class);

        Call<Node.MyNodes> call = jsonOracleAPI.getNodes();
        call.enqueue(new Callback<Node.MyNodes>() {

            @Override
            public void onResponse(Call<Node.MyNodes> call, Response<Node.MyNodes> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<Node> nodes = response.body().items;
                nodeViewModel.deleteAllNodes();
                for (Node node : nodes) {
                    nodeViewModel.insert(node);
                }
                progressbar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<Node.MyNodes> call, Throwable t) {
                progressbar.setVisibility(GONE);
            }
        });

    }

    public static void syncJobOracleAPI(final NodeViewModel nodeViewModel) {
        // problema con certificado IPG
        // https://futurestud.io/tutorials/retrofit-2-how-to-trust-unsafe-ssl-certificates-self-signed-expired
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        // problema com IPG certificado .. não utilizar produção!!

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonOracleAPI jsonOracleAPI = retrofit.create(JsonOracleAPI.class);

        Call<Node.MyNodes> call = jsonOracleAPI.getNodes();
        call.enqueue(new Callback<Node.MyNodes>() {

            @Override
            public void onResponse(Call<Node.MyNodes> call, Response<Node.MyNodes> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<Node> nodes = response.body().items;
                nodeViewModel.deleteAllNodes();
                for (Node node : nodes) {
                    nodeViewModel.insert(node);
                }
              //  progressbar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<Node.MyNodes> call, Throwable t) {
              //  progressbar.setVisibility(GONE);
            }
        });

    }



    // problema com certificado IPG
    public static class UnsafeOkHttpClient {
        public static OkHttpClient getUnsafeOkHttpClient() {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient okHttpClient = builder.build();
                return okHttpClient;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}



