package pt.ipg.SmartFarmAPP.UI.Fragment.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import pt.ipg.SmartFarmAPP.Entity.Node;


public class PictureAdapter extends RecyclerView.Adapter<NodeAdapter.NodeHolder> {

private List<Node> nodes = new ArrayList<>();
private List<Node> fullNodes = new ArrayList<>(nodes);
private NodeAdapter.onItemClickListener listener;

    @NonNull
    @Override
    public NodeAdapter.NodeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NodeAdapter.NodeHolder nodeHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



}
