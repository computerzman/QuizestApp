package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ViewProfileFragment extends Fragment {

    ImageButton btnEditProfile;
    LineChart graphQuizReport;
    List<Entry> lineChatEntryData;

    public ViewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_profile, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        chartBuilder();


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() != null && isAdded())
                ((MainActivity)getActivity()).fragmentTransition(new EditProfileFragment());
            }
        });

    }

    private void chartBuilder() {

        lineChatEntryData = new ArrayList<>();

        /*input data into entry */
        lineChatEntryData.add(new Entry(1, 26f));
        lineChatEntryData.add(new Entry(2, 14f, getResources().getDrawable(R.drawable.circle_bg_on_graph)));
        lineChatEntryData.add(new Entry(3, 12f));
        lineChatEntryData.add(new Entry(4, 19f));
        lineChatEntryData.add(new Entry(5, 12f));
        lineChatEntryData.add(new Entry(6, 21f));
        lineChatEntryData.add(new Entry(7, 8f));
        lineChatEntryData.add(new Entry(8, 22f));
        lineChatEntryData.add(new Entry(9, 10f));
        lineChatEntryData.add(new Entry(10, 7f));
        lineChatEntryData.add(new Entry(11, 15f));
        lineChatEntryData.add(new Entry(12, 18f));

        final ArrayList<String> lebels = new ArrayList<>();

        lebels.add("Jan");
        lebels.add("Fav");
        lebels.add("Mar");
        lebels.add("Apr");
        lebels.add("May");
        lebels.add("Jun");
        lebels.add("Jul");
        lebels.add("Aug");
        lebels.add("Sep");
        lebels.add("Oct");
        lebels.add("Nov");
        lebels.add("Dec");


        LineDataSet dataSet = new LineDataSet(lineChatEntryData, ""); // add entries to dataset
        dataSet.setFillDrawable(getResources().getDrawable(R.drawable.gradient_graph));
        dataSet.setDrawCircleHole(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawCircles(false);
        dataSet.setDrawHighlightIndicators(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(true);


        LineData lineData = new LineData(dataSet);
        graphQuizReport.setData(lineData);
        graphQuizReport.getDescription().setEnabled(false);
        graphQuizReport.invalidate();


        XAxis xAxis = graphQuizReport.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value <= lineChatEntryData.size() - 1) {
                    return lebels.get((int) value);
                } else {
                    return "Jan";
                }

            }
        });

        graphQuizReport.setVisibleYRange(0, 30, YAxis.AxisDependency.LEFT);
        graphQuizReport.getAxisRight().setEnabled(false);
        graphQuizReport.getAxisLeft().setEnabled(false);
        graphQuizReport.getAxisLeft().setDrawGridLines(false);
        graphQuizReport.getXAxis().setDrawGridLines(false);


    }


    private void initViews() {
        View view = getView();
        if (view != null) {
            btnEditProfile = view.findViewById(R.id.btn_edit_profile);
            graphQuizReport = view.findViewById(R.id.graph_quiz_report);
        }
    }

}
