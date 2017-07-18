package com.example.nishantgahlawat.themoviedbapp.MainPage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nishantgahlawat.themoviedbapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTAG";

    private TabLayout mTabs;
    private ViewPager mViewPager;
    private FloatingActionButton fab;

    private MainDiscoverMovieFragment discoverMovieFragment;
    private MainDiscoverTVFragment discoverTVFragment;

    private int selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TheMovieDB - Discover");

        mViewPager = (ViewPager)findViewById(R.id.mainViewPager);

        fab = (FloatingActionButton) findViewById(R.id.mainFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab==0){
                    discoverMovieFragment.scrollToTop();
                }
                else if(selectedTab==1){
                    discoverTVFragment.scrollToTop();
                }
            }
        });

        setupViewPager(mViewPager);

        mTabs = (TabLayout)findViewById(R.id.mainTabs);
        mTabs.setupWithViewPager(mViewPager);

        setupTabIcons();

        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupTabIcons() {
        TextView textViewMovies = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout,null);
        textViewMovies.setText("Movies");
        textViewMovies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_movie_black_24dp,0,0,0);
        mTabs.getTabAt(0).setCustomView(textViewMovies);

        TextView textViewTV = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout,null);
        textViewTV.setText("TV Shows");
        textViewTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tv_black_24dp,0,0,0);
        mTabs.getTabAt(1).setCustomView(textViewTV);
    }

    private void setupViewPager(ViewPager mViewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());

        discoverMovieFragment = new MainDiscoverMovieFragment();
        discoverMovieFragment.setFab(fab);
        adapter.addFragment(discoverMovieFragment ,"Movies");

        discoverTVFragment = new MainDiscoverTVFragment();
        discoverTVFragment.setFab(fab);
        adapter.addFragment(discoverTVFragment,"TV Shows");

        mViewPager.setAdapter(adapter);
    }

    class MainViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
