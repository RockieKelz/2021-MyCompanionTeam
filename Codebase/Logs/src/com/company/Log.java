
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 10/6/2021
 */


package com.company;
import java.util.Vector;

public class Log {

    // Properties

    private Vector<BaseEntry> Entries;

    /// METHODS ///

    // Constructor
    Log(){}

    // Accessors
    public Vector<BaseEntry> GetEntries() { return Entries; }

    // Mutators
    public void SetEntries(Vector<BaseEntry> _entries) { Entries = _entries; }

}
