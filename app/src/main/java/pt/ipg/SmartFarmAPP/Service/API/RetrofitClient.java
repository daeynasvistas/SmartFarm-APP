package pt.ipg.SmartFarmAPP.Service.API;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    // --- assinar HMAC ----- add header stuff


    static OkHttpClient okHttpClient = API.UnsafeOkHttpClient.getUnsafeOkHttpClient(); // não utilizar emprodução, para ultrapassar TLS problema

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)  /// devido ao problema HTTPS IPG
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}