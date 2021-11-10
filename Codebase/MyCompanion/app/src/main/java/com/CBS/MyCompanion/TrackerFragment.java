package com.CBS.MyCompanion;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.CBS.MyCompanion.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrackerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackerFragment newInstance(String param1, String param2) {
        TrackerFragment fragment = new TrackerFragment();
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
        View trackerView = inflater.inflate(R.layout.fragment_tracker, container, false);

        Calendar calendar = Calendar.getInstance();
        DisplayDateRange(calendar, trackerView, 0);

        ImageButton buttonPrevious = trackerView.findViewById(R.id.button_trackerPrev);
        ImageButton buttonNext = trackerView.findViewById(R.id.button_trackerNext);

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayDateRange(calendar, trackerView, -7);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayDateRange(calendar, trackerView, 7);
            }
        });

        PieChart emotionChart = trackerView.findViewById(R.id.pieChart_emotion);

        emotionChart.setDrawHoleEnabled(true);
        emotionChart.setUsePercentValues(true);
        emotionChart.setCenterText("Emotion Chart");
        emotionChart.getDescription().setEnabled(false);
        Legend emotionLegend = emotionChart.getLegend();
        emotionLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        emotionLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        emotionLegend.setWordWrapEnabled(true);

        ArrayList<PieEntry> emotions = new ArrayList<>();
        emotions.add(new PieEntry(1, "Stressed"));
        emotions.add(new PieEntry(1, "Sad"));
        emotions.add(new PieEntry(1, "Angry"));
        emotions.add(new PieEntry(1, "Anxious"));
        emotions.add(new PieEntry(1, "Happy"));
        emotions.add(new PieEntry(1, "Lonely"));
        emotions.add(new PieEntry(1, "Fearful"));
        emotions.add(new PieEntry(1, "Withdrawn"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(148, 62, 93));
        colors.add(Color.rgb(96, 60, 105));
        colors.add(Color.rgb(213, 91, 79));
        colors.add(Color.rgb(255, 187, 105));
        colors.add(Color.rgb(255, 234, 148));
        colors.add(Color.rgb(109, 191, 154));
        colors.add(Color.rgb(82, 173, 235));
        colors.add(Color.rgb(93, 109, 143));

        PieDataSet dataSet = new PieDataSet(emotions, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(emotionChart));
        data.setValueTextColor(Color.BLACK);
        emotionChart.setData(data);
        emotionChart.invalidate();


        return trackerView;
    }
    void DisplayDateRange(Calendar calendar, View view, int days)
    {
        while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            calendar.add(calendar.DATE, -1);
        }
        calendar.add(calendar.DATE, days);
        String startDate = DateFormat.getDateInstance().format(calendar.getTime());
        calendar.add(calendar.DATE, 6);
        String endDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = view.findViewById(R.id.text_trackerDate);
        textViewDate.setText(startDate + " - " + endDate);
    }
}