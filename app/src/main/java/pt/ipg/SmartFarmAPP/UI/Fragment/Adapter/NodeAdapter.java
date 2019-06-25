package pt.ipg.SmartFarmAPP.UI.Fragment.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
    private onItemClickListener listener;

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NodeHolder holder, int position) {
        Node currentNode = nodes.get(position);

        holder.textViewNodes.setText(currentNode.getModel());
        holder.textViewModel.setText("Sync ID: "+currentNode.getLocal_ID()+" | User: "+currentNode.getPerson()+" | MAC: "+currentNode.getMac()+" | Firmware: "+currentNode.getFirm_vers());
        holder.textViewModelMore.setText(" Altitude: "+currentNode.getLatitude()+" | Lat: "+currentNode.getLatitude()+" | Lng: "+currentNode.getLongitude());
        if(currentNode.getId()!=0){
          holder.textViewId.setText(String.valueOf(currentNode.getId()));
        }else{
          holder.textViewId.setText("sync");
          holder.itemView.setBackgroundColor(Color.LTGRAY);
        }

        Node currentItem = nodes.get(position);


    }

    // para editar no onclick
    public interface onItemClickListener{
        void onItemClick(Node node);
    }
    public void setOnItemClickListener(onItemClickListener  listener){
        this.listener = listener;
    }



    @Override
    public int getItemCount() {
        return nodes.size();
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
        notifyDataSetChanged();
    }

    // para swipe delete no adapter
    public Node getNodeAt(int position) {
        return nodes.get(position);
    }


    class NodeHolder extends RecyclerView.ViewHolder{
        private TextView textViewNodes;
        private TextView textViewModel;
        private TextView textViewModelMore;
        private TextView textViewId;

        public NodeHolder(@NonNull View itemView) {
            super(itemView);
            textViewNodes = itemView.findViewById(R.id.text_view_node);
            textViewModel = itemView.findViewById(R.id.text_view_model);
            textViewModelMore = itemView.findViewById(R.id.text_view_model_more);
            textViewId    = itemView.findViewById(R.id.text_view_node_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(nodes.get(position));
                    }
                }
            });


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
