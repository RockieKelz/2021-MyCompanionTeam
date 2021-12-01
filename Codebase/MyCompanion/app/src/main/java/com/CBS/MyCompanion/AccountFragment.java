package com.CBS.MyCompanion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference, profileRef;
    private DatabaseReference databaseReference;
    Button editAccountBtn, selectImageBtn, uploadImageBtn, cancelImageBtn;
    ImageButton editPicBtn;
    private TextView usersName, usersEmail, usersPassword;
    private CircleImageView acctProfileImage;
    Uri imagePath;
    String passwordUpdate;

    public AccountFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accountView = inflater.inflate(R.layout.fragment_account, container, false);

        //get the Firebase user information
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        if (currentUser != null) {
            //initialise views and set text to current user
            usersName = accountView.findViewById(R.id.account_Name);
            usersName.setText(currentUser.getDisplayName());

            usersPassword = accountView.findViewById(R.id.account_Password);
            if (passwordUpdate != null)
            {
                usersPassword.setText(passwordUpdate);
            }

            usersEmail = accountView.findViewById(R.id.account_Email);
            usersEmail.setText(currentUser.getEmail());
            acctProfileImage = accountView.findViewById(R.id.account_profile_pic);

            //load profile image from cloud data
            profileRef = storageReference.child("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(acctProfileImage));

            //add functionality to buttons to edit account
            editAccountBtn = accountView.findViewById(R.id.edit_Account_Button);
            editAccountBtn.setOnClickListener(this::buttonClickedEditAccount);

            editPicBtn = accountView.findViewById(R.id.account_EditPic);
            editPicBtn.setOnClickListener(this::buttonClickedEditImage);

            //logout of account
            Button logoutButton = accountView.findViewById(R.id.account_logout);
            logoutButton.setOnClickListener(v -> {
                if (mAuth.getCurrentUser() != null)
                    mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            });
        }
        else //TODO Delete Else Condition When Bypass Mode Is Remove
            {
                //initialise views and set text to current user
                usersName = accountView.findViewById(R.id.account_Name);
                usersName.setText("Please Login to Edit Account");

                usersEmail = accountView.findViewById(R.id.account_Email);

                acctProfileImage = accountView.findViewById(R.id.account_profile_pic);
                acctProfileImage.setImageResource(R.drawable.default_profile_pic);

                editAccountBtn = accountView.findViewById(R.id.edit_Account_Button);
                editAccountBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                editPicBtn = accountView.findViewById(R.id.account_EditPic);
                editPicBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                //logout of account
                Button logoutButton = accountView.findViewById(R.id.account_logout);
                logoutButton.setOnClickListener(v -> {
                    if (mAuth.getCurrentUser() != null)
                        mAuth.signOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                });
            }
        return accountView;
    }

    public void buttonClickedEditAccount(View view) {
        //set view to dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.edit_account_popup, null);
        //initialise editing views to current user
        final EditText editName = alertLayout.findViewById(R.id.edit_name);
        editName.setText(usersName.getText());
        final EditText editEmail = alertLayout.findViewById(R.id.edit_email);
        editEmail.setText((CharSequence) usersEmail.getText());
        final EditText editPassword = alertLayout.findViewById(R.id.edit_password);
        editPassword.setText((CharSequence) usersPassword.getText());
        //create dialog pop up
        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
        alert.setTitle("Edit Account");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        //allow user to cancel
        alert.setNegativeButton("Cancel", (dialog, which) -> {
        });
        //allow user to save changes and store data to cloud
        alert.setPositiveButton("OK", (dialog, which) -> {
            currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String nameUpdate = editName.getText().toString();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nameUpdate).build();
                currentUser.updateProfile(profileUpdates);
                usersName.setText(nameUpdate);

                String emailUpdate = editEmail.getText().toString();
                passwordUpdate = editPassword.getText().toString();
                if (TextUtils.isEmpty(passwordUpdate))
                {
                    Toast.makeText(requireActivity().getApplicationContext(),
                            "Account Not Updated, \nPlease enter password!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                else if (passwordUpdate.length() < 6)
                {
                    Toast.makeText(requireActivity().getApplicationContext(),
                            "Account Not Updated, \nPassword must be at least 6 characters",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                AuthCredential credential = EmailAuthProvider.getCredential(emailUpdate, passwordUpdate);
                currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        currentUser.updateEmail(emailUpdate);
                        currentUser.updatePassword(passwordUpdate);
                        usersEmail.setText(emailUpdate);
                        usersPassword.setText(passwordUpdate);
                        mAuth.signInWithEmailAndPassword(emailUpdate, passwordUpdate);
                        Toast.makeText(getActivity(), "Account Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        alert.create().show();
    }

    public void buttonClickedEditImage(View view) {
        //set view to dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View editLayout = inflater.inflate(R.layout.image_select_popup, null);
        //temporary comment for custom buttons
        //uploadImageBtn= view.findViewById(R.id.btnUploadImage);
        //selectImageBtn = view.findViewById(R.id.btnSelectImage);
        //create dialog pop up

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());
        alertDialog.setView(editLayout);
        alertDialog.setCancelable(false);
        //allows user to select image
        alertDialog.setPositiveButton("Select Image", (dialog, which) ->
        {
            //open image gallery
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1000);
        });
        //allow user to cancel
        alertDialog.setNeutralButton("Cancel", (dialog, which) -> {

        });
        alertDialog.create();
        alertDialog.show();
    }
    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && data != null) {
            //get the images file path
            imagePath = data.getData();
            try
            {   //set the image path to user profile image
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imagePath);
                acctProfileImage.setImageBitmap(bitmap);
                uploadImageAndSaveUri(imagePath);
            }
            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImageAndSaveUri(Uri uri) {
        //upload image to firebase storage
        UploadTask uploadTask = storageReference.child(("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()+"/profile.jpg")).putFile(uri);
        uploadTask.addOnFailureListener(e -> Toast.makeText(getActivity(), "Error: Uploading profile picture", Toast.LENGTH_SHORT).show()).addOnSuccessListener(taskSnapshot -> {
            //uploads pic stored under users id
            storageReference.child("users/" + mAuth.getCurrentUser().getUid()+"/profile.jpg").getDownloadUrl().addOnSuccessListener(uri1 -> {
                Picasso.get().load(uri1).into(acctProfileImage);
                Toast.makeText(getActivity(), "Profile picture uploaded", Toast.LENGTH_SHORT).show();
            });
        });
    }

}