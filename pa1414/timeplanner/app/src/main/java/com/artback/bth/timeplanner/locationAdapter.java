package com.artback.bth.timeplanner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artback.bth.timeplanner.db.Event;

import java.util.List;

public class locationAdapter extends RecyclerView.Adapter<locationAdapter.ViewHolder> {
        private List<GeofenceLocation> mGeofenceLocations;
        private List<Cal>

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView locationTextView;
            public TextView timeTextView;
            public ViewHolder(View v) {
                super(v);

                locationTextView = (TextView) itemView.findViewById(R.id.location_text);
                timeTextView = (TextView) itemView.findViewById(R.id.time_text);
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
            ViewHolder vh = new ViewHolder(v);
            return vh;
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
        textView.setText();
        //holder.mView.setText(mDataset[position].getName());
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mGeofenceLocations.size();
    }
}


