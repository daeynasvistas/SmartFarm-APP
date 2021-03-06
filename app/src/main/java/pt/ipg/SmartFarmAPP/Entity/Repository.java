package pt.ipg.SmartFarmAPP.Entity;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import pt.ipg.SmartFarmAPP.Database.AppDatabase;

public class Repository {

    private NodeDao nodeDao;
    private SensorDataDao sensorDataDao;
    private PictureDao pictureDao;

    private LiveData<List<Node>> allNodes;
    private LiveData<List<SensorData>> allSensorData;
    private List<SensorData> allSelectedSensorData;

    private LiveData<List<Picture>> allPictures;


    private static Application application;

    public Repository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        nodeDao = database.nodeDao();
        sensorDataDao = database.sensorDataDao();
        pictureDao = database.pictureDao();

        allNodes = nodeDao.getAllNodes();
        allSensorData = sensorDataDao.getAllSensorData();
        allPictures = pictureDao.getAllPictures();

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

    public LiveData<List<SensorData>> getAllSensorData() {
        return allSensorData;
    }

    public LiveData<List<SensorData>> getAllSelectedSensorData(int sensor, int limit, String node_ID) {
        return sensorDataDao.getAllSensorDatasSpecific(sensor, limit, node_ID);
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









    //------------------------------------ PICTURE --------------

    /// -------------------------------------- CRUD NODES-------------------------------------
    public void insert(Picture picture) {
        new InsertPictureAsyncTask(pictureDao).execute(picture);
    }

    public void update(Picture picture) {
        new UpdatePictureAsyncTask(pictureDao).execute(picture);
    }

    public void delete(Picture picture) {
        new DeletePictureAsyncTask(pictureDao).execute(picture);
    }

    public void deleteAllPictures() {
        new DeleteAllPicturesAsyncTask(pictureDao).execute();
    }

    public LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }



    /// -------------------------------------- ASYNC PICTURES -------------------------------------
    private static class InsertPictureAsyncTask extends AsyncTask<Picture, Void, Void> {
        private PictureDao pictureDao;

        private InsertPictureAsyncTask(PictureDao nodeDao) {
            this.pictureDao = nodeDao;
        }

        @Override
        protected Void doInBackground(Picture... pictures) {
            pictureDao.insert(pictures[0]);
            return null;
        }
    }

    private static class UpdatePictureAsyncTask extends AsyncTask<Picture, Void, Void> {
        private PictureDao pictureDao;

        private UpdatePictureAsyncTask(PictureDao nodeDao) {
            this.pictureDao = pictureDao;
        }

        @Override
        protected Void doInBackground(Picture... pictures) {
            pictureDao.update(pictures[0]);
            return null;
        }
    }

    private static class DeletePictureAsyncTask extends AsyncTask<Picture, Void, Void> {
        private PictureDao pictureDao;

        private DeletePictureAsyncTask(PictureDao pictureDao) {
            this.pictureDao = pictureDao;
        }

        @Override
        protected Void doInBackground(Picture... picture) {
            pictureDao.delete(picture[0]);
            return null;
        }
    }

    private static class DeleteAllPicturesAsyncTask extends AsyncTask<Void, Void, Void> {
        private PictureDao pictureDao;

        private DeleteAllPicturesAsyncTask(PictureDao pictureDao) {
            this.pictureDao = pictureDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pictureDao.deleteAllPictures();
            return null;
        }
    }





}
