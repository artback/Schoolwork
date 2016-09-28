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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private RecyclerView locationView;
    private RecyclerView.Adapter locAdapter;
    private RecyclerView.LayoutManager locationLayoutManager;
    public static String TAG = "Debug";
    static public boolean geofencesAlreadyRegistered = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_page);

        locationView = (RecyclerView) findViewById(R.id.location_list);
        locationView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        locationView.setLayoutManager(llm);

        FloatingActionButton fabNew = (FloatingActionButton) findViewById(R.id.fabNew);
        fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            setContentView(R.layout.add_location_page);
            }
        });

        locationLayoutManager= new LinearLayoutManager(this);
        locationView.setLayoutManager(locationLayoutManager);
        List<GeofenceLocation> myGeofenceSet = new ArrayList<GeofenceLocation>();
        locAdapter = new locationAdapter(myGeofenceSet);
        locationView.setAdapter(locAdapter);

        //start geolocation
        startService(new Intent(this, GeolocationService.class));
    }

    @Override
    protected void onStart() {
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
