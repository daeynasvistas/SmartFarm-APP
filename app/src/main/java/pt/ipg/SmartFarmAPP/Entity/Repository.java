package pt.ipg.SmartFarmAPP.Entity;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pt.ipg.SmartFarmAPP.Database.AppDatabase;

public class Repository {

    private NodeDao nodeDao;
    private LiveData<List<Node>> allNodes;
    private SensorDataDao sensorDataDao;
    private LiveData<List<SensorData>> allSensorDatas;

    private static Application application;

    public Repository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        nodeDao = database.nodeDao();
        allNodes = nodeDao.getAllNodes();

        sensorDataDao = database.sensorDataDao();
        allSensorDatas = sensorDataDao.getAllSensorData();
    }

    public static synchronized Repository newInstance(){
        Repository repository = new Repository(application);
        return repository;
    }

    /// -------------------------------------- CRUD NODES-------------------------------------
    public void insert(Node node) {
        new InsertNodeAsyncTask(nodeDao).execute(node);
    }

    public void update(Node node) {
        new UpdateNodeAsyncTask(nodeDao).execute(node);
    }

    public void delete(Node node) {
        new DeleteNodeAsyncTask(nodeDao).execute(node);
    }

    public void deleteAllNodes() {
        new DeleteAllNodesAsyncTask(nodeDao).execute();
    }

    public LiveData<List<Node>> getAllNodes() {
        return allNodes;
    }


    /// -------------------------------------- ASYNC NODES -------------------------------------
    private static class InsertNodeAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDao nodeDao;

        private InsertNodeAsyncTask(NodeDao nodeDao) {
            this.nodeDao = nodeDao;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDao.insert(nodes[0]);
            return null;
         }
    }

    private static class UpdateNodeAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDao nodeDao;

        private UpdateNodeAsyncTask(NodeDao nodeDao) {
            this.nodeDao = nodeDao;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDao.update(nodes[0]);
            return null;
        }
    }

    private static class DeleteNodeAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDao nodeDao;

        private DeleteNodeAsyncTask(NodeDao nodeDao) {
            this.nodeDao = nodeDao;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDao.delete(nodes[0]);
            return null;
        }
    }

    private static class DeleteAllNodesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NodeDao nodeDao;

        private DeleteAllNodesAsyncTask(NodeDao nodeDao) {
            this.nodeDao = nodeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            nodeDao.deleteAllNodes();
            return null;
        }
    }




    /// -------------------------------------- INSERT e GET SENSOR DATA-------------------------------------
    public void insert(SensorData sensorData) {
        new InsertSensorDataAsyncTask(sensorDataDao).execute(sensorData);
    }

    public LiveData<List<SensorData>> getAllSensorDatas() {
        return allSensorDatas;
    }


    /// -------------------------------------- ASYNC SENSOR DATA -------------------------------------
    private static class InsertSensorDataAsyncTask extends AsyncTask<SensorData, Void, Void> {
        private SensorDataDao sensorDataDao;

        private InsertSensorDataAsyncTask(SensorDataDao sensorDataDao) {
            this.sensorDataDao = sensorDataDao;
        }

        @Override
        protected Void doInBackground(SensorData... sensorData) {
            sensorDataDao.insert(sensorData[0]);
            return null;
        }
    }

}
