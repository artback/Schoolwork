package com.artback.bth.timeplanner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends Activity {
    private RecyclerView locationView;
    private RecyclerView.Adapter locAdapter;
    private RecyclerView.LayoutManager locationLayoutManager;
    private GoogleApiClient mGoogleApiClient;
    public static String TAG = "Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .build();

        setContentView(R.layout.main_page);
        locationView = (RecyclerView) findViewById(R.id.location_list);
        locationView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        locationView.setLayoutManager(llm);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        FloatingActionButton fabNew = (FloatingActionButton) findViewById(R.id.fabNew);
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        locationLayoutManager= new LinearLayoutManager(this);
        locationView.setLayoutManager(locationLayoutManager);
        Location[] myLocationSet = new Location[10];
        locAdapter = new locationAdapter(myLocationSet);
        locationView.setAdapter(locAdapter);

        //start geolocation
        startService(new Intent(this, GeolocationService.class));
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
