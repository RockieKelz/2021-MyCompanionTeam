package com.CBS.MyCompanion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Up Screen Transition Animation of Activity Page
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tracker);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TrackerFragment()).commit();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav);
        bottomNavView.setSelectedItemId(R.id.bottom_nav_tracker);
        //Display users name and image in navigation drawer
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            View header = navigationView.getHeaderView(0);
            CircleImageView headerProfileImage = header.findViewById(R.id.header_profile_picture);
            StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + "/profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(headerProfileImage));
            TextView headerUsername = header.findViewById(R.id.userName_header);
            headerUsername.setText(user.getDisplayName() + "'s Companion");
        }
        TabLayout tabLayout_tracker = findViewById(R.id.tabs_tracker);
        ViewPager2 pager2_tracker = findViewById(R.id.viewPager2_tracker);
        FragmentManager fManager = getSupportFragmentManager();
        ViewPager2Adapter_tracker adapter = new ViewPager2Adapter_tracker(fManager, getLifecycle());
        pager2_tracker.setAdapter(adapter);

        tabLayout_tracker.addTab(tabLayout_tracker.newTab().setText("Week"));
        tabLayout_tracker.addTab(tabLayout_tracker.newTab().setText("Month"));
        tabLayout_tracker.addTab(tabLayout_tracker.newTab().setText("Year"));

        tabLayout_tracker.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                pager2_tracker.setCurrentItem(tab.getPosition());
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

        pager2_tracker.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout_tracker.selectTab(tabLayout_tracker.getTabAt(position));
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
                        Intent intent1 = new Intent(TrackerActivity.this, MainActivity.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(TrackerActivity.this).toBundle());
                        break;
                    case R.id.nav_account:
                        tabLayout_tracker.setVisibility(View.GONE);
                        pager2_tracker.setVisibility(View.GONE);
                        replaceFragmentWithAnimation(new AccountFragment());
                        break;
                    case R.id.nav_calendar:
                        Intent intent2 = new Intent(TrackerActivity.this, CalendarActivity.class);
                        startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(TrackerActivity.this).toBundle());
                        break;
                    case R.id.nav_journal:
                        Intent intent3 = new Intent(TrackerActivity.this, JournalActivity.class);
                        startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(TrackerActivity.this).toBundle());
                        break;
                    case R.id.nav_help:
                        tabLayout_tracker.setVisibility(View.GONE);
                        pager2_tracker.setVisibility(View.GONE);
                        replaceFragmentWithAnimation(new ProfHelpFragment());
                        break;
                    case R.id.nav_settings:
                        tabLayout_tracker.setVisibility(View.GONE);
                        pager2_tracker.setVisibility(View.GONE);
                        replaceFragmentWithAnimation(new SettingsFragment());
                        break;
                    case R.id.nav_tracker:
                        replaceFragmentWithAnimation(new TrackerFragment());
                        break;
                    case R.id.nav_share:
                        Toast.makeText(TrackerActivity.this, "Share Pop Up Under Construction", Toast.LENGTH_SHORT).show();
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
                        Intent intent2 = new Intent(TrackerActivity.this, CalendarActivity.class);
                        startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(TrackerActivity.this).toBundle());
                        break;
                    case R.id.bottom_nav_tracker:
                        replaceFragmentWithAnimation(new TrackerFragment());
                        break;
                    case R.id.bottom_nav_home:
                        Intent intent3 = new Intent(TrackerActivity.this, MainActivity.class);
                        startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(TrackerActivity.this).toBundle());
                        break;
                    case R.id.bottom_nav_journal:
                        Intent intent1 = new Intent(TrackerActivity.this, JournalActivity.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(TrackerActivity.this).toBundle());
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