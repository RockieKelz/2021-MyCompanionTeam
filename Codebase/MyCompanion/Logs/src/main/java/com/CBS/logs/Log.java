
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/2/2021
 */

package com.CBS.logs;
import java.util.Date;
import java.util.Vector;

public class Log {

    // Properties
    private Vector<BaseEntry> Entries;
    private Date date;
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
    public Date GetDate() { return date; }
    public boolean CheckUpStatus() { return HasCheckUp; }
    public boolean JournalStatus() { return HasJournal; }

    // Mutators
    public void SetEntries(Vector<BaseEntry> _entries) { Entries = _entries; }
    public void SetDate(Date _date) { date = _date; }


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






    //TODO: 1. Add a check to see if a log still has an entry type after one is removed
    //      2.
}
