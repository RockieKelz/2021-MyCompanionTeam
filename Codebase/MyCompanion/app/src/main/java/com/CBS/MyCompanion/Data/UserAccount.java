/*
    Created by: Sam Whorton
    Date Created: 11/12/2021
    Last Modified: 11/16/2021
 */

package com.CBS.MyCompanion.Data;

import com.CBS.MyCompanion.Data.Logs.Log;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Vector;

public class UserAccount {

    // Properties
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private Integer totalLoginCount;
    private Integer currentLoginStreak;
    private Integer longestStreak;
    private Vector<Log> logs;

    // Constructors
    public UserAccount(){}

    public UserAccount(String _firstName, String _lastName){
        SetFirstName(_firstName);
        SetLastName(_lastName);
        SetFullName(_firstName + " " + _lastName);
        SetTotalLoginCount(0);
        SetCurrentLoginStreak(0);
        SetLongestStreak(0);
        logs = new Vector<Log>();
    }

    // Accessors
    public String GetId() { return id; }
    public String GetFirstName() { return firstName; }
    public String GetLastName() { return lastName; }
    public String GetFullName() { return fullName; }
    public Integer GetTotalLoginCount() { return totalLoginCount; }
    public Integer GetCurrentLoginStreak() { return currentLoginStreak; }
    public Integer GetLongestStreak() { return longestStreak; }
    public Vector<Log> GetLogs() { return logs; }

    // Mutators
    public void SetId(String _id) { id = _id; }
    public void SetFirstName(String _firstName) { firstName = _firstName; }
    public void SetLastName(String _lastName) { lastName = _lastName; }
    public void SetFullName(String _fullName) { fullName = _fullName; }
    public void SetTotalLoginCount(Integer count) { totalLoginCount = count; }
    public void SetCurrentLoginStreak(Integer count) { currentLoginStreak = count; }
    public void SetLongestStreak(Integer count) { longestStreak = count; }

    // Methods



}
