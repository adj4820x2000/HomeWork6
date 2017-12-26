package com.example.simon.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class map extends AppCompatActivity {
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        test=(TextView)findViewById(R.id.test);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Intent i=this.getIntent();
        final Bundle bundle=i.getExtras();
        test.setText("位置"+bundle.getString("LOCATION"));

        mapFragment.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
/*
                if (ActivityCompat.checkSelfPermission(map.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(map.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                googleMap.setMyLocationEnabled(true);*///上面是定位
                Geocoder geocoder = new Geocoder(getBaseContext());
                List<Address> addressList = null;
                int maxResults = 1;
                try{
                    addressList = geocoder.getFromLocationName(bundle.getString("LOCATION"),maxResults);
                }
                catch (IOException e){
                    Log.e("GeocoderActivity",e.toString());
                    Toast.makeText(getBaseContext(),"2",Toast.LENGTH_SHORT).show();
                }
                if (addressList == null ||addressList.isEmpty()){
                    Toast.makeText(getBaseContext(),"找不到喔",Toast.LENGTH_SHORT).show();
                }else{
                    Address address = addressList.get(0);
                    LatLng location = new LatLng(address.getLatitude(),
                            address.getLongitude());
                    String snippet = address.getAddressLine(0);
                    googleMap.addMarker(new MarkerOptions().position(location)
                            .title(bundle.getString("LOCATION")).snippet(snippet));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(location).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));

                }
            }
        });
    }



}
