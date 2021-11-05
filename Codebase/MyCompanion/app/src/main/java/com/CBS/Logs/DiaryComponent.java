/*
    Created by: Sam Whorton
    Date Created: 11/5/2021
    Last Modified: 11/5/2021
 */

package com.CBS.Logs;

public class DiaryComponent {

    // Properties
    private String question;
    private String response;

    // METHODS //

    // Copy Constructor
    DiaryComponent(DiaryComponent d){
        SetQuestion(d.GetQuestion());
        SetResponse(d.GetResponse());
    }

    // Accessors
    public String GetQuestion() { return question; }
    public String GetResponse() { return response; }

    // Mutators
    public void SetQuestion(String _question) { question = _question; }
    public void SetResponse(String _response) { response = _response; }
}
