package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.ActivityPackage.QuizActivity;
import com.quizest.quizestapp.AdapterPackage.QuizOptionsRecyclerRow;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.ModelPackage.QuestionList;
import com.quizest.quizestapp.NetworkPackage.ErrorHandler;
import com.quizest.quizestapp.NetworkPackage.RetrofitClient;
import com.quizest.quizestapp.NetworkPackage.RetrofitInterface;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    /*all global field instances */
    AdView adView;
    int count = 0;
    Button btnSkip, btn_next;
    ImageView ivAnswerA;
    Button quiz;

    public int TOTAL_COIN = 0;
    public int TOTAL_POINT = 0;
    private int HINT_POINT = 0;

    TextView tv_question_name, tv_user_point, tv_user_coin;
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


        /*inflate the fragment layout*/
        view = inflater.inflate(R.layout.fragment_quiz, container, false);

        initViews();

        /*show the admob ads*/
        adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        if (getActivity() != null && isAdded()) {
            /*make the counter value to default*/
            iv_stopwatch.setImageResource(R.drawable.ic_stopwatch);
            tvQuizPosition.setText(String.format("%d", ((QuizActivity) getActivity()).x));
            tv_quiz_time.setText(String.format("%s:%s", "0", "00"));
        }


        buildOptionRecycler();


        if (getArguments() != null) {
            questionItem = (QuestionList.AvailableQuestionListItem) getArguments().getSerializable(Util.QUESTION);
            if (questionItem != null) {
                /*start the count by getting the toatl time*/
                TimeCount(Util.getMillisecondsFromMinutes(questionItem.getTimeLimit()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    tvQuizCount.setText(String.format("/%d", ((QuizActivity) Objects.requireNonNull(getActivity())).questionList.getAvailableQuestionList().size()));
                }
                /*make the option recycler view*/
                tv_question_name.setText(questionItem.getTitle());
                quizOptionsRecyclerRow = new QuizOptionsRecyclerRow(this, questionItem.getOptions(), getActivity(), questionItem.getQuestionId(), questionItem.getPoint(), questionItem.getCoin(), catStatus, tv_user_coin, tv_user_point);
                optionRecyclerView.setAdapter(quizOptionsRecyclerRow);


                /*take total user coin, point and hint point*/

                TOTAL_POINT = getArguments().getInt("TOTAL_POINT");
                TOTAL_COIN = getArguments().getInt("TOTAL_COIN");
                HINT_POINT = getArguments().getInt("HINT_POINT");


                tv_user_coin.setText(String.valueOf(TOTAL_COIN));
                tv_user_point.setText(String.valueOf(TOTAL_POINT));
            }

        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if user clicks the next button chcek if the quesetion is played and then take the user to the next question*/
                try {
                    if (QuizActivity.isPlayed.get(questionItem.getQuestionId())) {

                        goToQuestion();

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

                /*  *//*take the user to the next question if all question are finished then show the result dialog*//*
                if (getActivity() != null && isAdded()) {
                    if (((QuizActivity) getActivity()).questionList != null) {
                        //   Log.e("size", String.valueOf(((QuizActivity) getActivity()).questionList.getAvailableQuestionList().size()));
                        if (((QuizActivity) getActivity()).x
                                < ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().size()) {
                            QuizFragment quizFragment = new QuizFragment();
                            Bundle bundle = new Bundle();
                            if (getActivity() != null)
                                bundle.putSerializable(Util.QUESTION, ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().get(((QuizActivity) getActivity()).x++));
                            bundle.putInt("TOTAL_POINT", TOTAL_POINT);
                            bundle.putInt("TOTAL_COIN", TOTAL_COIN);
                            bundle.putInt("HINT_POINT", HINT_POINT);
                            quizFragment.setArguments(bundle);

                            ((QuizActivity) getActivity()).fragmentTransition(quizFragment);

                        } else {

                            *//*take the user to the result dialog*//*
                            Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                            dialog.setContentView(R.layout.layout_congrats_dialog);
                            TextView earnedPoint = dialog.findViewById(R.id.tv_earned_point);
                            TextView earnedCoin = dialog.findViewById(R.id.tv_earned_coin);
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
                            earnedCoin.setText(String.valueOf(Util.TOTAL_COIN));
                            earnedPoint.setText(String.valueOf(Util.TOTAL_POINT));
                            dialog.show();
                        }
                    }
                }*/


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.skip_question_dialog_layout);
                final TextView categoryName = dialog.findViewById(R.id.tv_dialog_categoryname);
                TextView coinNeed = dialog.findViewById(R.id.tv_dialog_coin_need);
                TextView coinIHave = dialog.findViewById(R.id.tv_dialog_coin_ihave);
                Button letStart = dialog.findViewById(R.id.btn_let_start);
                Button cancel = dialog.findViewById(R.id.btn_cancel);

                final LinearLayout coin;
                final ProgressBar progressBar;
                final TextView message;

                message = dialog.findViewById(R.id.tv_message);
                coin = dialog.findViewById(R.id.layout_coin);
                progressBar = dialog.findViewById(R.id.pb_progress);

                dialog.getWindow().getAttributes().windowAnimations = R.style.alert_dialog;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                coinNeed.setText(String.format("%d Coin", questionItem.getSkipCoin()));
                coinIHave.setText(String.format("%d", TOTAL_COIN));

                letStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        expandCoin(dialog, questionItem.getSkipCoin(), progressBar, coin, message, categoryName);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                // coinIHave.setText(String.format("%d Coin", categoryModelList.get(getAdapterPosition()).get));

                dialog.show();

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

    /*funcation for go to next question*/
    private void goToQuestion() {
        /*take the user to the next question if all question are finished then show the result dialog*/
        if (getActivity() != null && isAdded()) {
            if (((QuizActivity) getActivity()).questionList != null) {
                if (((QuizActivity) getActivity()).x
                        < ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().size()) {
                    QuizFragment quizFragment = new QuizFragment();
                    Bundle bundle = new Bundle();
                    if (getActivity() != null)
                        bundle.putSerializable(Util.QUESTION, ((QuizActivity) getActivity()).questionList.getAvailableQuestionList().get(((QuizActivity) getActivity()).x++));
                    bundle.putInt("TOTAL_POINT", TOTAL_POINT);
                    bundle.putInt("TOTAL_COIN", TOTAL_COIN);
                    bundle.putInt("HINT_POINT", HINT_POINT);
                    quizFragment.setArguments(bundle);

                    ((QuizActivity) getActivity()).fragmentTransition(quizFragment);

                } else {

                    /*take the user to the result dialog*/
                    Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    dialog.setContentView(R.layout.layout_congrats_dialog);
                    TextView earnedPoint = dialog.findViewById(R.id.tv_earned_point);
                    TextView earnedCoin = dialog.findViewById(R.id.tv_earned_coin);
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
                    earnedCoin.setText(String.valueOf(Util.TOTAL_COIN));
                    earnedPoint.setText(String.valueOf(Util.TOTAL_POINT));
                    dialog.show();
                }
            }
        }

    }


    /*start the cound down timer*/
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


    //    set data to the timer
    @SuppressLint("DefaultLocale")
    private void setQuizTime(HashMap<String, Integer> timeFromMillisecond) {
        if (getActivity() != null && isAdded()) {
            tv_quiz_time.setText(String.format("%s:%s", String.valueOf(timeFromMillisecond.get("min")), String.valueOf(timeFromMillisecond.get("sec"))));
        }

    }


    /*make the */
    private void buildOptionRecycler() {
        optionRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        optionRecyclerView.setLayoutManager(gridLayoutManager);
    }

    /*type casting the views*/
    private void initViews() {
        if (view != null) {

            tv_user_coin = view.findViewById(R.id.tv_usercoin);
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


    //  do the animation
    private void doBlinkAnimation(TextView textView) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);
    }


    /*remove the animation */
    private void removeBlinkAnimation(TextView textView) {
        textView.clearAnimation();
    }


    /*destroyh the view */
    @Override
    public void onDestroyView() {
        if (getActivity() != null)
            if (QuizActivity.countDownTimer != null) {
                QuizActivity.countDownTimer.cancel();
            }
        super.onDestroyView();
    }


    private void expandCoin(final Dialog dialog, final int amount, final ProgressBar progressBar, LinearLayout coin, final TextView message, TextView categoryName) {

        progressBar.setVisibility(View.VISIBLE);

        categoryName.setVisibility(View.GONE);
        coin.setVisibility(View.GONE);


        Storage storage = new Storage(getActivity());
        RetrofitInterface networkInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        Call<String> expandCoint = networkInterface.expandCoin(storage.getAccessToken(), amount);
        expandCoint.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                /*handle error globally */
                ErrorHandler.getInstance().handleError(response.code(), getActivity(), null);
                if (response.isSuccessful()) {
                    /*success true*/
                    try {

                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean isSuccess = jsonObject.getBoolean("success");

                        if (isSuccess) {

                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }

                            /*get all the error messages and show to the user*/
                            String message = jsonObject.getString("message");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                            /*detuct the coin from total coin*/

                            if (TOTAL_COIN != 0) {
                                TOTAL_COIN = TOTAL_COIN - amount;
                            }


                            /*code for go to next question*/

                            goToQuestion();

                        } else {
                            /*get all the error messages and show to the user*/
                            String messageTxt = jsonObject.getString("message");
                            progressBar.setVisibility(View.GONE);
                            message.setVisibility(View.VISIBLE);
                            message.setText(messageTxt);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*handle network error and notify the user*/
                if (t instanceof SocketTimeoutException || t instanceof IOException) {
                    Toast.makeText(getActivity(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
