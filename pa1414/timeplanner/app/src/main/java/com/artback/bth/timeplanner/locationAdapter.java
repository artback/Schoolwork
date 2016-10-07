package com.artback.bth.timeplanner;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.artback.bth.timeplanner.Geofence.GeofenceLocation;

import java.util.List;

public class locationAdapter extends RecyclerView.Adapter<locationAdapter.ViewHolder> {
        private List<GeofenceLocation> mGeofenceLocations;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
            // each data item is just a string in this case
            private TextView locationTextView;
            private TextView timeTextView;
            public ViewHolder(View v) {
                super(v);

                locationTextView = (TextView) itemView.findViewById(R.id.location_text);
                timeTextView = (TextView) itemView.findViewById(R.id.time_text);
            }
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CalenderActivity.class);
                intent.putExtra(EXTRA_MESSAGE)
                view.getContext().startActivity(intent);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public locationAdapter(List<GeofenceLocation> GeofenceLocations) {
            mGeofenceLocations = GeofenceLocations;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public locationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_view, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new ViewHolder(v);
        }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        GeofenceLocation location = mGeofenceLocations.get(position);
        TextView textView = holder.locationTextView;
        TextView textView1 = holder.timeTextView;
        textView.setText(location.getId());
        textView1.setText("Total tid");
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mGeofenceLocations.size();
    }
}


