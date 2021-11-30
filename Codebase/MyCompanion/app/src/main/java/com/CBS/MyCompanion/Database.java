package com.CBS.MyCompanion;

import com.CBS.MyCompanion.Data.Logs.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Database {

    public static void AddLog(Log log) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();
        String date = Integer.toString((int)(log.GetDate().getTime())/1000);

        DocumentReference userData = database.collection("User_Data").document(user.getUid());
        userData.update(date, log);

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
