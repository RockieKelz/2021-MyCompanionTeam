
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/5/2021
 */

package com.CBS.MyCompanion.Data.Logs;
import java.util.Calendar;
import java.util.Date;

enum EntryType { JOURNAL, CHECKUP }

abstract class BaseEntry {

    // Properties
    private Calendar date;
    private EntryType type;


    /// METHODS ///

    // Accessors
    public Calendar GetDate() { return date; }
    public EntryType GetType() { return type; }


    // Mutators
    public void SetDate(Calendar _date) { date = _date; }
    public void SetType(EntryType _type) { type = _type; }
}