package com.CBS.MyCompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.CBS.MyCompanion.Data.UserAccount;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;
    public static final String currentStreak = "streak";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        Bundle args = new Bundle();
         fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView name = homeView.findViewById(R.id.userName_home);
        name.setText(this.getResources().getString(R.string.usernames_name));

        //Determine and display the date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("EEEE, MMM d, yyyy");
        getTimeFromAndroid(cal, homeView, 0);

        //Generate Login Streak
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("streak", Context.MODE_PRIVATE);
        int today = cal.get(Calendar.DAY_OF_YEAR);
        int lastDay = sharedPreferences.getInt("last Login", 0);
        int streak = sharedPreferences.getInt("current streak", 0);
        getLoginStreak(today,lastDay, streak, homeView);

        //Change the date
        ImageButton buttonPrevious = homeView.findViewById(R.id.button_homePrev);
        ImageButton buttonNext = homeView.findViewById(R.id.button_homeNext);
        //Move to previous date
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeFromAndroid(cal, homeView, -1);
            }
        });
        //Move to next date
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeFromAndroid(cal, homeView, +1);
            }
        });

        //Determine if the checkboxes are checked
        CheckBox journalCheckBox = (CheckBox) homeView.findViewById(R.id.home_checkBox1);
        //If log is completed
        //journalCheckBox.setChecked(true);
        CheckBox trackerCheckBox= (CheckBox) homeView.findViewById(R.id.home_checkBox2);
        //If log is completed
        //trackerCheckBox.setChecked(true);

        return homeView;
    }
    public void getTimeFromAndroid(Calendar cal,View view, int day)
    {
        cal.add(cal.DATE, day);
        int timeOfDaY = cal.get(Calendar.HOUR_OF_DAY);
        String userGreetings = null;
        SimpleDateFormat simpleDate = new SimpleDateFormat("EEEE, MMM d, yyyy");
        String formattedDate = simpleDate.format(cal.getTime());

        if (timeOfDaY >= 1 && timeOfDaY <12)
        {
            userGreetings = "Good Morning, ";
        }
        else if (timeOfDaY >= 12 && timeOfDaY < 17)
        {
            userGreetings = "Good Afternoon, ";
        }
        else
        {
            userGreetings = "Good Evening, ";
        }
        TextView greeting = view.findViewById(R.id.userGreeting);
        greeting.setText(userGreetings);
        TextView date = view.findViewById(R.id.homeDate);
        date.setText(formattedDate);
    }
    public void onCheckboxClicked (View view)
    {
        //Is the view checked
        boolean checked = ((CheckBox)view).isChecked();

    }
    private void getLoginStreak(int currentLogin, int lastLogin, int streak, View view)
    {
        if (lastLogin == currentLogin-1)
        {
            streak += 1;
            sharedPreferences.edit().putInt("last login", currentLogin).apply();
            sharedPreferences.edit().putInt("streak", streak).apply();
        }
        else
        {
            sharedPreferences.edit().putInt("last login", currentLogin).apply();
            sharedPreferences.edit().putInt("streak", 1).apply();
            streak=1;
        }
        TextView streakCount = view.findViewById(R.id.homeStreaks);
        streakCount.setText("Current Login Streak: " + streak);

    }
}