package com.example.x.bonus;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String maps,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        maps = intent.getStringExtra("map");
        name = intent.getStringExtra("name");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] map = maps.split(",");
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Float.parseFloat(map[0]),Float.parseFloat(map[1]) );
        mMap.addMarker(new MarkerOptions().position(sydney).title(name));
        CameraPosition cameraPosition = null;
        try {
                cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(Float.parseFloat(map[0]),Float.parseFloat(map[1])))
                        .zoom(14)
                        .build();


            } catch (Exception e){
                Log.e("setLoc"," We're have some problems");
            }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
