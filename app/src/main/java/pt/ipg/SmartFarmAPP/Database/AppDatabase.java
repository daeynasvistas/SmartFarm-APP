package pt.ipg.SmartFarmAPP.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.NodeDao;
import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.Entity.PictureDao;
import pt.ipg.SmartFarmAPP.Entity.Repository;
import pt.ipg.SmartFarmAPP.Entity.SensorData;
import pt.ipg.SmartFarmAPP.Entity.SensorDataDao;
import pt.ipg.SmartFarmAPP.Service.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Service.API.API;
import pt.ipg.SmartFarmAPP.Service.API.Tools.HMAC;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Database(entities = {Node.class, SensorData.class, Picture.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract NodeDao nodeDao();
    public abstract SensorDataDao sensorDataDao();
    public abstract PictureDao pictureDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "node_database")
                    .addCallback(roomCallback) // populate as cenas no inicio
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NodeDao nodeDao;
        private SensorDataDao sensorDataDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            // vers 2 -- com dados dos sensores
            nodeDao = db.nodeDao();
            sensorDataDao = db.sensorDataDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                HMAC hmac = new HMAC();
                String nonce = hmac.getNonce();
                String key = hmac.getKey();
                String sign = hmac.getSign(nonce, hmac.getSecret());


                Log.d("ORACLE", "--- DOWNLOAD JSON ---");
                OkHttpClient okHttpClient = API.UnsafeOkHttpClient.getUnsafeOkHttpClient();
                // problema com IPG certificado .. não utilizar produção!!

                //------------------------------------------------ DOWNLOAD dos NODES -------
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/")
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonOracleAPI jsonOracleAPI = retrofit.create(JsonOracleAPI.class);
                Call<Node.MyNodes> call = jsonOracleAPI.getNodes();

                call.enqueue(new retrofit2.Callback<Node.MyNodes>() {

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
                Call<SensorData.MySensorDataValues> callData = jsonOracleAPI.getSensorData(1530048258, key, sign, nonce); // 2018

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
                        }
                }

                    @Override
                    public void onFailure(Call<SensorData.MySensorDataValues> call, Throwable t) {
                        Log.d("ORACLE", " Montes problemas com valores sensores");
                    }
                });










            } catch (Exception e) {
                e.printStackTrace();
            }
            /// Populate com cenas .. remover e colocar retrofit para receber API
       /*     nodeDao.insert(new Node(
                    "Daniel Mendes",
                    "Lora_a1",
                    "0.1",
                    "00000000",
                    0.0f,
                    0.0f,
                    0,
                    0,
                    "192.168.0.1"
                    ));
*/
            return null;
        }
    }

}
