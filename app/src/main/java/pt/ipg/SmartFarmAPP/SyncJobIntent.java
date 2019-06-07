package pt.ipg.SmartFarmAPP;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.view.View;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Service.API.API;
import pt.ipg.SmartFarmAPP.Service.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Service.API.OracleDbToRoomDataUpdateTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncJobIntent extends JobIntentService {
    private static final String TAG = "SmartFJobIntentService";
    private JsonOracleAPI mAPIService;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SyncJobIntent.class, 1001, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork");
        String input = intent.getStringExtra("inputExtra");

        // cenas ..... e mais cenas ... Get API aqui
        for (int i = 0; i < 2; i++) {
            Log.d(TAG, input + " - " + i);
            if (isStopped()) return;
            SystemClock.sleep(1000);
        }
        // cenas ..... e mais cenas ...
        mAPIService = API.getAPIService();
        mAPIService.postNode(1,"ESP32-LORA (Android)","0.1","1AA11110123","192.168.000.000", 40.451245f, -7.1254545, 1000)
            .enqueue(new Callback<Node>() {
                public void onResponse(Call<Node> call, Response<Node> response) {
                    if(response.isSuccessful()) {
                        showResponse(response.body().toString());
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                    }
                }
                public void onFailure(Call<Node> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                }
        });
    }


    public void showResponse(String response) {
            // Mostrar cenas resposta
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
    }
}