
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/5/2021
 */

package com.CBS.MyCompanion.Data.Logs;


import java.time.LocalDate;
import java.util.Date;
import java.util.Calendar;
import java.util.Vector;

public class Log {

    // Properties
    private CheckUpEntry checkUpEntry;
    private JournalEntry journalEntry;
    private Calendar date;
    private boolean HasCheckUp;
    private boolean HasJournal;

    // METHODS //

    // Constructors
    public Log(){}

    // Copy Constructor
    Log(Log log){
        SetCheckUp(log.GetCheckUp());
        SetJournal(log.GetJournal());
        date = log.GetDate();
    }


    // Accessors
    public CheckUpEntry GetCheckUp() { return checkUpEntry; }
    public JournalEntry GetJournal() { return journalEntry; }
    public Calendar GetDate() { return date; }
    public boolean CheckUpStatus() { return HasCheckUp; }
    public boolean JournalStatus() { return HasJournal; }

    // Mutators
    public void SetCheckUp(CheckUpEntry _checkUp) { checkUpEntry = _checkUp; }
    public void SetJournal(JournalEntry _journalEntry) { journalEntry = _journalEntry; }
    public void SetDate(Calendar _date) { date = _date; }
    public void SetEmotions(Vector<Emotions> emotions) { checkUpEntry.SetEmotions(emotions); }
    public void SetRating(Integer rating) { checkUpEntry.SetRating(rating); }

}
