package com.CBS.MyCompanion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.CBS.MyCompanion.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckUpFragment extends Fragment {

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

        TextView textQuestion = checkUpView.findViewById(R.id.text_question);
        Calendar calendar = Calendar.getInstance();
        int dayNum = calendar.get(Calendar.DAY_OF_MONTH);
        int monthNum = calendar.get(Calendar.MONTH);
        int yearNum = calendar.get(Calendar.YEAR);
        String dateIsToday = "How are you\nToday?";
        textQuestion.setText(dateIsToday);

        ImageButton dateChoice = checkUpView.findViewById(R.id.button_calendarPick_checkUp);

        dateChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (year == yearNum && month == yearNum && dayOfMonth == dayNum)
                        {
                            textQuestion.setText(dateIsToday);
                        }
                        else {
                            textQuestion.setText("How were you on\n" + intMonthToString(month) + " " + dayOfMonth + ", " + year);
                        }
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(checkUpView.getContext(), dateSetListener, yearNum, monthNum, dayNum);
                datePickerDialog.show();
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