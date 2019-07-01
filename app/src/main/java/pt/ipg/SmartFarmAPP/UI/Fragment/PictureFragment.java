package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.PictureAdapter;
import pt.ipg.SmartFarmAPP.ViewModel.PictureViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.SharedViewModel;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

public class PictureFragment extends Fragment {
    public static final int REQUEST_CODE = 3000;
    private SharedViewModel viewModel;
    private PictureViewModel pictureViewModel;

    Bitmap bitMap;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, null);
        bitMap = null;
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
                    startActivityForResult(intent, REQUEST_CODE);
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

        final PictureViewModel pictureViewModel = ViewModelProviders.of(getActivity()).get(PictureViewModel.class);
        pictureViewModel.getAllPictures().observe(getActivity(), new Observer<List<Picture>>() {

            @Override
            public void onChanged(@Nullable List<Picture> pictures) {
                //
                //cenas aqui onchange
            }

        });


        progressbar.setVisibility(GONE);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                    bitMap = (Bitmap) data.getExtras().get("data");
                    break;
        }

    }
}
