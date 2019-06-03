package pt.ipg.SmartFarmAPP.Service.API;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.arch.core.executor.TaskExecutor;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.Database.AppDatabase;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Service.API.Tools.API;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OracleDbToRoomDataUpdateTask extends JobService {
    private AppDatabase db;
    private TaskExecutor taskExecutor;
    private NodeViewModel nodeViewModel;
    private Constantes constantes;


    public OracleDbToRoomDataUpdateTask(){
        taskExecutor = new TaskExecutor();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void getNodesFromOracleUpdateLocalDb(final Context ctx) {


        // cenas
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
        call.enqueue(new Callback<Node.MyNodes>() {

            @Override
            public void onResponse(Call<Node.MyNodes> call, Response<Node.MyNodes> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                // já tenho update AQUI !!!
                List<Node> nodes = response.body().items;
                taskExecutor.execute(new RoomUpdateTask(nodes, ctx));
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
        public RoomUpdateTask(List<Node> newnodes, Context ctx){
            nodes = newnodes;
            context = ctx;
        }
        @Override
        public void run() {
            insertLatestNodesIntoLocalDb(nodes, context);
        }

        private void insertLatestNodesIntoLocalDb(List<Node> nodes, Context ctx){
            // singleton  .. em teoria só há uma instance .. mesmo que com novo builder
            db = Room.databaseBuilder(ctx,
                    AppDatabase.class, "node_database").build();

            //insert new nodes
            db.nodeDao().insertNodes(nodes);

            Log.d("ROOM", "local database update complete");
            Log.d("ROOM", "number of local nodes " +
                    db.nodeDao().getNodes().size());
        }

    }



    private String getTodaysDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
    }
}