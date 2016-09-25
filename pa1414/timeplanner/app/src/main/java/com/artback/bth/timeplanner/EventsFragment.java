package com.artback.bth.timeplanner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.artback.bth.timeplanner.db.Event;
import com.artback.bth.timeplanner.db.EventDataSource;

import java.util.ArrayList;

public class EventsFragment extends Fragment {
	protected EventListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_events, container,
				false);

		ArrayList<Event> list = new ArrayList<Event>();
		ListView listLv = (ListView) rootView.findViewById(R.id.list);
		adapter = new EventListAdapter(getActivity(), R.layout.item_event, list);
		listLv.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		loadData();
	}

	protected void loadData() {
		EventDataSource eds = new EventDataSource(getActivity());
		ArrayList<Event> list = eds.getEvents();
		eds.close();

		adapter.clear();
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.menu_events, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case id.go_to_map:
			Fragment f = new MapFragment();
			FragmentManager fragmentManager = getFragmentManager();

			fragmentManager.beginTransaction().replace(android.R.id.content, f)
					.addToBackStack("map").commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
