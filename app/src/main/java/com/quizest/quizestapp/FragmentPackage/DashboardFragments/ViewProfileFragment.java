package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.quizest.quizestapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ViewProfileFragment extends Fragment {

    LineChart graphQuizReport;
    List<Entry> linceChatEntryData;

    public ViewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        chartBuilder();

    }

    private void chartBuilder() {

        linceChatEntryData = new ArrayList<>();

        /*input data into entry */
        for (int i = 0; i < 12; i++) {
            linceChatEntryData.add(new Entry((10-1), i));
        }


        LineDataSet dataSet = new LineDataSet(linceChatEntryData, ""); // add entries to dataset
        dataSet.setColor(getResources().getColor(R.color.color_holo_blue));
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setDrawCircleHole(false);

        LineData lineData = new LineData(dataSet);

        graphQuizReport.setData(lineData);

        graphQuizReport.invalidate();


    }

    private void chartStypeCustomization() {


    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            graphQuizReport = view.findViewById(R.id.graph_quiz_report);
        }
    }

}
