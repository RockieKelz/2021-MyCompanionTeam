package com.CBS.MyCompanion;

import com.CBS.MyCompanion.Data.Logs.CheckUpEntry;
import com.CBS.MyCompanion.Data.Logs.DiaryComponent;
import com.CBS.MyCompanion.Data.Logs.Emotions;
import com.CBS.MyCompanion.Data.Logs.JournalEntry;
import com.CBS.MyCompanion.Data.Logs.Log;
import com.CBS.MyCompanion.Data.UserAccount;
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

    public static void AddLog(Log log) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        Timestamp timestamp = Timestamp.now();
        String year = new SimpleDateFormat("yyyy")
                .format(timestamp.toDate());
        String month = new SimpleDateFormat("MM")
                .format(timestamp.toDate());
        String day = new SimpleDateFormat("dd")
                .format(timestamp.toDate());

        Map<String, Integer> rating = new HashMap<>();
        rating.put("Rating", log.GetCheckUp().GetRating());

        DocumentReference userData = database.collection("User_Data")
                .document(user.getUid()).collection("Logs")
                .document(year).collection(month).document(day);
        userData.set(rating);

        // Adds the Emotions as an array in Firestore
        {
            Vector<Integer> emotions = new Vector<Integer>();
            for (Emotions e : log.GetCheckUp().GetEmotions()) {
                emotions.add(e.getIndex());
            }
            userData.update("Emotions",emotions);
        }

        // Adds the Journal Entries
        {
            /*
            if (log.GetJournal().GetComponents().size() == 1) {
                Map<String, String> freeWrite = new HashMap<>();
                freeWrite.put("Free Write", log.GetJournal().GetComponents()
                        .firstElement().GetResponse());
                userData.collection("Journals")
                        .document("Free Write")
                        .set(freeWrite);
            } else if (log.GetJournal().GetComponents().size() > 1) {
                Map<String, String> guided = new HashMap<>();
                for (DiaryComponent c: log.GetJournal().GetComponents()) {
                    guided.put(c.GetQuestion(), c.GetResponse());
                }
                userData.update("Guided", guided);
            }
             */
        }
    }

    public static Log GetLog(Date _date) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        Log log = new Log();
        CheckUpEntry checkUp = new CheckUpEntry();
        JournalEntry journal = new JournalEntry();

        String date = new SimpleDateFormat("MM-dd-yyyy").format(_date);

        DocumentReference userData = database.collection("User_Data")
                .document(user.getUid()).collection("Logs")
                .document(date);
        DocumentSnapshot document = userData.get().getResult();

        Integer rating = Integer.parseInt(document.getString("Rating"));

        Vector<String> emotions = (Vector<String>) document.get("Emotions");
        checkUp.SetRating(rating);

        return log;
    }


}

/*TODO:
    Functions to add:
        1. Add a log to the users database
        2. Search the database for a specific log
        3. Delete specific Logs
        4. Delete a profile
        5. Edit Data in a log

 */
