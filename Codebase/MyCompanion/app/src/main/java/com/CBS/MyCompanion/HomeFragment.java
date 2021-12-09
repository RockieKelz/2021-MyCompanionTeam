package com.CBS.MyCompanion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FirebaseUser user;
    FirebaseFirestore database;
    SharedPreferences sharedPreferences;
    String name;
    int streak;

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

        //Update user information from firebase
        pullUserInfoFromFirebase(homeView);


        //Determine and display the date
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("EEEE, MMM d, yyyy");
        getTimeFromAndroid(cal, homeView, 0);

        getLoginStreak(cal, homeView);

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
    @SuppressLint("SetTextI18n")
    private void getLoginStreak(Calendar cal, View view)
    {
        /* Access the user's storage field and update their login streak
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("User_data").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null)
                {
                    streak = documentSnapshot.get("currentLoginStreak");
                }
            }
        }); */
        //Generate Login Streak
        Date yesterday;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("MM, dd, yyyy");

        //get the last sign in date and format it to a string
        Date lastLogin = new Date(Objects.requireNonNull(user.getMetadata()).getLastSignInTimestamp());
        String formatLogin = simpleDate.format(lastLogin);
        //check if the last sign in is the current date
        if (!formatLogin.equals(simpleDate.format(cal.getTime())))
        {
            //get yesterday's date and format it to a string
            cal.add(Calendar.DATE, -1);
            yesterday = cal.getTime();
            String formatYesterday = simpleDate.format(yesterday);
            //determine if user's login streak should reset or go up
            if (formatYesterday.equals(formatLogin))
            {
                ++streak;
            }
            else
            {
                streak=1;
            }
        }
        /*Update their streak count
        FirebaseFirestore.getInstance().collection("User_data").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("currentLoginStreak:", streak);*/
        TextView streakCount = view.findViewById(R.id.homeStreaks);
        streakCount.setText("Current Login Streak: " + streak);

    }
    public void pullUserInfoFromFirebase(View view)
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        TextView homeName = view.findViewById(R.id.userName_home);
        CircleImageView homePic = view.findViewById(R.id.homePic);
        CircleImageView headerProfileImage = view.findViewById(R.id.header_profile_picture);

        StorageReference profileRef;
        //adds user's name and picture to homepage
        if (user !=null) {
           name = user.getDisplayName();
           homeName.setText((CharSequence) name);
           profileRef = FirebaseStorage.getInstance().getReference().child("users/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()+"/profile.jpg");
           profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(homePic));
        }
        else
        {
            homeName.setText(requireActivity().getResources().getString(R.string.bypass_name));
            homePic.setImageResource(R.drawable.default_profile_pic);
            //headerProfileImage.setImageResource(R.drawable.default_profile_pic);
        }

    }

}