package com.oraby.egyptiantourguide.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.ui.auth.SplashScrean;
import com.oraby.egyptiantourguide.ui.main.artifacts.artifacts_fragment;
import com.oraby.egyptiantourguide.ui.main.home.home_fragment;
import com.oraby.egyptiantourguide.ui.main.hotels.hotels_fragment;
import com.oraby.egyptiantourguide.ui.main.profile.profile_fragment;
import com.oraby.egyptiantourguide.ui.main.resturants.resturants_fragments;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    ActionBar  actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.home_nav:
                                selectedFragment = home_fragment.newInstance();
                                break;
                            case R.id.hotel_nav:
                                selectedFragment = hotels_fragment.newInstance();
                                break;
                            case R.id.resturant_nav:
                                selectedFragment = resturants_fragments.newInstance();
                                break;

                            case R.id.artifact_nav:
                                selectedFragment = artifacts_fragment.newInstance();
                                break;

                            case R.id.profile_nav:
                                selectedFragment = profile_fragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, home_fragment.newInstance());
        transaction.commit();

    }
}
