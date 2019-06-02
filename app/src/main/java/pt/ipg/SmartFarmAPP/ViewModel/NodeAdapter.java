package pt.ipg.SmartFarmAPP.ViewModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.R;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeHolder> {

    private List<Node> nodes = new ArrayList<>();

    @NonNull
    @Override
    public NodeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.node_item, viewGroup, false);

        return new NodeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeHolder holder, int position) {
        Node currentNode = nodes.get(position);
        holder.textViewNodes.setText(currentNode.getPerson());
        holder.textViewModel.setText(currentNode.getModel());
        holder.textViewId.setText(String.valueOf(currentNode.get_id()));
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
        notifyDataSetChanged();
    }


    class NodeHolder extends RecyclerView.ViewHolder{
        private TextView textViewNodes;
        private TextView textViewModel;
        private TextView textViewId;

        public NodeHolder(@NonNull View itemView) {
            super(itemView);
            textViewNodes = itemView.findViewById(R.id.text_view_node);
            textViewModel = itemView.findViewById(R.id.text_view_model);
            textViewId    = itemView.findViewById(R.id.text_view_node_id);
        }
    }
}
