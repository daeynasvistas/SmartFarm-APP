package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.Tools.DataConverter;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.NodeAdapter;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.PictureAdapter;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.PictureViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.SharedViewModel;
import pt.ipg.SmartFarmAPP.ViewNodeActivity;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

public class PictureFragment extends Fragment {
    public static final int CAMERA_INTENT = 3000;
    private SharedViewModel viewModel;
    private PictureViewModel pictureViewModel;

    Picture pictureAt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, null);
         // mais nodes
        FloatingActionButton fab = view.findViewById(R.id.fabAddPicture);//Find fab Id
        // ------------------  MAIS uma Picture ---------------------------------------
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // faz cenas
                Toast.makeText(getActivity(), "Montes de cenas!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: opening dialog");

                // receber picture aqui
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(intent, CAMERA_INTENT);
                }
            }



        });

        // Content in view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final PictureAdapter pictureAdapter = new PictureAdapter();
        recyclerView.setAdapter(pictureAdapter);

        ProgressBar progressbar = view.findViewById(R.id.progressbar);

        pictureViewModel = ViewModelProviders.of(getActivity()).get(PictureViewModel.class);
        pictureViewModel.getAllPictures().observe(getActivity(), new Observer<List<Picture>>() {

            @Override
            public void onChanged(@Nullable List<Picture> pictures) {
                //
                //cenas aqui onchange
                pictureAdapter.setPictures(pictures);
            }

        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                pictureAt = pictureAdapter.getPictureAt(viewHolder.getAdapterPosition());

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setCancelable(false);
                dialog.setTitle("CONFIRMAÇÃO");
                dialog.setMessage("Tem a certeza que pretende remover esta imagem?" );
                dialog.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Action for "Delete".
                     //   startService("Sync Oracle - DELETE", nodeAt.getId(), null, null);
                        pictureViewModel.delete(pictureAt); ///  <--- sem nenhuma confirmação!!! todo confirmação delete node

                        Snackbar snackbar = Snackbar.make(getView(), "Imagem removida.", Snackbar.LENGTH_LONG);
                        pictureAdapter.notifyDataSetChanged();
                        snackbar.show();

                    }
                })
                        .setNegativeButton("Cancelar ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar snackbar = Snackbar.make(getView(), "Acção cancelada.", Snackbar.LENGTH_LONG);
                                pictureAdapter.notifyDataSetChanged();
                                snackbar.show();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);
        // FIM --- Content in view

        
        // --- editar item AQUI com anonymous class!!
        pictureAdapter.setOnItemClickListener(new PictureAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Picture picture) {
            //   Intent intent = new Intent(getContext(), ViewPictureActivity.class);
            //    intent.putExtra(ViewNodeActivity.EXTRA_ID, picture.getMac());

            //    ViewNodeActivity.currentPicture = picture; //Enviar directamento o Node
            //    startActivityForResult(intent, EDIT_NODE_REQUEST);

            }
        });

        progressbar.setVisibility(GONE);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_INTENT && resultCode == Activity.RESULT_OK){
            Bitmap bitMap = (Bitmap) data.getExtras().get("data");
            if (bitMap!=null){
                // Add Local

                byte[] bytesImage = DataConverter.convertImage2ByteArray(bitMap);
                Picture picture = new Picture("Sync","sync",15212545, bytesImage, 45.000f,7.255f,1000);
                pictureViewModel.insert(picture);


            }

        }


    }
}
