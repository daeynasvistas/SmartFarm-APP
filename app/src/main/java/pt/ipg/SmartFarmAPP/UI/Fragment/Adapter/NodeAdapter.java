package pt.ipg.SmartFarmAPP.UI.Fragment.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.R;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeHolder> implements Filterable {

    private List<Node> nodes = new ArrayList<>();
    private List<Node> fullNodes = new ArrayList<>(nodes);

    /*
    public NodeAdapter(List<Node> nodes) {
        this.nodes = nodes;
        fullNodes = new ArrayList<>(nodes);
    }
*/

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

        holder.textViewNodes.setText("User: "+currentNode.getPerson());
        holder.textViewModel.setText(" Modelo: "+currentNode.getModel()+" | MAC: "+currentNode.getMac()+" | Firmware: "+currentNode.getFirm_vers());
        holder.textViewId.setText(String.valueOf(currentNode.getId()));

        Node currentItem = nodes.get(position);

        ImageView imageView;
        TextView textView1;
        TextView textView2;

      //  holder.imageView.setImageResource(currentItem.getImageResource());
      //  holder.textView1.setText(currentItem.getText1());
      //  holder.textView2.setText(currentItem.getText2());
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


    @Override
    public Filter getFilter() {
        return (Filter) nodes;
    }
    private Filter nodesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Node> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullNodes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Node item : nodes) {
                 //   if (item.getText2().toLowerCase().contains(filterPattern)) {
                 //       filteredList.add(item);
                 //   }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            nodes.clear();
            nodes.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
