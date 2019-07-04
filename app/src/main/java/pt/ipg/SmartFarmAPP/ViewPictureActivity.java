package pt.ipg.SmartFarmAPP;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.Service.API.Tools.DataConverter;
import pt.ipg.SmartFarmAPP.Service.API.Tools.MapsTools;


public class ViewPictureActivity   extends   AppCompatActivity{


    public static final int EDIT_PICTURE_REQUEST = 3000;
    public static final String EXTRA_LOCAL_ID = "pt.ipg.SmartFarmAPP.EXTRA_LOCAL_ID"; // para o SELECT só este node

    public static Picture currentPicture; // picture aqui (todo colocar private ver 0.7)
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sow_node);
        initToolbar();
        initMapFragment();


        ImageView imageView = findViewById(R.id.imageViewPlant);
        imageView.setImageBitmap(DataConverter.convertByteArray2Bitmap( currentPicture.getImage()));

        ImageView imageViewThumb = findViewById(R.id.imageViewThumb);
        imageViewThumb.setImageBitmap(DataConverter.convertByteArray2Bitmap( currentPicture.getImage()));
    //   initLocation();
        // Permissions ok, we get last location



    }



    private void initMapFragment() {
        // aqui: https://developers.google.com/maps/documentation/android-sdk/intro
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                configureMap(googleMap);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSystemBarColor(this, R.color.colorAccent);
        setSystemBarLight(this);
    }


    // aqui https://stackoverflow.com/questions/28979276/how-to-change-the-color-of-status-bar-in-android
    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color,null));
        }
    }

    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }



    public void configureMap(GoogleMap googleMap) {
        mMap = MapsTools.configBasicGoogleMap(googleMap);

        double latitude_picture = currentPicture.getLatitude(); //Double.parseDouble("40.540996");
        double longitude_picture = currentPicture.getLongitude(); //Double.parseDouble("-7.282595");

        CameraUpdate center = CameraUpdateFactory.newLatLng(new com.google.android.gms.maps.model.LatLng( longitude_picture,latitude_picture ));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15.2f);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        LatLng picturePos = new LatLng( longitude_picture,latitude_picture);
        displayMarker(this, mMap, picturePos );

    }



    public static void displayMarker(Activity act, GoogleMap googleMap, LatLng pos) {
        // make current location marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(pos);  ///---------------------------------------- Alterar para pos picture
        markerOptions.anchor(0.5f, 0.5f);

        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // --------------------------------------------------------------------------------------------------------- SE PRETENDER COLOCAR UMA IMAGEM NO PIN
        //View marker_view = inflater.inflate(R.layout.maps_marker, null);
        // ImageView marker = (ImageView) marker_view.findViewById(R.id.marker);
        // marker.setImageResource(R.drawable.ic_close_black_24dp);
        // marker.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(act, 40), dpToPx(act, 40)));
        //  marker.setRotation(p.rotation);
        // markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createBitmapFromView(act, marker_view)));

        googleMap.addMarker(markerOptions);
    }







}
