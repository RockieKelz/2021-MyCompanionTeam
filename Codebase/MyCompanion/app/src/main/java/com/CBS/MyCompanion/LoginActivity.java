package com.CBS.MyCompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.CBS.MyCompanion.Data.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.activity_login_emailEditText);
        passwordEditText = findViewById(R.id.activity_login_passwordEditText);
        loginButton = findViewById(R.id.activity_login_loginButton);
        progressBar = findViewById(R.id.activity_login_progressBar);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
    {

        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    /*
                                    Toast.makeText(getApplicationContext(),

                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                     */

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);

                                    // Set the User Id to the current user
                                    UserAccount currentUser = UserAccount.getInstance();
                                    currentUser.SetId(mAuth.getUid());

                                    if (currentUser.GetFirstName() == null){
                                        Intent i
                                                = new Intent(LoginActivity.this,
                                                InputUserData.class);
                                        startActivity(i);
                                    } else {

                                        // if sign-in is successful
                                        // intent to home activity
                                        Intent i
                                                = new Intent(LoginActivity.this,
                                                MainActivity.class);
                                        startActivity(i);
                                    }
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
    }
}