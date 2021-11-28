package com.CBS.MyCompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.CBS.MyCompanion.Data.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Button editAccountButton, editPic;
    private TextView usersName, usersEmail, usersPassword;
    private CircleImageView profileImage;
    Uri imagePath;

    public AccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        usersName = accountView.findViewById(R.id.account_Name);
        usersPassword = accountView.findViewById(R.id.account_Password);
        usersEmail = accountView.findViewById(R.id.account_Email);
        profileImage = accountView.findViewById(R.id.account_profile_pic);

        editAccountButton = accountView.findViewById(R.id.edit_Account_Button);
        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickedEditAccount(v);
            }
        });

        Button logoutButton = accountView.findViewById(R.id.account_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        
        return accountView;
    }
    public void buttonClickedEditAccount(View view) {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.edit_account_popup, null);
        final EditText editUsername = alertLayout.findViewById(R.id.edit_username);
        editUsername.setText(usersName.getText());
        final EditText textEmail = alertLayout.findViewById(R.id.edit_email);
        textEmail.setText((CharSequence) usersEmail.getText());
        final EditText textPassword = alertLayout.findViewById(R.id.edit_password);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Edit Account");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameUpdate = editUsername.getText().toString();
                UserAccount userAccount = new UserAccount();
                userAccount.SetFirstName(nameUpdate);
                usersName.setText(user.getDisplayName());

                String emailUpdate = textEmail.getText().toString();
                user.updateEmail(emailUpdate);
                usersEmail.setText(user.getEmail());

                String passwordUpdate = textPassword.getText().toString();
                user.updatePassword(passwordUpdate);
                usersPassword.setText(passwordUpdate);

                databaseReference.child(user.getUid()).setValue(userAccount);
                editUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}