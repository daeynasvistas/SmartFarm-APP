package pt.ipg.SmartFarmAPP;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.Repository;
import pt.ipg.SmartFarmAPP.Entity.SensorData;
import pt.ipg.SmartFarmAPP.Service.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Service.API.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SyncJobService extends JobService {
    private static final String TAG = "JobService";
    private boolean jobCancelled = false;
    public static final String URL = "https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/";

    public static final String SHARED_PREFS = "sharePrefs";
    public static final String LAST_DATE_UPDATE_SENSOR_VALUES = "LastDate";

    int timestamp;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        doSyncOracleBackgroundWork(params);

        return true;
    }


    private void doSyncOracleBackgroundWork(final JobParameters params) {
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
                            Repository repository = Repository.newInstance();

                            repository.deleteAllNodes(); // APAGAR TUDO  <---------------------------------------- REVER 0.5
                            for (Node node : nodes) {
                                repository.insert(node);
                            }
                            // database updated!!!!!
                            // cancelar JOB
                            //todo: rever se quero para o JOB depois de success ou sync para receber notificações
                            // só quero fazer isto uma vez -- se ok cancelar
                         //   JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                        //    scheduler.cancel(1000);
                        //    Log.d("ROOM", "local database update complete, total Nodes: "+nodes.size());
                        }

                        @Override
                        public void onFailure(Call<Node.MyNodes> call, Throwable t) {
                            Log.d("ORACLE", " Montes problemas com nodes");
                        }

                    });



                    //------------------------------------------------ DOWNLOAD dos VALORES SENSORES -------

                    Retrofit retrofit_Data = new Retrofit.Builder()
                            .baseUrl("https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/")
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    jsonOracleAPI = retrofit_Data.create(JsonOracleAPI.class);
                    // qual foi ao ultimo timestamp atualização
                    // receber e guardar ultimo timestamp das atualizações
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    timestamp = sharedPreferences.getInt(LAST_DATE_UPDATE_SENSOR_VALUES, 1561545996);

                    Call<SensorData.MySensorDataValues> callData = jsonOracleAPI.getSensorData(timestamp);

                    callData.enqueue(new retrofit2.Callback<SensorData.MySensorDataValues>() {


                        @Override
                        public void onResponse(Call<SensorData.MySensorDataValues> call, Response<SensorData.MySensorDataValues> response) {
                            if (!response.isSuccessful()) {
                                return;
                            }


                            // já tenho update AQUI !!!
                            List<SensorData> sensorDatas = response.body().items;
                            Repository repository = Repository.newInstance();
                            // atualizar database com novos sensor data
                            for (SensorData sensorData : sensorDatas) {
                                repository.insert(sensorData);
                                timestamp = sensorData.getDate_of_value();

                            }
                            // guardar a ultima atualização

                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(LAST_DATE_UPDATE_SENSOR_VALUES, timestamp);
                            editor.apply();

                        }

                        @Override
                        public void onFailure(Call<SensorData.MySensorDataValues> call, Throwable t) {
                            Log.d("ORACLE", " Montes problemas com valores sensores");
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
