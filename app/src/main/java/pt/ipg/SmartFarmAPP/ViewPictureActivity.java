package pt.ipg.SmartFarmAPP;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.Service.API.Tools.MapsTools;

public class ViewPictureActivity extends AppCompatActivity {
    public static final int EDIT_PICTURE_REQUEST = 3000;

    public static final String EXTRA_LOCAL_ID = "pt.ipg.SmartFarmAPP.EXTRA_LOCAL_ID"; // para o SELECT só este node
    public static Picture currentPicture; // picture aqui (todo colocar private ver 0.7)

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remover dinamicamente ANTES de lá colocar content
        // aqui https://stackoverflow.com/questions/25863676/android-activity-without-actionbar
     //  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
     //   getSupportActionBar().hide();


        setContentView(R.layout.activity_sow_node);
        initToolbar();


        initMapFragment();


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
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10.2f);

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
