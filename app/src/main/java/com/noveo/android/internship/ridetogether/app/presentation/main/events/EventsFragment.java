package com.noveo.android.internship.ridetogether.app.presentation.main.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.Events;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseViewFragment;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.view.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.view.bus.event.RideClickEvent;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter.EventStaggeredAdapter;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter.ItemsClickSupport;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends BaseViewFragment implements EventListView {
    private static final String EVENTS_TAG = "EVENTS_TAG";
    private static final String RANGE_TAG = "RANGE_TAG";
    @Bind(R.id.events_list)
    RecyclerView eventsView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private List<Event> events = new ArrayList<>();
    private EventStaggeredAdapter adapter;
    private EventUtil.Range range;

    private EventsPresenter presenter = new EventsPresenter();

    public static EventsFragment newInstance(List<Event> events, EventUtil.Range range) {
        EventsFragment fragment = new EventsFragment();
        Bundle parameters = new Bundle();
        parameters.putParcelable(EVENTS_TAG, Parcels.wrap(new Events(events)));
        parameters.putSerializable(RANGE_TAG, range);
        fragment.setArguments(parameters);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        events = ((Events) Parcels.unwrap(args.getParcelable(EVENTS_TAG))).getEvents();
        range = (EventUtil.Range) args.getSerializable(RANGE_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setupSwipe();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new EventStaggeredAdapter(events, getActivity());
        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        eventsView.setAdapter(adapter);
        eventsView.setLayoutManager(manager);
        ItemsClickSupport.addTo(eventsView)
                .setOnItemClickListener(
                        (recyclerView, position, v) -> {
                            BusProvider.getInstance().post(new RideClickEvent(events.get(position)));
                        });
    }

//    private void setupSwipe() {
//        swipeLayout.setOnRefreshListener(() -> presenter.loadEventsInRange(range));
//    }

    @Override
    public void showEvents(List<Event> events) {
        swipeLayout.setRefreshing(false);
        this.events.clear();
        this.events.addAll(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void attachPresenter() {
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }
}
