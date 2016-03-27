package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.event.GetRideEvents;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveRideEvents;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.service.BusProvider;
import com.noveo.android.internship.ridetogether.app.ui.fragment.EventsListFragment;
import com.noveo.android.internship.ridetogether.app.ui.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.EventsPagerAdapter;
import com.noveo.android.internship.ridetogether.app.ui.view.holder.EventStaggeredViewHolder;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventStaggeredViewHolder.EventClickListener {
    @Bind(R.id.viewpager)
    ViewPager pager;
    @Bind(R.id.eventslist_toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    private EventsPagerAdapter adapter;
    private List<Event> events = new ArrayList<>();
    private Bus bus = BusProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    private void setupViewPager(ReceiveRideEvents event) {
        adapter = new EventsPagerAdapter(getSupportFragmentManager());
        String[] titles = getResources().getStringArray(R.array.fragments_titles);

        this.events = event.getEvents();

        EventUtil.Range range;
        for (String title : titles) {
            range = EventUtil.Range.ANY;
            if (title.equals(getResources().getString(R.string.today))) {
                range = EventUtil.Range.TODAY;
            } else if (title.equals(getResources().getString(R.string.tomorrow))) {
                range = EventUtil.Range.TOMORROW;
            } else if (title.equals(getResources().getString(R.string.this_week))) {
                range = EventUtil.Range.WEEK;
            } else if (title.equals(getResources().getString(R.string.this_month))) {
                range = EventUtil.Range.MONTH;
            }
            adapter.addFragment(EventsListFragment.newInstance(EventUtil.getDateInRange(events, range)), title);
        }
        pager.setAdapter(adapter);
    }

    @Subscribe
    public void onReceiveEvents(ReceiveRideEvents event) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager(event);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        bus.post(new GetRideEvents());
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onEventClick(int position) {
        startActivity(EventActivity.createIntent(this, events.get(position)));
    }
}
