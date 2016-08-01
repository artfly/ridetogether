package com.noveo.android.internship.ridetogether.app.presentation.main.events;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.maps.model.LatLng;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.event_creation.EventCreationActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.home.SplashScreenActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.login.LoginActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.route_creation.RouteCreationActivity;
import com.noveo.android.internship.ridetogether.app.utils.LoginUtil;
import com.noveo.android.internship.ridetogether.app.view.bus.event.RideClickEvent;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter.EventsPagerAdapter;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    ViewPager pager;
    @Bind(R.id.eventslist_toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private LatLng currentPosition;

    @OnClick(R.id.new_event_fab)
    public void createRoute() {
        startActivity(new Intent(this, EventCreationActivity.class));
    }

    @Bind(R.id.nav_view)
    NavigationView navigationView;
    private EventsPagerAdapter adapter;
    private List<Event> events = new ArrayList<>();
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        events = IntentUtil.getEvents(getIntent());
        currentPosition = IntentUtil.getCurrentPosition(getIntent());

        setupActionBar();
        setupDrawer();

        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new EventsPagerAdapter(getSupportFragmentManager());
        String[] titles = getResources().getStringArray(R.array.fragments_titles);

        EventUtil.Range range;
        for (String title : titles) {
            range = EventUtil.getRange(title, this);
            adapter.addFragment(EventsFragment.newInstance(EventUtil.getEventsInRange(events, range), range), title);
        }
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
    }

    private void setupDrawer() {
        //bug in support lib, cannot use butterknife or findviewbyid
        View headerView = navigationView.getHeaderView(0);
        final TextView username = (TextView) headerView.findViewById(R.id.header_username);
        final ImageView userimage = (ImageView) headerView.findViewById(R.id.header_userimage);
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.logout:
                    LoginUtil.deleteCredentials(this);
                    startActivity(new Intent(this, SplashScreenActivity.class));
            }
            return true;
        });
        username.setText(LoginUtil.getUsername(this));
        File file = LoginUtil.getUserImage(this);
        if (!file.exists()) {
            Glide.with(this)
                    .load(R.drawable.ic_action_perm_identity)
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(userimage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circular =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circular.setCircular(true);
                            userimage.setImageDrawable(circular);
                        }
                    });
            userimage.setOnClickListener(v -> startActivity(new Intent(EventsActivity.this, LoginActivity.class)));
        } else {
            Glide.with(this)
                    .load(file)
                    .asBitmap()
                    .centerCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new BitmapImageViewTarget(userimage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circular =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circular.setCircular(true);
                            userimage.setImageDrawable(circular);
                        }
                    });
        }
    }

    private void setupActionBar() {
        toolbar.setTitle(getString(R.string.events));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEventClick(RideClickEvent event) {
        startActivity(IntentUtil.createIntent(this, event.getEvent()));
    }
}
