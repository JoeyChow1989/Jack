package com.example.administrator.design_reg5137.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.administrator.design_reg5137.R;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_new);


        //viewpager & tabs
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SwitchesFragment.newInstance(0), "swiches");
        adapter.addFragment(ButtonFragment.newInstance(1), "botton");
        adapter.addFragment(ProgressFragment.newInstance(2), "progress");
        adapter.addFragment(TextfieldFragment.newInstance(3), "textfield");
        adapter.addFragment(SliderFragment.newInstance(4), "slider");
        adapter.addFragment(SpinnerFragment.newInstance(5), "spinner");
        adapter.addFragment(DialogsFragment.newInstance(6), "dialogs");
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.nav_new:
                break;
            case R.id.nav_xingan:
                break;
            case R.id.nav_mr:
                break;
            case R.id.nav_sw:
                break;
            case R.id.nav_xz:
                break;
            case R.id.nav_wallpaper:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_personal:
                break;
        }
        toolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> mFragments = new LinkedList<>();
        private List<String> mTitles = new LinkedList<>();

        public MyFragmentPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTitles.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragments.size();
        }
    }

}
