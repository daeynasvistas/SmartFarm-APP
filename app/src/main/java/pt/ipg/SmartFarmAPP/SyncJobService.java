package pt.ipg.SmartFarmAPP;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.util.Log;

import pt.ipg.SmartFarmAPP.Service.API.OracleDbToRoomDataUpdateTask;
import pt.ipg.SmartFarmAPP.Service.API.Tools.API;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;


public class SyncJobService extends JobService {
    private static final String TAG = "JobService";
    private boolean jobCancelled = false;
    private NodeViewModel nodeViewModel;

    @Override
    public boolean onStartJob(JobParameters params) {
        Context context = this;
        Log.d(TAG, "Job started");
        doBackgroundWork(params,context);

        return true;
    }

    private void doBackgroundWork(final JobParameters params, final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OracleDbToRoomDataUpdateTask dbUpdateTask = new OracleDbToRoomDataUpdateTask();
                    dbUpdateTask.getNodesFromOracleUpdateLocalDb(context);
                } finally {
                    Log.d(TAG, "Job finished");
                    jobFinished(params, true);
                }

            }
        }).start();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
