/*
    Created by: Sam Whorton
    Date Created: 11/12/2021
    Last Modified: 11/12/2021
 */

package com.CBS.MyCompanion.Data;

import com.CBS.MyCompanion.Data.Logs.Log;

import java.util.Date;
import java.util.Vector;

public class UserAccount {

    // Properties

    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private Date birthday;
    private Integer totalLoginCount;
    private Integer currentLoginStreak;
    private Integer longestStreak;
    private Vector<Log> logs;

    // Constructors
    UserAccount(String _firstName, String _lastName, Date _birthday, String _id){
        setFirstName(_firstName);
        setLastName(_lastName);
        setFullName(_firstName + " " + _lastName);
        setBirthday(_birthday);
        setId(_id);
        setTotalLoginCount(0);
        setCurrentLoginStreak(0);
        setLongestStreak(0);
        logs = new Vector<Log>();
    }

    // Accessors
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return fullName; }
    public Date getBirthday() { return birthday; }
    public Integer getTotalLoginCount() { return totalLoginCount; }
    public Integer getCurrentLoginStreak() { return currentLoginStreak; }
    public Integer getLongestStreak() { return longestStreak; }
    public Vector<Log> getLogs() { return logs; }

    // Mutators
    public void setId(String _id) { id = _id; }
    public void setFirstName(String _firstName) { firstName = _firstName; }
    public void setLastName(String _lastName) { lastName = _lastName; }
    public void setFullName(String _fullName) { fullName = _fullName; }
    public void setBirthday(Date _birthday) { birthday = _birthday; }
    public void setTotalLoginCount(Integer count) { totalLoginCount = count; }
    public void setCurrentLoginStreak(Integer count) { currentLoginStreak = count; }
    public void setLongestStreak(Integer count) { longestStreak = count; }


    // Methods


}
