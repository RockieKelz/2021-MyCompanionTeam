
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/5/2021
 */

package com.CBS.MyCompanion.Data.Logs;

import java.util.Vector;

public class CheckUpEntry extends BaseEntry {

    // Properties
    // TODO: Add the appropriate properties to the CheckUp entries
    //- how should the check ups be packaged

    private Vector<Emotions> emotions;
    private int rating;                     // The days rating from 1-5, inclusive


    /// METHODS ///

    // Constructor

    public CheckUpEntry(){ this.SetType(EntryType.CHECKUP); }

    // Accessors
    public Vector<Emotions> GetEmotions() { return emotions; }
    public int GetRating() { return rating; }


    // Mutators
    public void SetEmotions(Vector<Emotions> _emotions) { emotions = _emotions; }
    public void SetRating(int _rating) { rating = _rating; }

}
