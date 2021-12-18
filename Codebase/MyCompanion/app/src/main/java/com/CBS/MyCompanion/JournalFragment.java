package com.CBS.MyCompanion;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.CBS.MyCompanion.Data.Logs.DiaryComponent;
import com.CBS.MyCompanion.Data.Logs.JournalEntry;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class JournalFragment extends Fragment {

    private Button presetSelectionButton, closePresetButton, saveJournalButton;
    private ArrayList<String> presetQuestionsList;
    private ListView presetListView;
    protected ArrayAdapter<String> adapter;
    public TextView journalDate;
    public EditText presetInput, journalInput;
    static boolean isJournalCompleted = false;
    int daySelected, monthSelected, yearSelected;   //months are 0 - 11

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View journalView = inflater.inflate(R.layout.fragment_journal, container, false);
        journalInput = journalView.findViewById(R.id.journal_Entry);

        //display date
        journalDate = journalView.findViewById(R.id.currentDate);
        Calendar calendar = Calendar.getInstance();
        String today = DateFormat.getDateInstance().format(calendar.getTime());
        journalDate.setText(today);

        //set selected date and default text view
        daySelected = calendar.get(Calendar.DAY_OF_MONTH);
        monthSelected = calendar.get(Calendar.MONTH);
        yearSelected = calendar.get(Calendar.YEAR);

        //initialize buttons
        saveJournalButton = journalView.findViewById(R.id.submitJournal);
        saveJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(yearSelected, monthSelected, daySelected);

                JournalEntry journalEntry = new JournalEntry();
                DiaryComponent diary = new DiaryComponent("FreeWrite", journalInput.getText().toString());
                journalEntry.AddComponent(diary);
                journalEntry.SetDate(selectedDate);

                Database.AddJournal(journalEntry);

                //trigger home journal checkbox
                isJournalCompleted = true;

                //clear's the entry from the screen
                Toast.makeText(requireActivity(), "Journal Entry Saved", Toast.LENGTH_LONG).show();
                journalInput.setText("");
            }
        });
        presetSelectionButton = journalView.findViewById(R.id.preset_questions_button);
        presetSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the pop up
                View presetLayout = getLayoutInflater().inflate(R.layout.activity_preset_popup, null);
                PopupWindow presetDialog = new PopupWindow(presetLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                presetDialog.setFocusable(true);
                presetDialog.setAnimationStyle(R.style.Animation_PopUp);

                //create the preset questions section
                presetListView = presetLayout.findViewById(R.id.preset_list_view);
                presetQuestionsList = new ArrayList<>();
                createPresetList();
                setAdapter();

                //allow for any text written in freewrite to be displayed in the pop up's input area
                presetInput = presetLayout.findViewById(R.id.presetInputBox);
                presetInput.setText(journalInput.getText());
                presetInput.requestFocusFromTouch();

                KeyboardVisibilityEvent.setEventListener(
                    requireActivity(),
                    new KeyboardVisibilityEventListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onVisibilityChanged(boolean isOpen) {
                            if(isOpen){
                                ((JournalActivity) requireActivity()).getNav().setVisibility(View.GONE);
                                requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            }
                        }
                    });

                presetDialog.update(0,0, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                presetDialog.showAtLocation(journalView, Gravity.TOP, 0, 0);
                //close the pop up window
                closePresetButton = presetLayout.findViewById(R.id.close_presetButton);
                closePresetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        journalInput.setText(presetInput.getText());
                        presetDialog.dismiss();
                    }
                });
            }
        });
        // TODO: Add functionality to submit button
        return journalView;
    }
    private void createPresetList()
    {
        //add the questions to be displayed in the list
        presetQuestionsList.add("How many hours did I sleep? \nWas the sleep restful or restless?");
        presetQuestionsList.add("What mood did I wake up in? Why do I believe I woke up in that mood?");
        presetQuestionsList.add("Did any unexpected events occur today? If so, how did the make you feel?");
        presetQuestionsList.add("What did I dream about last?");
        presetQuestionsList.add("When did I feel most like myself today? When did I feel least like myself?");
        presetQuestionsList.add("What's 3 things you'd like other's to know about yourself?");
        presetQuestionsList.add("What would you like to accomplish this week? How would you like to accomplish it?");
        presetQuestionsList.add("Finish this sentence, \"My life would be incomplete without...\"");
        presetQuestionsList.add("What did I eat today? Do I regret what I ate/didn't eat?");
        presetQuestionsList.add("What's something that makes me smile? Why does it bring a smile to my face?");
        presetQuestionsList.add("What type of work/life balance do I have? How can I improve it?");
        presetQuestionsList.add("Have I/will I spend any quality time with someone this week? (Express what is planned and how I feel about it)");
        presetQuestionsList.add("Have I had any feelings of self-harm?");

    }
    private void setAdapter()
    {
        //transfer the questions list into the layout's view
        adapter = new ArrayAdapter<String>(requireActivity().getApplicationContext(), android.R.layout.simple_selectable_list_item, presetQuestionsList);
        presetListView.setAdapter(adapter);

    }
    public static boolean getJournalState()
    {
        return isJournalCompleted;
    }
    public static void setJournalState(boolean _isJournalCompleted)
    {
        isJournalCompleted = _isJournalCompleted;
    }
}