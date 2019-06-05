package pt.ipg.SmartFarmAPP;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.NodeRepository;
import pt.ipg.SmartFarmAPP.Service.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Service.API.OracleDbToRoomDataUpdateTask;
import pt.ipg.SmartFarmAPP.Service.API.Tools.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SyncJobService extends JobService {
    private static final String TAG = "JobService";
    private boolean jobCancelled = false;
    public static final String URL = "https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/";



    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(jobCancelled){return; }

                try {
                    Log.d("ORACLE", "--- DOWNLOAD JSON ---");
                    OkHttpClient okHttpClient = API.UnsafeOkHttpClient.getUnsafeOkHttpClient();
                    // problema com IPG certificado .. não utilizar produção!!
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
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
                            // já tenho update AQUI !!!
                            List<Node> nodes = response.body().items;
                           // taskExecutor.execute(new OracleDbToRoomDataUpdateTask.RoomUpdateTask(nodes));
                            // singleton  .. em teoria só há uma instance .. mesmo que com novo builder
                            NodeRepository nodeRepository = NodeRepository.newInstance();

                            nodeRepository.deleteAllNodes(); // APAGAR TUDO  <---------------------------------------- REVER 0.5
                            for (Node node : nodes) {
                                nodeRepository.insert(node);
                            }
                            // database updated!!!!!

                            // cancelar JOB
                            // só quero fazer isto uma vez -- se ok cancelar
                            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                            scheduler.cancel(1000);
                            Log.d("ROOM", "local database update complete, total Nodes: "+nodes.size());

                            /* REMOVER método directamente na BD melhor sempre repositório
                            db = Room.databaseBuilder(ctx,
                                    AppDatabase.class, "node_database").build();
                            //insert new nodes
                            db.nodeDao().deleteAllNodes();
                            db.nodeDao().insertNodes(nodes);
                            */
                        }

                        @Override
                        public void onFailure(Call<Node.MyNodes> call, Throwable t) {
                            Log.d("ORACLE", " Montes problemas ",
                                    t.getCause());
                        }

                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Job finished");
                jobFinished(params, true);  // true se a operação foi cancelada por algum motivo!!!!
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
