package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.MainActivity;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.Tools.DataConverter;
import pt.ipg.SmartFarmAPP.Service.API.Tools.GoogleAI;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.NodeAdapter;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.PictureAdapter;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.PictureViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.SharedViewModel;
import pt.ipg.SmartFarmAPP.ViewNodeActivity;
import pt.ipg.SmartFarmAPP.ViewPictureActivity;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static pt.ipg.SmartFarmAPP.ViewPictureActivity.EDIT_PICTURE_REQUEST;

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

        initLocation();  //--- saber a minhaPos para foto

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
                  Intent intent = new Intent(getContext(), ViewPictureActivity.class);
                  intent.putExtra(ViewPictureActivity.EXTRA_LOCAL_ID, picture.getLocal_ID());

                  ViewPictureActivity.currentPicture = picture; //Enviar directamento a picture
                  startActivityForResult(intent, EDIT_PICTURE_REQUEST);
            }
        });

        progressbar.setVisibility(GONE);
        return view;
    }









    private StringBuilder builder;
    private FusedLocationProviderClient location;
    private FusedLocationProviderClient mFusedLocationClient;

    private LatLng minhaPos;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    public static final int REQUEST_CODE_LOCATION = 5000;

    private void initLocation() {

        builder = new StringBuilder();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        } else {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

            // getLocationUpdates();
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // ... tenho aqui pos
                                minhaPos = new LatLng( location.getLongitude(),location.getLatitude());
                                Toast.makeText(getContext(), "Pos: "+minhaPos.latitude+" , "+minhaPos.longitude, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_INTENT && resultCode == Activity.RESULT_OK){
            Bitmap bitMap = (Bitmap) data.getExtras().get("data");
            if (bitMap!=null){
                // Add Local

                byte[] bytesImage = DataConverter.convertImage2ByteArray(bitMap);

                recognizeObjects(bytesImage);

            }

        }


    }






    public static final String API_KEY = "AIzaSyAKSWSLnbOS7jSV3DH1CmPCS5GLzQ2pzYI";
    private void recognizeObjects(byte[] bytesImage) {

        Long tsNow = DataConverter.getNowTimestamp();
        String base64Data = Base64.encodeToString(bytesImage, Base64.URL_SAFE);

        // google AI cenas
        try {

            //"features": [
            JSONObject feature = new JSONObject();
            feature.put("type", "LABEL_DETECTION");
            JSONArray features = new JSONArray();
            features.put(feature);
            JSONObject imageContent = new JSONObject();
            imageContent.put("content", base64Data);


            // "requests": [
            JSONObject request = new JSONObject();
            request.put("image", imageContent);
            request.put("features", features);
            JSONArray requests = new JSONArray();
            requests.put(request);

            // fábrica de JSON para envio
            JSONObject postData = new JSONObject();
            postData.put("requests", requests);

            String json = postData.toString();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"), json);


            Request requestAPI = new Request.Builder()
                    .header("content-type", "application/json")
                    .url("https://vision.googleapis.com/v1/images:annotate?key=" + API_KEY)
                    .post(body)
                    .build();


            OkHttpClient client = new OkHttpClient();

            client.newCall(requestAPI).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myReponse = response.body().string();

                             try {
                                    JSONArray labels = new JSONObject(myReponse)
                                            .getJSONArray("responses")
                                            .getJSONObject(0)
                                            .getJSONArray("labelAnnotations");

                                    String results = "";

                                    for (int i = 0; i < labels.length(); i++) {

                                        results = results +
                                                labels.getJSONObject(i).getString("description") +
                                               // " (" + labels.getJSONObject(i).getString("score") + ") " +
                                                ", ";
                                    }

                                 /// ------------------------------------------------------------------------------- ToDo alterar lat lng para a minha POS no momento da foto ver 0.8
                                 Picture picture = new Picture(labels.getJSONObject(0).getString("description"), results , tsNow, bytesImage, (float)minhaPos.longitude,(float)minhaPos.latitude,1000);
                                 pictureViewModel.insert(picture);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                    } // if statment
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
