
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 10/6/2021
 */


package com.company;
import java.util.Date;
import java.util.Vector;

public class Log {

    // Properties

    private Date date;

    public Vector<BaseEntry> Entries;

/// METHODS ///

    // Constructor
    Log(){}

    // Accessors
    public Date GetDate() { return date; }

    // Mutators
    public void SetDate(Date _date) { date = _date; }


    public void AddEntry(BaseEntry entry) {
        entry.SetDate(date);
        Entries.add(entry);
    }

}
