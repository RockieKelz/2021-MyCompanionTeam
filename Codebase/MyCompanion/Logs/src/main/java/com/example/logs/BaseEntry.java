
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/2/2021
 */

package com.example.logs;
import java.util.Date;

enum EntryType { JOURNAL, CHECKUP }

public class BaseEntry {

    // Properties
    private Date date;
    private EntryType type;
    private JournalEntry journal;
    private CheckUpEntry checkUp;


    /// METHODS ///

    // Accessors
    public Date GetDate() { return date; }
    public EntryType GetType() { return type; }
    public JournalEntry GetJournal() { return journal; }
    public CheckUpEntry GetCheckUp() { return checkUp; }


    // Mutators
    public void SetDate(Date _date) { date = _date; }
    public void SetType(EntryType _type) { type = _type; }
    public void SetJournal(JournalEntry _journal) { journal = _journal; }
    public void SetCheckUp(CheckUpEntry _checkUp) { checkUp = _checkUp; }
}