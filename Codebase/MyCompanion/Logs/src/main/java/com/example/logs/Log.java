
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

    // Accessors
    public Vector<BaseEntry> GetEntries() { return Entries; }

    // Mutators
    public void SetEntries(BaseEntry _entry) { AddEntry(_entry); }      // Might need to be removed and then add a copy constructor


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
