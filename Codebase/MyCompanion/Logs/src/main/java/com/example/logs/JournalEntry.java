
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/2/2021
 */

package com.example.logs;

public class JournalEntry extends BaseEntry {

    // Properties
    // TODO: Add the appropriate properties to the Journal entries
    //- does the entry need to be saved as a single string or as multiple?
    private String entry;

    /// METHODS ///

    // Constructor

    JournalEntry(){
        this.SetType(EntryType.JOURNAL);
    }

    // Accessors
    public String GetEntry() { return entry; }

    // Mutators
    public void SetEntry(String _entry) { entry = _entry; }

}
