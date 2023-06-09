package com.CBS.MyCompanion;

import androidx.annotation.NonNull;

import com.CBS.MyCompanion.Data.Logs.CheckUpEntry;
import com.CBS.MyCompanion.Data.Logs.DiaryComponent;
import com.CBS.MyCompanion.Data.Logs.Emotions;
import com.CBS.MyCompanion.Data.Logs.JournalEntry;
import com.CBS.MyCompanion.Data.Logs.Log;
import com.CBS.MyCompanion.Data.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class Database {

    public static void AddUser(UserAccount currentUser){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();

        Map<Integer, Object> Logs = new HashMap<>();

        CollectionReference dbUsers = database.collection("User_Data");
        dbUsers.document(user.getUid()).set(currentUser);
        dbUsers.document(user.getUid()).collection("Logs")
                .document("Initial").set(Logs);

    }

    public static void AddCheckUp(CheckUpEntry checkUp) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        Timestamp timestamp = Timestamp.now();
        String year = new SimpleDateFormat("yyyy")
                .format(timestamp.toDate());
        String month = new SimpleDateFormat("MM")
                .format(timestamp.toDate());
        String day = new SimpleDateFormat("dd")
                .format(timestamp.toDate());

        /*
        int y = log.GetDate().get(Calendar.YEAR);
        int m = log.GetDate().get(Calendar.MONTH);
        int d = log.GetDate().get(Calendar.DAY_OF_MONTH);

        String year = String.valueOf(y);
        String month = String.format("%02d",(m + 1));
        String day = String.format("%02d",d);
         */


        Map<String, Integer> rating = new HashMap<>();
        rating.put("Rating", checkUp.GetRating());

        DocumentReference userData = database.collection("User_Data")
                .document(user.getUid()).collection("Logs")
                .document(year).collection(month).document(day);
        userData.set(rating);

        // Adds the Emotions as an array in Firestore
        {
            Vector<Integer> emotions = new Vector<Integer>();
            for (Emotions e : checkUp.GetEmotions()) {
                emotions.add(e.getIndex());
            }
            userData.update("Emotions",emotions);
        }

    }

    public static void AddJournal(JournalEntry journal) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        Timestamp timestamp = Timestamp.now();
        String year = new SimpleDateFormat("yyyy")
                .format(timestamp.toDate());
        String month = new SimpleDateFormat("MM")
                .format(timestamp.toDate());
        String day = new SimpleDateFormat("dd")
                .format(timestamp.toDate());


        CollectionReference userData = database.collection("User_Data")
                .document(user.getUid()).collection("Logs")
                .document(year).collection(month).document(day).collection("Journal");

        // Adds the Journal Entries

        if (journal.GetComponents().size() == 1) {
            Map<String, String> freeWrite = new HashMap<>();
            freeWrite.put("Free Write", journal.GetComponents()
                    .firstElement().GetResponse());
            userData.document("Free Write").set(freeWrite);
        } else if (journal.GetComponents().size() > 1) {
            Map<String, String> guided = new HashMap<>();
            for (DiaryComponent c: journal.GetComponents()) {
                guided.put(c.GetQuestion(), c.GetResponse());
                userData.document("Guided").update(c.GetQuestion(), c.GetResponse());
            }
        }
    }

    public static Log GetLog(Calendar _date) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        Log log = new Log();
        CheckUpEntry checkUp = new CheckUpEntry();
        JournalEntry journal = new JournalEntry();

        String date = new SimpleDateFormat("MM-dd-yyyy").format(_date.getTime());

        DocumentReference userData = database.collection("User_Data")
                .document(user.getUid()).collection("Logs")
                .document(date);
        DocumentSnapshot document = userData.get().getResult();

        Integer rating = Integer.parseInt(document.getString("Rating"));

        Vector<String> emotions = (Vector<String>) document.get("Emotions");
        checkUp.SetRating(rating);

        return log;
    }

    public static DiaryComponent GetJournal(Date date) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        String year = new SimpleDateFormat("yyyy")
                .format(date);
        String month = new SimpleDateFormat("MM")
                .format(date);
        String day = new SimpleDateFormat("dd")
                .format(date);
        CollectionReference journals = database.collection("User_Data")
                .document(user.getUid()).collection("Logs")
                .document(year).collection(month).document(day).collection("Journal");

        final String[] response = {""};

        journals.document("FreeWrite").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        response[0] = document.getString("Free Write");

                    }
                }
            }
        });
        String r = response[0];

        DiaryComponent diaryComponent = new DiaryComponent("Free Write", r);

        return diaryComponent;
    }

    public static DiaryComponent GetJournal(String year, String month, String day) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();

        CollectionReference journals = database.collection("User_Data")
                .document(user.getUid()).collection("Logs")
                .document(year).collection(month).document(day).collection("Journal");

        final String[] response = {""};

        journals.document("FreeWrite").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        response[0] = document.getString("Free Write");

                    }
                }
            }
        });
        String r = response[0];

        DiaryComponent diaryComponent = new DiaryComponent("Free Write", r);

        return diaryComponent;
    }

}