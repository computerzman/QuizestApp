package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.annotation.SuppressLint;
import android.icu.util.UniversalTimeScale;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Helper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizest.quizestapp.ActivityPackage.QuizActivity;
import com.quizest.quizestapp.AdapterPackage.QuizOptionsRecyclerRow;
import com.quizest.quizestapp.DialogPackage.Congratsdialog;
import com.quizest.quizestapp.ModelPackage.QuestionList;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    Button btnSkip;
    ImageView ivAnswerA;
    int x;
     static CountDownTimer countDownTimer;
    TextView tv_question_name;
    RecyclerView optionRecyclerView;
    QuizOptionsRecyclerRow quizOptionsRecyclerRow;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();


        if (getActivity() != null && isAdded())
            ((QuizActivity) getActivity()).tvQuizPosition.setText(String.valueOf(((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1));

        if (getActivity() != null && isAdded())
            ((QuizActivity) getActivity()).tv_quiz_time.setText(String.format("%s:%s", "0", "00"));

        buildOptionRecycler();

        if (getArguments() != null) {
            QuestionList.AvailableQuestionListItem questionItem = (QuestionList.AvailableQuestionListItem) getArguments().getSerializable(Util.QUESTION);
            if (questionItem != null) {
                /*set question name*/
                tv_question_name.setText(questionItem.getTitle());
                quizOptionsRecyclerRow = new QuizOptionsRecyclerRow(questionItem.getOptions(), getActivity(), questionItem.getQuestionId(), questionItem.getPoint());
                optionRecyclerView.setAdapter(quizOptionsRecyclerRow);
                TimeCount(Util.getMillisecondsFromMinutes(questionItem.getTimeLimit()));
            }
        }



        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (getActivity() != null && isAdded()){
                    if(((QuizActivity)getActivity()).questionList != null)
                   if(((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1 == ((QuizActivity)getActivity()).questionList.getAvailableQuestionList().size()){
                       Congratsdialog congratsdialog = new Congratsdialog();
                       congratsdialog.show(getChildFragmentManager(), "");
                   }else{
                        ((QuizActivity) getActivity()).quizViewPager.setCurrentItem(((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1, true);
                        ((QuizActivity) getActivity()).tvQuizPosition.setText(String.valueOf(((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1));
                    }

                }


            }
        });
    }


    private  void TimeCount(long milliseconds) {
         countDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long l) {
                /*call this method every one second with reducing 1000 millisecond */
                setQuizTime(Util.getTimeFromMillisecond(l));
            }

            @Override
            public void onFinish() {
                /*code to execute after the countdown finish*/
            }
        };

        countDownTimer.start();
    }


    @SuppressLint("DefaultLocale")
    private void setQuizTime(HashMap<String, Integer> timeFromMillisecond) {
        if (getActivity() != null && isAdded())
            ((QuizActivity) getActivity()).tv_quiz_time.setText(String.format("%d:%d", timeFromMillisecond.get("min"), timeFromMillisecond.get("sec")));
    }

    private void buildOptionRecycler() {
        optionRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        optionRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            tv_question_name = view.findViewById(R.id.tv_question_name);
            optionRecyclerView = view.findViewById(R.id.recyclerview_quiz_option);
            btnSkip = view.findViewById(R.id.btn_skip);
        }
    }



}
