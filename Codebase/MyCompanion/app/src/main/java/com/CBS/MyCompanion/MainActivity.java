package com.CBS.MyCompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Up Screen Transition Animation of Activity Page
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(true);

        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav);
        bottomNavView.setSelectedItemId(R.id.bottom_nav_home);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.END);

                //Navigation switch case to determine which page was selected
                switch (id)
                {
                    case R.id.nav_home:
                        replaceFragmentWithAnimation(new HomeFragment());
                        break;
                    case R.id.nav_account:
                        replaceFragmentWithAnimation(new AccountFragment());
                        break;
                    case R.id.nav_calendar:
                        replaceFragmentWithAnimation(new CalendarFragment());
                        break;
                    case R.id.nav_journal:
                        Intent intent1 = new Intent(MainActivity.this, JournalActivity.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        break;
                    case R.id.nav_help:
                        replaceFragmentWithAnimation(new ProfHelpFragment());
                        break;
                    case R.id.nav_settings:
                        replaceFragmentWithAnimation(new SettingsFragment());
                        break;
                    case R.id.nav_tracker:
                        replaceFragmentWithAnimation(new TrackerFragment());
                        break;
                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "Share Pop Up Under Construction", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        //TODO: Fix bottom navigation menu highlight
        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.bottom_nav_calendar:
                        Intent intent2 = new Intent(MainActivity.this, CalendarActivity.class);
                        startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        break;
                    case R.id.bottom_nav_tracker:
                        Intent intent3 = new Intent(MainActivity.this, TrackerActivity.class);
                        startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        break;
                    case R.id.bottom_nav_home:
                        replaceFragmentWithAnimation(new HomeFragment());
                        break;
                    case R.id.bottom_nav_journal:
                        Intent intent1 = new Intent(MainActivity.this, JournalActivity.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        break;
                    case R.id.bottom_nav_menu:
                        drawerLayout.openDrawer(GravityCompat.END);
                        break;
                }
                return true;
            }
        });

    }

//    Swap existing fragment page with a new fragment page
    private void replaceFragmentWithAnimation(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right,R.anim.slide_out_left);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

}