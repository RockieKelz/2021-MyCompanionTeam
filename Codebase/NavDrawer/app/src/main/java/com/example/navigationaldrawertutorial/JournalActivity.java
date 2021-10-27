package com.example.navigationaldrawertutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class JournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new JournalFragment()).commit();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav);
        bottomNavView.setSelectedItemId(R.id.bottom_nav_journal);

        //Todo: Fix TabLayout
       TabLayout tabLayout_journal = findViewById(R.id.tabs_journal);
        ViewPager2 pager2_journal = findViewById(R.id.viewPager2_journal);
        FragmentManager fManager = getSupportFragmentManager();
        ViewPager2Adapter adapter = new ViewPager2Adapter(fManager, getLifecycle());
        pager2_journal.setAdapter(adapter);

        tabLayout_journal.addTab(tabLayout_journal.newTab().setText("Journal"));
        tabLayout_journal.addTab(tabLayout_journal.newTab().setText("Check-Up"));

        tabLayout_journal.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                pager2_journal.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        pager2_journal.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout_journal.selectTab(tabLayout_journal.getTabAt(position));
            }
        });

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
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.nav_account:
                        replaceFragment(new AccountFragment());
                        break;
                    case R.id.nav_calendar:
                        replaceFragment(new CalendarFragment());
                        break;
                    case R.id.nav_journal:
                        replaceFragment(new JournalFragment());
                        break;
                    case R.id.nav_help:
                        replaceFragment(new ProfHelpFragment());
                        break;
                    case R.id.nav_settings:
                        replaceFragment(new SettingsFragment());
                        break;
                    case R.id.nav_tracker:
                        replaceFragment(new TrackerFragment());
                        break;
                    case R.id.nav_share:
                        Toast.makeText(JournalActivity.this, "Share Pop Up Under Construction", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        //TODO: Fix bottom navigation "Menu" highlight
        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.bottom_nav_calendar:
                        replaceFragment(new CalendarFragment());
                        break;
                    case R.id.bottom_nav_tracker:
                        replaceFragment(new TrackerFragment());
                        break;
                    case R.id.bottom_nav_home:
                        Intent intent1 = new Intent(JournalActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.bottom_nav_journal:
                        replaceFragment(new JournalFragment());
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
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}