
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/2/2021
 */

package com.example.logs;
import java.util.Vector;

public class Log {

    // Properties
    private Vector<BaseEntry> Entries;

    // METHODS //

    // Constructors

    // Copy Constructor
    Log(Log log){
        SetEntries(log.GetEntries());
    }


    // Accessors
    public Vector<BaseEntry> GetEntries() { return Entries; }

    // Mutators
    public void SetEntries(Vector<BaseEntry> _entries) { Entries = _entries; }


    public void AddEntry(BaseEntry entry) { Entries.add(entry); }

    public void RemoveEntry(BaseEntry entry) {
        if(Entries.contains(entry)){
            Entries.remove(entry);
            // TODO: Throw an exception if removal is not possible
        } else {
            System.out.println( "Entry not found, nothing to remove.");
        }
    }


}
