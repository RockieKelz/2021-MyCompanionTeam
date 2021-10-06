
/*
    Created by: Sam Whorton
    Date Created: 10/6/2021
    Last Modified: 10/6/2021
 */

package com.company;
import java.util.Date;


enum LogType { JOURNAL, CHECKUP }

public class BaseLog {

    // Properties
    private Date date;
    private LogType type;


    /// METHODS ///

    // Accessors
    public Date GetDate() { return date; }
    public LogType GetType() { return type; }

    // Mutators
    public void SetDate(Date _date) { date = _date; }
    public void SetType(LogType _type) { type = _type; }
}
