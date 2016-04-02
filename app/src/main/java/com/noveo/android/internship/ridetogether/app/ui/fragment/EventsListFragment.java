package com.noveo.android.internship.ridetogether.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.Events;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.EventStaggeredAdapter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class EventsListFragment extends BaseFragment {
    private static final String EVENTS_TAG = "EVENTS_TAG";
    @Bind(R.id.events_list)
    RecyclerView eventsView;
    private List<Event> events = new ArrayList<>();
    private EventStaggeredAdapter adapter;

    public static EventsListFragment newInstance(List<Event> events) {
        EventsListFragment fragment = new EventsListFragment();
        Bundle parameters = new Bundle();
        parameters.putParcelable(EVENTS_TAG, Parcels.wrap(new Events(events)));
        fragment.setArguments(parameters);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        events = ((Events) Parcels.unwrap(args.getParcelable(EVENTS_TAG))).getEvents();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new EventStaggeredAdapter(events, getActivity());
        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        eventsView.setAdapter(adapter);
        eventsView.setLayoutManager(manager);
    }
}
