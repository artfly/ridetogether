package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.event.RideClickEvent;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.ui.fragment.EventsListFragment;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.EventsPagerAdapter;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    ViewPager pager;
    @Bind(R.id.eventslist_toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
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

        setupActionBar();
        setupDrawer();

        setupViewPager();
        tabLayout.setupWithViewPager(pager);
    }

    private void setupViewPager() {
        adapter = new EventsPagerAdapter(getSupportFragmentManager());
        String[] titles = getResources().getStringArray(R.array.fragments_titles);

        EventUtil.Range range;
        for (String title : titles) {
            range = EventUtil.Range.ANY;
            if (TextUtils.equals(title, getResources().getString(R.string.today))) {
                range = EventUtil.Range.TODAY;
            } else if (TextUtils.equals(title, getResources().getString(R.string.tomorrow))) {
                range = EventUtil.Range.TOMORROW;
            } else if (TextUtils.equals(title, getResources().getString(R.string.this_week))) {
                range = EventUtil.Range.WEEK;
            } else if (TextUtils.equals(title, getResources().getString(R.string.this_month))) {
                range = EventUtil.Range.MONTH;
            }
            adapter.addFragment(EventsListFragment.newInstance(EventUtil.getDateInRange(events, range)), title);
        }
        pager.setAdapter(adapter);
    }

    private void setupDrawer() {
        //bug in support lib, cannot use butterknife or findviewbyid
        View headerView = navigationView.getHeaderView(0);
        final TextView username = (TextView) headerView.findViewById(R.id.header_username);
        final ImageView userimage = (ImageView) headerView.findViewById(R.id.header_userimage);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
        username.setText(RideTogetherStub.username);
        Glide.with(this)
                .load(RideTogetherStub.userImagePath)
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
