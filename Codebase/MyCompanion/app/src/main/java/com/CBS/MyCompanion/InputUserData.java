package com.CBS.MyCompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.CBS.MyCompanion.Data.UserAccount;


public class InputUserData extends AppCompatActivity {

    private EditText firstnameEditText, lastNameEditText;
    private Button ctnButton;
    public String firstName, lastName;
    public FirebaseAuth mAuth;
    public FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_user_data);

        firstnameEditText = findViewById(R.id.activity_inputUserData_firstName);
        lastNameEditText = findViewById(R.id.activity_inputUserData_lastName);
        ctnButton = findViewById(R.id.activity_inputUserData_ctnButton);

        firstName = firstnameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        ctnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void addDataToFirestore(String _firstName, String _lastName, String _id) {


        UserAccount currentUser = new UserAccount(_firstName, _lastName, _id);

        CollectionReference dbUsers = database.collection("User_Data");
        dbUsers.document(mAuth.getUid()).set(currentUser);

        dbUsers.document(mAuth.getUid()).update("firstName", firstName);

        /*
        dbUsers.add(currentUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Success", "User Data added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Failure", "Error adding data", e);
            }
        });
         */
    }

    public void Login() {
        if (!TextUtils.isEmpty(firstName) || !TextUtils.isEmpty(lastName)){
            Toast.makeText(getApplicationContext(),
                    "Please fill out required fields",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        addDataToFirestore(firstName, lastName, mAuth.getUid());

        Intent i
                = new Intent(InputUserData.this,
                MainActivity.class);
        startActivity(i);

    }
}