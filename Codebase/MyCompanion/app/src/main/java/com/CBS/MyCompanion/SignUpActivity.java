package com.CBS.MyCompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button bypass;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup);

        emailEditText = findViewById(R.id.activity_signup_emailEditText);
        passwordEditText = findViewById(R.id.activity_signup_passwordEditText);
        loginButton = findViewById(R.id.activity_signup_loginButton);
        registerButton = findViewById(R.id.activity_signup_registerButton);
        progressBar = findViewById(R.id.activity_signup_progressBar);
        bypass = findViewById(R.id.activity_signup_Bypass);
        mAuth = FirebaseAuth.getInstance();

        // TODO: Delete before publishing
        mAuth.signOut();


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(s);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { registerNewUser(); }
        });

        bypass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(s);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            currentUser.reload();
            Intent s = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(s);
        }
    }

    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password;
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(),
                    "Password must be at least 6 characters",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            Log.d("Success", "createUserWithEmail:success");


                            Intent intent
                                    = new Intent(SignUpActivity.this,
                                    InputUserData.class);
                            startActivity(intent);
                        }
                        else {
                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(
                                            new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(
                                                        @NonNull Task<AuthResult> task)
                                                {
                                                    if (task.isSuccessful()) {

                                                        // hide the progress bar
                                                        progressBar.setVisibility(View.GONE);

                                                        Intent i = new Intent(SignUpActivity.this,
                                                                MainActivity.class);
                                                        startActivity(i);

                                                    } else {

                                                        // sign-in failed
                                                        Toast.makeText(getApplicationContext(),
                                                                "Registration failed!!",
                                                                Toast.LENGTH_LONG)
                                                                .show();
                                                        Log.w("Failure",
                                                                "createUserWithEmail:failure");
                                                    }
                                                }
                                            });

                        }
                    }
                });
    }

}