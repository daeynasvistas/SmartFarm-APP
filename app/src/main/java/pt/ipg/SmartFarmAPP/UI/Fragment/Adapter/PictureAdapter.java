package pt.ipg.SmartFarmAPP.UI.Fragment.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.Tools.DataConverter;


public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private List<Picture> pictures = new ArrayList<>();
    private List<Picture> fullPictures = new ArrayList<>(pictures);
    private PictureAdapter.onItemClickListener listener;


    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.picture_item, viewGroup, false);
        return new PictureHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureHolder holder, int position) {
        Picture currentPicture = pictures.get(position);

        holder.textViewDoenca.setText(currentPicture.getDoenca());
        // convert array em bmp para mostrar
        holder.imageView.setImageBitmap(DataConverter.convertByteArray2Bitmap(currentPicture.getImage()));
        holder.textViewDescription.setText("Descrição: "+currentPicture.getDescription());
        holder.textViewMaisInfo.setText("Altitude: "+currentPicture.getAltitude()+" | Lat: "+currentPicture.getLatitude()+" | Lng: "+currentPicture.getLongitude() + " | Data: "+ currentPicture.getDate());

        if(currentPicture.getLocal_ID()!=0){
            holder.textViewId.setText(String.valueOf(currentPicture.getLocal_ID()));
        }else{
            holder.textViewId.setText("sync");
           // holder.itemView.setBackgroundColor(Color.LTGRAY);
        }

        // Node currentItem = nodes.get(position);

    }


    // para editar no onclick
    public interface onItemClickListener{
        void onItemClick(Picture picture);
    }
    public void setOnItemClickListener(PictureAdapter.onItemClickListener listener){
        this.listener = listener;
    }



    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
        notifyDataSetChanged();
    }

    // para swipe delete no adapter
    public Picture getPictureAt(int position) {
        return pictures.get(position);
    }



    class PictureHolder extends RecyclerView.ViewHolder {
        private TextView textViewDoenca;
        private TextView textViewDescription;
        private TextView textViewMaisInfo;
        private TextView textViewId;
        private ImageView imageView;

        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewDoenca = itemView.findViewById(R.id.text_view_picture_doenca);
            textViewDescription = itemView.findViewById(R.id.text_view_picture_description);
            textViewMaisInfo = itemView.findViewById(R.id.text_view_picturel_more);
            textViewId = itemView.findViewById(R.id.text_view_picture_id);
        }


    }
}
