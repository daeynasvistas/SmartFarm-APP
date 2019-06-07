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
import pt.ipg.SmartFarmAPP.Entity.NodeRepository;
import pt.ipg.SmartFarmAPP.Service.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Service.API.API;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Database(entities = {Node.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract NodeDao nodeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
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

        private PopulateDbAsyncTask(AppDatabase db) {
            nodeDao = db.nodeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Log.d("ORACLE", "--- DOWNLOAD JSON ---");
                OkHttpClient okHttpClient = API.UnsafeOkHttpClient.getUnsafeOkHttpClient();
                // problema com IPG certificado .. não utilizar produção!!
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
                        NodeRepository nodeRepository = NodeRepository.newInstance();

                        nodeRepository.deleteAllNodes(); // APAGAR TUDO  <---------------------------------------- REVER 0.5
                        for (Node node : nodes) {
                            nodeRepository.insert(node);
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
                        Log.d("ORACLE", " Montes problemas ");
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
