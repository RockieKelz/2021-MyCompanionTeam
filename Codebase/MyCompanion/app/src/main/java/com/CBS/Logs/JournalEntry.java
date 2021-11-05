
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/5/2021
 */

package com.CBS.Logs;
import java.util.HashMap;

public class JournalEntry extends BaseEntry {

    // Properties
    // TODO: Add the appropriate properties to the Journal entries
    //- does the entry need to be saved as a single string or as multiple?

    private HashMap<String, String> Entry;
    private String title;

    /// METHODS ///

    // Constructor

    JournalEntry(){
        this.SetType(EntryType.JOURNAL);
    }

    // Accessors
    public HashMap<String, String> GetEntry() { return Entry; }
    public String GetTitle() { return title; }

    // Mutators
    public void SetEntry(HashMap<String, String> _entry) { Entry = _entry; }
    public void SetTitle(String _title) { title = _title; }



    public void AddQuestion(String question, String response){
        Entry.put(question, response);
    }
}
