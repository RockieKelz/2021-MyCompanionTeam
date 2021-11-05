
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 11/5/2021
 */

package com.CBS.Logs;
import java.util.HashMap;
import java.util.Vector;

public class JournalEntry extends BaseEntry {

    // Properties
    // TODO: Add the appropriate properties to the Journal entries
    //- does the entry need to be saved as a single string or as multiple?

    private Vector<DiaryComponent> Components;
    private String title;

    /// METHODS ///

    // Constructor

    JournalEntry(){
        this.SetType(EntryType.JOURNAL);
    }

    // Accessors
    public Vector<DiaryComponent> GetComponents() {return Components;}
    public String GetTitle() { return title; }

    // Mutators
    public void SetComponents(Vector<DiaryComponent> _components) { Components = _components; }
    public void SetTitle(String _title) { title = _title; }



    public void AddComponent(DiaryComponent _component) { Components.add(_component); }
}
