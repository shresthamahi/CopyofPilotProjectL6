package com.example.feb8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 0;
    int fragmentNum = 0;
    int count=0;
    final static String CURRENT_FRAGMENT="0";
    //the logic layer
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    FragmentManager fm =getSupportFragmentManager();
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            count=1;
            fragmentNum = savedInstanceState.getInt(CURRENT_FRAGMENT);

        }
        //Imp to set up manifest permissions and Do Not Disturb Notification Policy
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
            }
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //redirects notification Manager screen of your smartphone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intents = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intents);
        }

        /**
         *Initially setting up some important manifest permissions and Do Not Disturb Notification
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle=setupDrawerToggle();
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        if (count==0){
        FragmentTransaction ft9= fm.beginTransaction();
        ft9.replace(R.id.flContent,new HomeFragment());
        ft9.commit();
            }

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );

    }

    private void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment= null;
        Class fragmentClass = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                fragmentNum=0;
                FragmentTransaction fgt= fragmentManager.beginTransaction();
                fgt.replace(R.id.flContent,new HomeFragment());
                fgt.commit();
                break;
            case R.id.nav_first_fragment:
                fragmentNum=2;
                FragmentTransaction ft= fragmentManager.beginTransaction();
                ft.replace(R.id.flContent,new DriveMode());
                ft.commit();
                break;
            case R.id.nav_second_fragment:
                fragmentNum=3;
                FragmentTransaction ft2= fragmentManager.beginTransaction();
                ft2.replace(R.id.flContent,new TimerFragment());
                ft2.commit();
                break;
            case R.id.nav_third_fragment:

                Intent intent= new Intent(MainActivity.this,User_Dashboard.class);
                startActivity(intent);
                break;

        }


        // Insert the fragment by replacing any existing fragment


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_FRAGMENT,fragmentNum);
    }
}