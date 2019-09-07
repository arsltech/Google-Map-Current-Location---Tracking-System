package com.developer.arsltech.trackingsystem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RetrieveMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_map);
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Current Location");

        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                Double longitude = dataSnapshot.child("longitude").getValue(Double.class);

                LatLng location = new LatLng(latitude,longitude);

                mMap.addMarker(new MarkerOptions().position(location).title(getCOmpleteAddress(latitude,longitude)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private String getCOmpleteAddress(double Latitude,double Longtitude){

       String address = "";

       Geocoder geocoder = new Geocoder(RetrieveMapActivity.this,Locale.getDefault());

       try{

           List<Address> addresses = geocoder.getFromLocation(Latitude,Longtitude,1);

           if(address!=null){

               Address returnAddress = addresses.get(0);
               StringBuilder stringBuilderReturnAddress =  new StringBuilder("");

               for(int i=0; i<=returnAddress.getMaxAddressLineIndex();i++){
                   stringBuilderReturnAddress.append(returnAddress.getAddressLine(i)).append("\n");
               }

               address = stringBuilderReturnAddress.toString();

           }
           else{
               Toast.makeText(this, "Address not found", Toast.LENGTH_SHORT).show();
           }

       }
       catch (Exception e){
           Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
       }


        return address;
    }

}
