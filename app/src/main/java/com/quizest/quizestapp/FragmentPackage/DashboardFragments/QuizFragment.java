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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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


    CounterClass counterClass;
    Button btnSkip;
    ImageView ivAnswerA;
    private static CountDownTimer countDownTimer = null;
    TextView tv_question_name;
    TextView tvQuizCount, tv_quiz_time, tvQuizPosition;
    RecyclerView optionRecyclerView;
    ImageView catStatus, iv_stopwatch;
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

        if (getActivity() != null && isAdded()) {


            iv_stopwatch.setImageResource(R.drawable.ic_stopwatch);
            tvQuizPosition.setText(String.format("%d", ((QuizActivity) getActivity()).x++));
            tv_quiz_time.setText(String.format("%s:%s", "0", "00"));
        }

        buildOptionRecycler();

        if (getArguments() != null) {
            QuestionList.AvailableQuestionListItem questionItem = (QuestionList.AvailableQuestionListItem) getArguments().getSerializable(Util.QUESTION);
            if (questionItem != null) {
                /*set question name*/

                if(counterClass != null){
                    counterClass.cancel();
                }
                counterClass = new CounterClass(Util.getMillisecondsFromMinutes(questionItem.getTimeLimit()), 1000);
                counterClass.start();
                //TimeCount(Util.getMillisecondsFromMinutes(questionItem.getTimeLimit()));
                tvQuizCount.setText(String.format("/%d", ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().size()));
                tv_question_name.setText(questionItem.getTitle());
                quizOptionsRecyclerRow = new QuizOptionsRecyclerRow(questionItem.getOptions(), getActivity(), questionItem.getQuestionId(), questionItem.getPoint(), catStatus);
                optionRecyclerView.setAdapter(quizOptionsRecyclerRow);



            }
        }


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null && isAdded()) {
                    if (((QuizActivity) getActivity()).questionList != null) {
                        if (((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1 == ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().size()) {
                            Congratsdialog congratsdialog = new Congratsdialog();
                            congratsdialog.show(getChildFragmentManager(), "");
                        } else {
                            ((QuizActivity) getActivity()).quizViewPager.setCurrentItem(((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1, true);
                        }
                    }
                }
            }
        });
    }

    class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            /*call this method every one second with reducing 1000 millisecond */
            if ((millisUntilFinished / 1000) <= 10) {
                doBlinkAnimation(tv_quiz_time);
                iv_stopwatch.setImageResource(R.drawable.ic_frown);
            }
            setQuizTime(Util.getTimeFromMillisecond(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            removeBlinkAnimation(tv_quiz_time);
            iv_stopwatch.setImageResource(R.drawable.ic_stopwatch);
            tv_quiz_time.setText(String.format("%s:%s", "0", "00"));
            if (getActivity() != null && isAdded())
                Log.e("MKTESTTIME", "HOW MUCH TIME?");
            ((QuizActivity) getActivity()).quizViewPager.setCurrentItem(((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1, true);
        }

        public void cancelTime(){
            cancel();
        }
    }

    private void TimeCount(long milliseconds) {
        countDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long l) {
                /*call this method every one second with reducing 1000 millisecond */
                if ((l / 1000) <= 10) {
                    doBlinkAnimation(tv_quiz_time);
                    iv_stopwatch.setImageResource(R.drawable.ic_frown);
                }
                setQuizTime(Util.getTimeFromMillisecond(l));
            }

            @Override
            public void onFinish() {
                removeBlinkAnimation(tv_quiz_time);
                iv_stopwatch.setImageResource(R.drawable.ic_stopwatch);
                tv_quiz_time.setText(String.format("%s:%s", "0", "00"));
                if (getActivity() != null && isAdded())
                    Log.e("MKTESTTIME", "HOW MUCH TIME?");
                ((QuizActivity) getActivity()).quizViewPager.setCurrentItem(((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1, true);


            }
        };
        countDownTimer.start();
    }


    @SuppressLint("DefaultLocale")
    private void setQuizTime(HashMap<String, Integer> timeFromMillisecond) {
        if (getActivity() != null && isAdded()) {
            tv_quiz_time.setText(String.format("%s:%s", String.valueOf(timeFromMillisecond.get("min")), String.valueOf(timeFromMillisecond.get("sec"))));


        }

    }

    private void buildOptionRecycler() {
        optionRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        optionRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            catStatus = view.findViewById(R.id.img_cat_status);
            tv_quiz_time = view.findViewById(R.id.tv_quiz_time);
            tvQuizCount = view.findViewById(R.id.tv_quiz_count);
            tvQuizPosition = view.findViewById(R.id.tv_quiz_position);
            iv_stopwatch = view.findViewById(R.id.iv_stopwatch);
            tv_question_name = view.findViewById(R.id.tv_question_name);
            optionRecyclerView = view.findViewById(R.id.recyclerview_quiz_option);
            btnSkip = view.findViewById(R.id.btn_skip);
        }
    }


    private void doBlinkAnimation(TextView textView) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);
    }


    private void removeBlinkAnimation(TextView textView) {
        textView.clearAnimation();
    }

    @Override
    public void onDestroyView() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroyView();
    }


}
