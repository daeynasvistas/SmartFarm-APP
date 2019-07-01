package pt.ipg.SmartFarmAPP.UI.Fragment.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.R;


public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private List<Picture> pictures = new ArrayList<>();
    private List<Picture> fullPictures = new ArrayList<>(pictures);


    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.picture_item, viewGroup, false);

        return new PictureHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureHolder pictureHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



    class PictureHolder extends RecyclerView.ViewHolder {
        private TextView textViewNodes;
        private TextView textViewModel;
        private TextView textViewModelMore;
        private TextView textViewId;

        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            textViewNodes = itemView.findViewById(R.id.text_view_node);
            textViewModel = itemView.findViewById(R.id.text_view_model);
            textViewModelMore = itemView.findViewById(R.id.text_view_model_more);
            textViewId = itemView.findViewById(R.id.text_view_node_id);
        }


    }
}
