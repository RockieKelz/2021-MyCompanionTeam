
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 10/6/2021
 */

package com.company;
import java.util.Date;


enum EntryType { JOURNAL, CHECKUP }

public class BaseEntry {

    // Properties
    private Date date;
    private EntryType type;


    /// METHODS ///

    // Accessors
    public Date GetDate() { return date; }
    public EntryType GetType() { return type; }

    // Mutators
    public void SetDate(Date _date) { date = _date; }
    public void SetType(EntryType _type) { type = _type; }
}