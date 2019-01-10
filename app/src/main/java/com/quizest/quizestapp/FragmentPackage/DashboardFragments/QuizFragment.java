package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.UniversalTimeScale;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Helper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.ActivityPackage.QuizActivity;
import com.quizest.quizestapp.AdapterPackage.QuizOptionsRecyclerRow;
import com.quizest.quizestapp.DialogPackage.Congratsdialog;
import com.quizest.quizestapp.ModelPackage.QuestionList;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    int count = 0;
    Button btnSkip, btn_next;
    ImageView ivAnswerA;
    Button quiz;
    TextView tv_question_name, tv_user_point;
    TextView tvQuizCount, tv_quiz_time, tvQuizPosition;
    RecyclerView optionRecyclerView;
    ImageView catStatus, iv_stopwatch;
    QuestionList.AvailableQuestionListItem questionItem;
    QuizOptionsRecyclerRow quizOptionsRecyclerRow;
    private static QuizFragment quizFragment = null;
    View view;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInsatance(QuestionList.AvailableQuestionListItem item) {
        Bundle bundleGot = new Bundle();
        bundleGot.putSerializable(Util.QUESTION, item);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(bundleGot);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_quiz, container, false);

        initViews();

        tv_user_point.setText(String.valueOf(Util.QuizPoint));

        if (getActivity() != null && isAdded()) {
            iv_stopwatch.setImageResource(R.drawable.ic_stopwatch);
            tvQuizPosition.setText(String.format("%d", ((QuizActivity) getActivity()).x));
            tv_quiz_time.setText(String.format("%s:%s", "0", "00"));
        }

        buildOptionRecycler();

        if (getArguments() != null) {
            questionItem = (QuestionList.AvailableQuestionListItem) getArguments().getSerializable(Util.QUESTION);
            if (questionItem != null) {

                TimeCount(Util.getMillisecondsFromMinutes(questionItem.getTimeLimit()));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    tvQuizCount.setText(String.format("/%d", ((QuizActivity) Objects.requireNonNull(getActivity())).questionList.getAvailableQuestionList().size()));
                }

                tv_question_name.setText(questionItem.getTitle());
                quizOptionsRecyclerRow = new QuizOptionsRecyclerRow(questionItem.getOptions(), getActivity(), questionItem.getQuestionId(), questionItem.getPoint(), catStatus, tv_user_point);
                optionRecyclerView.setAdapter(quizOptionsRecyclerRow);


            }
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (QuizActivity.isPlayed.get(questionItem.getQuestionId())) {

                        btnSkip.performClick();

                    } else {
                        Toast.makeText(getActivity(), "sorry, Play first!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "sorry, Play first!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (getActivity() != null && isAdded()) {
                    if (((QuizActivity) getActivity()).questionList != null) {
                        Log.e("size", String.valueOf(((QuizActivity) getActivity()).questionList.getAvailableQuestionList().size()));
                        if (((QuizActivity) getActivity()).x
                                < ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().size()) {
                            QuizFragment quizFragment = new QuizFragment();
                            Bundle bundle = new Bundle();
                            if (getActivity() != null)
                                bundle.putSerializable(Util.QUESTION, ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().get(((QuizActivity) getActivity()).x++));
                            quizFragment.setArguments(bundle);

                            ((QuizActivity) getActivity()).fragmentTransition(quizFragment);

                        } else {
                            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                            dialog.setContentView(R.layout.layout_congrats_dialog);
                            TextView tv_result = dialog.findViewById(R.id.tv_result);
                            quiz = dialog.findViewById(R.id.btn_quiz);

                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });
                            quiz.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            });
                            tv_result.setText(String.valueOf(Util.TOTAL_POINT));
                            dialog.show();
                        }
                    }
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    private void TimeCount(long milliseconds) {
        if (getActivity() != null)
            QuizActivity.countDownTimer = new CountDownTimer(milliseconds, 1000) {
                @Override
                public void onTick(long l) {

                    if ((l / 1000) <= 10) {
                        doBlinkAnimation(tv_quiz_time);
                        iv_stopwatch.setImageResource(R.drawable.ic_frown);


                    }
                    setQuizTime(Util.getTimeFromMillisecond(l));
                }

                @Override
                public void onFinish() {
                    tv_quiz_time.setText(String.format("%s:%s", "0", "00"));
                    removeBlinkAnimation(tv_quiz_time);
                    iv_stopwatch.setImageResource(R.drawable.ic_stopwatch);
                    btnSkip.performClick();
                    cancel();
                }
            };
        QuizActivity.countDownTimer.start();
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
        if (view != null) {
            btn_next = view.findViewById(R.id.btn_next);
            tv_user_point = view.findViewById(R.id.tv_user_point);
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
        if (getActivity() != null)
            if (QuizActivity.countDownTimer != null) {
                QuizActivity.countDownTimer.cancel();
            }
        super.onDestroyView();
    }


}
