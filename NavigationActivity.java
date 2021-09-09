package com.example.adi.rejsekortv1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adi.rejsekortv1.fragments.balance_fragment;
import com.example.adi.rejsekortv1.fragments.bankAccount_fragment;
import com.example.adi.rejsekortv1.fragments.home_fragment;
import com.example.adi.rejsekortv1.fragments.travelHistory_fragment;
import com.example.adi.rejsekortv1.fragments.travel_fragment;
import com.example.adi.rejsekortv1.fragments.userProfile_fragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    // Main menus for switiching fragments
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_Travel) {
            fragment = new travel_fragment();

        } else if (id == R.id.nav_chargeupBalance) {
            fragment = new balance_fragment();

        } else if (id == R.id.nav_mytavel) {
            fragment = new travelHistory_fragment();

        } else if (id == R.id.nav_bankaccount) {
            fragment = new bankAccount_fragment();

        } else if (id == R.id.nav_settings) {
            fragment = new userProfile_fragment();

        }

        if(fragment != null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_navigation, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // For Logout menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Logout(){
        // To format the username and password saved
        SharedPreferences sharedpreferences = getSharedPreferences("UIdPrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        // redirect user to LoginActivity
        Intent i = new Intent(NavigationActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
