package com.developer.arsltech.trackingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnCurrentLocation(View view) {

        startActivity(new Intent(this,MapsActivity.class));

    }

    public void btnRetrieveLocation(View view) {

        startActivity(new Intent(getApplicationContext(),RetrieveMapActivity.class));


    }
}
