
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/2/2021
 */

package com.CBS.logs;
import java.util.Date;

enum EntryType { JOURNAL, CHECKUP }
enum Emotions {SAD, HAPPY, ANXIOUS, CALM, ANGRY, EXCITED}

abstract class BaseEntry {

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