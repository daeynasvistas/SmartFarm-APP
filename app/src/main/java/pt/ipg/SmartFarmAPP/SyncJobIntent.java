package pt.ipg.SmartFarmAPP;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import pt.ipg.SmartFarmAPP.Service.API.OracleDbToRoomDataUpdateTask;

public class SyncJobIntent extends JobIntentService {
    private static final String TAG = "SmartFJobIntentService";


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
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, input + " - " + i);
            if (isStopped()) return;
            SystemClock.sleep(1000);
        }
        // cenas ..... e mais cenas ...

        OracleDbToRoomDataUpdateTask dbUpdateTask = new OracleDbToRoomDataUpdateTask();
        dbUpdateTask.getNodesFromOracleUpdateLocalDb();
        if (isStopped()) return;


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