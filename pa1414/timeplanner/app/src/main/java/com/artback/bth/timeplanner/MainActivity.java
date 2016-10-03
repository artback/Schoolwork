package com.artback.bth.timeplanner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        locationView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                    }
        //start geolocation
        startService(new Intent(this, GeolocationService.class));

        locationLayoutManager= new LinearLayoutManager(this);
        locationView.setLayoutManager(locationLayoutManager);
        List<GeofenceLocation> myGeofenceSet = new ArrayList<GeofenceLocation>
                (GeofenceLocationStore.getInstance().geofences.values());
        locAdapter = new locationAdapter(myGeofenceSet);
        locationView.setAdapter(locAdapter);

    }
    public void openaddpage(View view){
        Intent intent = new Intent(this, AddLocationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
