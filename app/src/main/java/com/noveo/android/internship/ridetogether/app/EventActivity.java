package com.noveo.android.internship.ridetogether.app;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.event.GetRideEvent;
import com.noveo.android.internship.ridetogether.app.event.ReceiveRideEvent;
import com.noveo.android.internship.ridetogether.app.event.ReceiveUnsubscribeEvent;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.view.adapter.EventAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    @Bind(R.id.event_list)
    RecyclerView eventView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private List<Object> items = new ArrayList<>();
    private EventAdapter adapter;
    private Bus bus = BusProvider.getInstance();

    @Subscribe
    public void onReceiveRideEvent(ReceiveRideEvent event) {
        EventUtil.updateItems(items, event.getRideEvent(), this);
        adapter.notifyDataSetChanged();
        collapsingToolbar.setTitle(event.getRideEvent().getTitle());
    }

    @Subscribe
    public void onReceiveUnsubscribeEvent(ReceiveUnsubscribeEvent event) {
        EventUtil.removeUser(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_name));

        eventView.setHasFixedSize(true);
        eventView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(items, this);
        eventView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        bus.post(new GetRideEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO : cancel calls
        bus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
