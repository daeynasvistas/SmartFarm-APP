package pt.ipg.SmartFarmAPP;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        setContentView(R.layout.activity_sow_node);

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



    public void configureMap(GoogleMap googleMap) {
        mMap = MapsTools.configBasicGoogleMap(googleMap);

        double latitude_picture = Double.parseDouble("40.540996");
        double longitude_picture = Double.parseDouble("-7.282595");

        CameraUpdate center = CameraUpdateFactory.newLatLng(new com.google.android.gms.maps.model.LatLng(latitude_picture, longitude_picture));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10.2f);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        LatLng picturePos = new LatLng(latitude_picture, longitude_picture);
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
