package com.example.marcos.mybrotherhoodapp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marcos.mybrotherhoodapp.fragments.GalleryFragment;
import com.example.marcos.mybrotherhoodapp.fragments.InfoFragment;
import com.example.marcos.mybrotherhoodapp.fragments.InitialFragment;
import com.example.marcos.mybrotherhoodapp.fragments.LatestFragment;
import com.example.marcos.mybrotherhoodapp.fragments.SettingsFragment;
import com.example.marcos.mybrotherhoodapp.fragments.SuggestionsFragment;
import com.example.marcos.mybrotherhoodapp.fragments.ShareFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    static private Class fragmentClass = InitialFragment.class;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v(TAG, "onCreate");

        //set preferences default values
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(fragmentClass);
    }

    @Override
    public void onBackPressed() {

        Log.v(TAG, "onBackPressed");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.v(TAG, "onCreateOptionsMenu");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.v(TAG, "onOptionsItemSelected");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            getFragmentManager().beginTransaction().replace(R.id.flContent, new SettingsFragment()).addToBackStack(null).commit();
            //fragmentClass = SettingsFragment.class;
            //setFragment(fragmentClass);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Log.v(TAG, "onNavigationItemSelected");

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_latest) {
            fragmentClass = LatestFragment.class;
        } else if (id == R.id.nav_information) {
            fragmentClass = InfoFragment.class;
        } else if (id == R.id.nav_gallery) {
            fragmentClass = GalleryFragment.class;
        } else if (id == R.id.nav_manage) {
            fragmentClass = SettingsFragment.class;
        } else if (id == R.id.nav_share) {
            fragmentClass = ShareFragment.class;
        } else if (id == R.id.nav_suggestions) {
            fragmentClass = SuggestionsFragment.class;
        }

        setFragment(fragmentClass);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void setFragment(Class fragmentClass) {

        Log.v(TAG, "setFragment");

        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
             fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }
}
