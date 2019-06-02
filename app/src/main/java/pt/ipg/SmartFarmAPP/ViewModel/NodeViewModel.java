package pt.ipg.SmartFarmAPP.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.NodeRepository;

public class NodeViewModel extends AndroidViewModel {

    private NodeRepository repository;
    private LiveData<List<Node>> allNodes;

    public NodeViewModel(@NonNull Application application) {
        super(application);
        repository = new NodeRepository(application);
        allNodes = repository.getAllNodes();

    }
    public void insert(Node node) {
        repository.insert(node);
    }

    public void update(Node node) {
        repository.update(node);
    }

    public void delete(Node node) {
        repository.delete(node);
    }

    public void deleteAllNodes() {
        repository.deleteAllNodes();
    }

    public LiveData<List<Node>> getAllNodes() {
        return allNodes;
    }


}
