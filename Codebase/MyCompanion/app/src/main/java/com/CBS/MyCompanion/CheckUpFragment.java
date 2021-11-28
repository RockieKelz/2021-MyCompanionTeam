package com.CBS.MyCompanion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.CBS.MyCompanion.Data.Logs.CheckUpEntry;
import com.CBS.MyCompanion.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckUpFragment extends Fragment {

    int daySelected, monthSelected, yearSelected;   //months are 0 - 11
    int mood;                                       // mood rating from 1(sad) to 5(happy)
    enum Emotions {SAD, HAPPY, ANXIOUS, STRESSED, ANGRY, LONELY, WITHDRAWN, FEARFUL}
    Vector<Emotions> selectedEmotions;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckUpFragment newInstance(String param1, String param2) {
        CheckUpFragment fragment = new CheckUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View checkUpView = inflater.inflate(R.layout.fragment_checkup, container, false);

        //Change text view based on date picker dialog
        TextView textQuestion = checkUpView.findViewById(R.id.text_question);
        Calendar calendar = Calendar.getInstance();

        //set current date
        int dayCurrent = calendar.get(Calendar.DAY_OF_MONTH);
        int monthCurrent = calendar.get(Calendar.MONTH);
        int yearCurrent = calendar.get(Calendar.YEAR);

        //set selected date and default text view
        daySelected = calendar.get(Calendar.DAY_OF_MONTH);
        monthSelected = calendar.get(Calendar.MONTH);
        yearSelected = calendar.get(Calendar.YEAR);
        String dateIsToday = "How are you\nToday?";
        textQuestion.setText(dateIsToday);

        ImageButton dateChoice = checkUpView.findViewById(R.id.button_calendarPick_checkUp);

        //Open date picker dialogue with image button click
        dateChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (year == yearCurrent && month == monthCurrent && dayOfMonth == dayCurrent)
                        {
                            textQuestion.setText(dateIsToday);
                        }
                        else {
                            String selectedDate = "How were you on\n" + intMonthToString(month) + " " + dayOfMonth + ", " + year;
                            textQuestion.setText(selectedDate);
                        }
                        daySelected = dayOfMonth;
                        monthSelected = month;
                        yearSelected = year;
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(checkUpView.getContext(), dateSetListener, yearSelected, monthSelected, daySelected);
                datePickerDialog.show();
            }
        });

        Slider sliderMood = checkUpView.findViewById(R.id.slider_checkUp);
        mood = (int)sliderMood.getValue();
        //Change the mood
        sliderMood.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                mood = (int)sliderMood.getValue();
            }
        });

        //TODO: Add java code for emotion toggle groups
        selectedEmotions = new Vector<>();

        MaterialButton stressedBtn = checkUpView.findViewById(R.id.button_stressed);
        MaterialButton sadBtn = checkUpView.findViewById(R.id.button_sad);
        MaterialButton anxiousBtn = checkUpView.findViewById(R.id.button_anxious);
        MaterialButton angryBtn = checkUpView.findViewById(R.id.button_angry);
        MaterialButton withdrawnBtn = checkUpView.findViewById(R.id.button_withdrawn);
        MaterialButton lonelyBtn = checkUpView.findViewById(R.id.button_lonely);
        MaterialButton fearfulBtn = checkUpView.findViewById(R.id.button_fearful);
        MaterialButton happyBtn = checkUpView.findViewById(R.id.button_happy);

        stressedBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.STRESSED);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.STRESSED);
                }
            }
        });
        sadBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.SAD);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.SAD);
                }
            }
        });
        anxiousBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.ANXIOUS);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.ANXIOUS);
                }
            }
        });
        angryBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.ANGRY);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.ANGRY);
                }
            }
        });
        withdrawnBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.WITHDRAWN);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.WITHDRAWN);
                }
            }
        });
        lonelyBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.LONELY);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.LONELY);
                }
            }
        });
        fearfulBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.FEARFUL);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.FEARFUL);
                }
            }
        });
        happyBtn.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                if (isChecked)
                {
                    selectedEmotions.add(Emotions.HAPPY);
                }
                else
                {
                    //remove from vector
                    selectedEmotions.remove(Emotions.HAPPY);
                }
            }
        });

        //Save button actions
        CheckUpEntry newEntry = new CheckUpEntry();
        Button saveCheckup = checkUpView.findViewById(R.id.button_save_checkUp);
        saveCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Remove test string
                String test = "mood:" + mood + " Date:" + daySelected +" "+ monthSelected +" "+ yearSelected + " " + selectedEmotions.size();
                Toast.makeText(checkUpView.getContext(), test, Toast.LENGTH_LONG).show();
            }
        });


        return checkUpView;
    }
    String intMonthToString (int monthNum)
    {
        String month = "";
        switch (monthNum + 1)
        {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
        }
        return month;
    }
}