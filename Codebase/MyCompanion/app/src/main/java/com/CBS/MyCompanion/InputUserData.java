package com.CBS.MyCompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.CBS.MyCompanion.Data.Logs.CheckUpEntry;
import com.CBS.MyCompanion.Data.Logs.DiaryComponent;
import com.CBS.MyCompanion.Data.Logs.Emotions;
import com.CBS.MyCompanion.Data.Logs.JournalEntry;
import com.CBS.MyCompanion.Data.Logs.Log;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.CBS.MyCompanion.Data.UserAccount;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class InputUserData extends AppCompatActivity {

    private EditText firstnameEditText, lastNameEditText;
    private Button ctnButton;
    public String firstName, lastName;
    public FirebaseAuth mAuth;
    public FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_input_user_data);

        firstnameEditText = findViewById(R.id.activity_inputUserData_firstName);
        lastNameEditText = findViewById(R.id.activity_inputUserData_lastName);
        ctnButton = findViewById(R.id.activity_inputUserData_ctnButton);

        firstName = firstnameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        ctnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void addDataToFirestore() {


        UserAccount currentUser = new UserAccount(firstnameEditText.getText().toString(),
                lastNameEditText.getText().toString());
        currentUser.SetId(mAuth.getUid());
        Map<Integer, Object> Logs = new HashMap<>();

        Database.AddUser(currentUser);

    }

    public void Login() {
        if (!TextUtils.isEmpty(firstName) || !TextUtils.isEmpty(lastName)){
            Toast.makeText(getApplicationContext(),
                    "Please fill out required fields",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        addDataToFirestore();
        BuildTestLog();

        Intent i
                = new Intent(InputUserData.this,
                MainActivity.class);
        startActivity(i);

    }

    public void BuildTestLog() {
        Timestamp timestamp = Timestamp.now();
        String defaultFreeWrite = "This is a default Journal Entry";
        JournalEntry journalEntry = new JournalEntry();
        DiaryComponent diary = new DiaryComponent("Free Write", defaultFreeWrite);
        journalEntry.AddComponent(diary);
        Integer rating = 3;
        Vector<Emotions> emotions = new Vector<>();
        emotions.add(Emotions.HAPPY);
        emotions.add(Emotions.LONELY);
        CheckUpEntry checkUp = new CheckUpEntry();
        checkUp.SetEmotions(emotions);
        checkUp.SetRating(rating);

        Log log = new Log();
        log.SetCheckUp(checkUp);
        log.SetJournal(journalEntry);

        Database.AddLog(log);

    }

}