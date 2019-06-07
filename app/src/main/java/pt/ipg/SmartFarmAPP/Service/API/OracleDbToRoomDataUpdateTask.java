package pt.ipg.SmartFarmAPP.Service.API;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.NodeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OracleDbToRoomDataUpdateTask  extends Fragment {
    public static final String URL = "https://bd.ipg.pt:5500/ords/bda_1007249/APIv3/";
    private TaskExecutor taskExecutor;

    public OracleDbToRoomDataUpdateTask(){
        taskExecutor = new TaskExecutor();
    }

    public void getNodesFromOracleUpdateLocalDb()  {
        // cenas
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
                taskExecutor.execute(new RoomUpdateTask(nodes));
            }

            @Override
            public void onFailure(Call<Node.MyNodes> call, Throwable t) {
                Log.d("ORACLE", " Montes problemas ",
                        t.getCause());
            }

        });

    }

    public class TaskExecutor implements Executor {
        @Override
        public void execute(@NonNull Runnable runnable) {
            Thread t =  new Thread(runnable);
            t.start();
        }
    }


    public class RoomUpdateTask implements Runnable{
        private List<Node> nodes;
        private Context context;
        public RoomUpdateTask(List<Node> newnodes){
            nodes = newnodes;
        }
        @Override
        public void run() {
            insertLatestNodesIntoLocalDb(nodes, context);
        }

        private void insertLatestNodesIntoLocalDb(List<Node> nodes, Context ctx){
            // singleton  .. em teoria só há uma instance .. mesmo que com novo builder
            NodeRepository nodeRepository = NodeRepository.newInstance();

            nodeRepository.deleteAllNodes();
            for (Node node : nodes) {
                nodeRepository.insert(node);
            }

            /* REMOVER método directamente na BD melhor sempre repositório
            db = Room.databaseBuilder(ctx,
                    AppDatabase.class, "node_database").build();
            //insert new nodes
            db.nodeDao().deleteAllNodes();
            db.nodeDao().insertNodes(nodes);
            */
            Log.d("ROOM", "local database update complete, total Nodes: "+nodes.size()+", "+getTodaysDate());

        }

    }


    private String getTodaysDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }
}