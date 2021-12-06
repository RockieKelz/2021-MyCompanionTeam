
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/5/2021
 */

package com.CBS.MyCompanion.Data.Logs;

import java.util.Date;
import java.util.Calendar;
import java.util.Vector;

public class Log {

    // Properties
    private Vector<BaseEntry> Entries;
    private Calendar date;
    private boolean HasCheckUp;
    private boolean HasJournal;

    // METHODS //

    // Constructors

    // Copy Constructor
    Log(Log log){
        SetEntries(log.GetEntries());
        date = log.GetDate();
    }


    // Accessors
    public Vector<BaseEntry> GetEntries() { return Entries; }
    public Calendar GetDate() { return date; }
    public boolean CheckUpStatus() { return HasCheckUp; }
    public boolean JournalStatus() { return HasJournal; }

    // Mutators
    public void SetEntries(Vector<BaseEntry> _entries) { Entries = _entries; }
    public void SetDate(Calendar _date) { date = _date; }


    public void AddEntry(BaseEntry entry) {
        if(entry.GetDate() == date) {
            Entries.add(entry);
            if(entry.GetType() == EntryType.CHECKUP){
                HasCheckUp = true;
            } else if (entry.GetType() == EntryType.JOURNAL){
                HasJournal = true;
            }
        } else {
            System.out.println("Entry is from a different date, cannot add to current Log.");
        }
    }

    public void RemoveEntry(BaseEntry entry) {
        if(Entries.contains(entry)){
            Entries.remove(entry);
            // TODO: Throw an exception if removal is not possible
        } else {
            System.out.println( "Entry not found, nothing to remove.");
        }
    }

    public void DateOverride(BaseEntry entry) {     // Gives ability to override an entries date to match the current log
        entry.SetDate(date);
    }

    /*
    // FireStore Initialization
    public void SaveLog() {
        FirebaseFirestore log = FirebaseFirestore.getInstance();
        log.collection("Logs").add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        android.util.Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getID());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        android.util.Log.w(TAG, "Error Adding document", e);
                    }
                });
    }

     */




    //TODO: 1. Add a check to see if a log still has an entry type after one is removed
    //      2.
}
