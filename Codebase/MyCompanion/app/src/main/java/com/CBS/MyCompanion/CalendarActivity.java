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
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class CalendarActivity extends AppCompatActivity {


    //CalendarView object for calender uses
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Up Screen Transition Animation of Activity Page
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(true);

        setContentView(R.layout.activity_calendar);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        //Commented out following line #Alex Roeder
        //Dont know if this line is nessesary or what it is for exactly, with line commented out navigation leads to
        //calender now loads calendar. 
        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CalendarFragment()).commit();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav);
        bottomNavView.setSelectedItemId(R.id.bottom_nav_calendar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                        Intent intent1 = new Intent(CalendarActivity.this, JournalActivity.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(CalendarActivity.this).toBundle());
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
                        Toast.makeText(CalendarActivity.this, "Share Pop Up Under Construction", Toast.LENGTH_SHORT).show();
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
                        replaceFragmentWithAnimation(new CalendarFragment());
                        break;
                    case R.id.bottom_nav_tracker:
                        Intent intent2 = new Intent(CalendarActivity.this, TrackerActivity.class);
                        startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(CalendarActivity.this).toBundle());
                        break;
                    case R.id.bottom_nav_home:
                        Intent intent3 = new Intent(CalendarActivity.this, MainActivity.class);
                        startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(CalendarActivity.this).toBundle());
                        break;
                    case R.id.bottom_nav_journal:
                        Intent intent1 = new Intent(CalendarActivity.this, JournalActivity.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(CalendarActivity.this).toBundle());
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right,R.anim.slide_out_left);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();

    }
}