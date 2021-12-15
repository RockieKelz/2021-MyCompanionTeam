package com.CBS.MyCompanion;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FeedbackFragment extends Fragment {
    private Button feedbackSubmitButton;
    private EditText feedbackInput, feedbackEmail;
    private TextView feedbackText;
    StorageReference storageReference;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View feedbackLayout = inflater.inflate(R.layout.fragment_feedback, container, false);

        feedbackEmail = feedbackLayout.findViewById(R.id.feedback_email);
        feedbackInput = feedbackLayout.findViewById(R.id.feedback_input);
        feedbackSubmitButton = feedbackLayout.findViewById(R.id.feedback_submit);
        feedbackText = feedbackLayout.findViewById(R.id.feedback_text);
        storageReference = FirebaseStorage.getInstance().getReference();

        feedbackText.setText("We're always looking for ways to improve your in-app experience. " +
                "If you have any opinions, feedback, or requests you'd like to share, we'd love to hear it. " +
                "We can't guarantee that every request will be granted, but we will take everything you send us into consideration.");

        KeyboardVisibilityEvent.setEventListener(
                requireActivity(),
                new KeyboardVisibilityEventListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                        }
                    }
                });

        feedbackSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtain inputted information
                String email = feedbackEmail.getText().toString();
                String feedback = feedbackInput.getText().toString();
                //generate date for file saving
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMM");
                String date = simpleDate.format(calendar.getTime());

                //Create a file to save the feedback to
                File feedbackFile = new File(requireActivity().getFilesDir(), "feedback");
                if (!feedbackFile.exists()) {
                    feedbackFile.mkdir();
                }
                try {
                    File file = new File(feedbackFile, "feedback.txt");
                    FileWriter writer = new FileWriter(file);
                    writer.append(feedback);
                    writer.flush();
                    writer.close();
                //upload the feedback to fire storage
                UploadTask uploadFeedback = storageReference.child("feedback/" + date + email + ".txt").putFile(Uri.fromFile(file));
                uploadFeedback.addOnFailureListener(e -> Toast.makeText(getActivity(), "Error: Please try again", Toast.LENGTH_SHORT).show())
                        .addOnSuccessListener(taskSnapshot -> Toast.makeText(getActivity(), "Your feedback is appreciated!", Toast.LENGTH_LONG).show());
                    file.delete();
                } catch (Exception e) { }

                feedbackFile.delete();
            }
        });
        return feedbackLayout;
    }
}