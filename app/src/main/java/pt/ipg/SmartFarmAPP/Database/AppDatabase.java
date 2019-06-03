package pt.ipg.SmartFarmAPP.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.NodeDao;

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
