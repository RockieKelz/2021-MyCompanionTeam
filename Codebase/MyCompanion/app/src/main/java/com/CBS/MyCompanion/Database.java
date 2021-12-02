package com.CBS.MyCompanion;

import com.CBS.MyCompanion.Data.Logs.Log;
import com.CBS.MyCompanion.Data.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Database {

    public static void AddUser(UserAccount user){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Map<Integer, Object> Logs = new HashMap<>();

        CollectionReference dbUsers = database.collection("User_Data");
        dbUsers.document(mAuth.getUid()).set(user);
        dbUsers.document(mAuth.getUid()).collection("Logs")
                .document("Initial").set(Logs);

    }

    public static void AddLog(Log log) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        String date = Integer.toString((int)(log.GetDate().getTime())/1000);

        /*
            TODO:
                1. Add an array for the CheckUp Entry
                2. Add a map for "Free Write" or "Guided"
                    A. For "Free Write"
                        a. the field is the Time of Day
                        b. the value is the answer
                    B. For "Guided"
                        a. The field is the question
                        b. The value is the answer
                     ** There can only be one Guided response
         */

    }

    public static Log GetLog(Date _date) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        String date = Integer.toString((int) _date.getTime()/1000);

        DocumentReference userData = database.collection("User_data")
                .document(user.getUid());

        Log log = null;
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
