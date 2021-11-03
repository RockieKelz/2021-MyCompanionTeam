
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/2/2021
 */

package com.example.logs;
import java.util.Date;
import java.util.Vector;

public class Log {

    // Properties
    private Vector<BaseEntry> Entries;
    private Date date;

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

    // Mutators
    public void SetEntries(Vector<BaseEntry> _entries) { Entries = _entries; }
    public void SetDate(Date _date) { date = _date; }


    public void AddEntry(BaseEntry entry) {
        if(entry.GetDate() == date) {
            Entries.add(entry);
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


}
