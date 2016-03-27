package com.noveo.android.internship.ridetogether.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.Events;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.EventStaggeredAdapter;
import com.noveo.android.internship.ridetogether.app.ui.view.holder.EventStaggeredViewHolder.EventClickListener;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class EventsListFragment extends Fragment {
    private static final String EVENTS_TAG = "EVENTS_TAG";
    @Bind(R.id.events_list)
    RecyclerView eventsView;
    private List<Event> events = new ArrayList<>();
    private EventStaggeredAdapter adapter;
    private EventClickListener listener;

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
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        ButterKnife.bind(this, view);
        try {
            this.listener = (EventClickListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(getClass().getSimpleName(), "The activity should implement EventClickListener interface", e);
        }
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        adapter = new EventStaggeredAdapter(events, getActivity(), listener);
        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        eventsView.setAdapter(adapter);
        eventsView.setLayoutManager(manager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
