package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.ActivityPackage.SettingActivity;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.ModelPackage.ProfileSection;
import com.quizest.quizestapp.ModelPackage.UserLogIn;
import com.quizest.quizestapp.NetworkPackage.ErrorHandler;
import com.quizest.quizestapp.NetworkPackage.RetrofitClient;
import com.quizest.quizestapp.NetworkPackage.RetrofitInterface;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.GlideApp;
import com.quizest.quizestapp.UtilPackge.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends Fragment {

    ImageButton btnEditProfile, button_setting_profile;
    LineChart graphQuizReport;
    ImageView img_profile;
    ArrayList<Entry> lineChatEntryData;
    TextView userName, userEmail, userPoints, userRanking;
    ArrayList<String> lebels;

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

        lebels = new ArrayList<>();
        lineChatEntryData = new ArrayList<>();

        getProfileData();


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null && isAdded())
                    ((MainActivity) getActivity()).fragmentTransition(new EditProfileFragment());
            }
        });

        button_setting_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                if(getActivity() != null)
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                getActivity().finish();
            }
        });


    }

    private void chartBuilder(final ArrayList<String> lebels, final ArrayList<Entry> lineChatEntryData) {

        /*
         */
        /*input data into entry *//*

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

*/


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
                    return "1";
                }

            }
        });

        graphQuizReport.setVisibleYRangeMaximum(200, YAxis.AxisDependency.LEFT);
        //gr//aphQuizReport.setVisibleYRange(0, 100, YAxis.AxisDependency.LEFT);
        graphQuizReport.getAxisRight().setEnabled(false);
        //graphQuizReport.getAxisLeft().setEnabled(false);
        graphQuizReport.getAxisLeft().setDrawGridLines(false);
        graphQuizReport.getXAxis().setDrawGridLines(false);


    }


    private void initViews() {
        View view = getView();
        if (view != null) {
            button_setting_profile = view.findViewById(R.id.button_setting_profile);
            userEmail = view.findViewById(R.id.tv_user_email);
            userName = view.findViewById(R.id.tv_profile_username);
            userPoints = view.findViewById(R.id.tv_user_points);
            userRanking = view.findViewById(R.id.tv_user_ranking);
            img_profile = view.findViewById(R.id.img_profile);
            btnEditProfile = view.findViewById(R.id.btn_edit_profile);
            graphQuizReport = view.findViewById(R.id.graph_quiz_report);
        }
    }

    private void getProfileData() {
        final ProgressDialog dialog = Util.showDialog(getActivity());
        final Storage storage = new Storage(getActivity());
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        final Call<String> profileCall = retrofitInterface.getProfileData(storage.getAccessToken());
        profileCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                /*handle error globally */
                ErrorHandler.getInstance().handleError(response.code(), getActivity(), dialog);
                if (response.isSuccessful()) {
                    /*success true*/
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean isSuccess = jsonObject.getBoolean("success");
                        if (isSuccess) {
                            /*serialize the String response  */
                            Gson gson = new Gson();
                            ProfileSection profileSection = gson.fromJson(response.body(), ProfileSection.class);
                            storage.saveUserName(profileSection.getData().getUser().getName());
                            /*simple data binding for profile section*/
                            userEmail.setText(profileSection.getData().getUser().getEmail());
                            userName.setText(profileSection.getData().getUser().getName());
                            userPoints.setText(profileSection.getData().getUser().getPoints());
                            userRanking.setText(String.valueOf(profileSection.getData().getUser().getRanking()));
                            if (getActivity() != null)
                                GlideApp.with(getActivity()).load(profileSection.getData().getUser().getPhoto()).into(img_profile);

                            /*make the chart*/
                            for (int i = 0; i < profileSection.getDailyScore().size(); i++) {
                                lineChatEntryData.add(new Entry(i, Math.round((float) profileSection.getDailyScore().get(i).getScorePercentage())));
                                lebels.add(Util.getFormattedDate(profileSection.getDailyScore().get(i).getDate()));

                            }

                            lineChatEntryData.add(new Entry(2, 35));
                            lineChatEntryData.add(new Entry(3, 55));
                            lineChatEntryData.add(new Entry(4, 35));
                            lineChatEntryData.add(new Entry(5, 60));
                            lineChatEntryData.add(new Entry(6, 45));
                            lineChatEntryData.add(new Entry(7, 70));



                            lebels.add("30");
                            lebels.add("28");
                            lebels.add("27");
                            lebels.add("26");
                            lebels.add("25");
                            lebels.add("24");

                            chartBuilder(lebels, lineChatEntryData);

                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);

                        } else {
                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);
                            /*get all the error messages and show to the user*/
                            String message = jsonObject.getString("message");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(getActivity()!=null)
                    /*dismiss the dialog*/
                    Util.dissmisDialog(dialog);
                    Toast.makeText(getActivity(), R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*dismiss the dialog*/
                Util.dissmisDialog(dialog);
                /*handle network error and notify the user*/
                if (t instanceof SocketTimeoutException || t instanceof IOException) {
                    if (getActivity() != null && isAdded())
                        Toast.makeText(getActivity(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
