package pt.ipg.SmartFarmAPP.Entity;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NodeRepository {

    private NodeDao nodeDao;
    private LiveData<List<Node>> allNodes;

    public NodeRepository(Application application) {
        NodeDatabase database = NodeDatabase.getInstance(application);
        nodeDao = database.nodeDao();
        allNodes = nodeDao.getAllNodes();
    }

    /// -------------------------------------- CRUD -------------------------------------
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


    /// -------------------------------------- ASYNC  -------------------------------------
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


}
