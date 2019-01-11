package com.quizest.quizestapp.ActivityPackage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quizest.quizestapp.AdapterPackage.QuizViewPagerAdapter;
import com.quizest.quizestapp.CustomViews.CustomViewPager;
import com.quizest.quizestapp.CustomViews.ViewPagerCustomDuration;
import com.quizest.quizestapp.FragmentPackage.DashboardFragments.QuizFragment;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.ModelPackage.QuestionList;
import com.quizest.quizestapp.ModelPackage.UserLogIn;
import com.quizest.quizestapp.NetworkPackage.ErrorHandler;
import com.quizest.quizestapp.NetworkPackage.RetrofitClient;
import com.quizest.quizestapp.NetworkPackage.RetrofitInterface;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    /*all global field instances are here*/
    ImageButton btn_back, btnSetting;
    Fragment currentFragment;
    public QuestionList questionList;

    public static HashMap<String, Boolean> isPlayed;
    List<Fragment> quizList;
    public int x =
            1;
    public int c = 1;
    public static CountDownTimer countDownTimer;
    QuizViewPagerAdapter quizViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*add the layout to the activity*/
        setContentView(R.layout.activity_quiz);



        /*type casting the views*/
        btn_back = findViewById(R.id.btn_back);
        btnSetting = findViewById(R.id.btn_setting);

        /*if user clicks on the back button take user to MainActivity*/
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                QuizActivity.this.overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        });

/*if user clicks on the setting button take the user to the Setting activity*/
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, SettingActivity.class);
                startActivity(intent);
                QuizActivity.this.overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        });

        /*track the total point of a user globally*/
        Util.TOTAL_POINT = 0;

        /*intilize the lists*/
        quizList = new ArrayList<>();

        isPlayed = new HashMap<>();

        isPlayed.clear();

        /*get the question id from intent and pass it to the getQuestionList function to do the api call for question list */

        String QUESTION_ID = getIntent().getStringExtra(Util.QUIZLIST);
        if (QUESTION_ID != null) {
            getQuestionList(QUESTION_ID);
        }


    }



    /*this is called when user press back button*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    /*this is the api call to get data from the server we passes question id to get the question list */
    private void getQuestionList(String QUESTION_ID) {
        Storage storage = new Storage(this);
        final ProgressDialog dialog = Util.showDialog(this);
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        final Call<String> questionCall = retrofitInterface.getQuizList(RetrofitClient.CATEGORY_URL + QUESTION_ID, storage.getAccessToken());
        questionCall.enqueue(new Callback<String>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                /*handle http error globally*/
                ErrorHandler.getInstance().handleError(response.code(), QuizActivity.this, dialog);
                if (response.isSuccessful()) {
                    /*success true*/
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean isSuccess = jsonObject.getBoolean("success");
                        if (isSuccess) {
                            /*serialize the String response  */

                            Gson gson = new Gson();
                            questionList = gson.fromJson(response.body(), QuestionList.class);


                            if (questionList != null) {
                                if (questionList.getAvailableQuestionList().size() != 0) {
                                    QuizFragment quizFragment = new QuizFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(Util.QUESTION, questionList.getAvailableQuestionList().get(0));
                                    quizFragment.setArguments(bundle);
                                    fragmentTransition(quizFragment);
                                } else {
                                    Toast.makeText(QuizActivity.this, "NO Question Found!", Toast.LENGTH_SHORT).show();
                                }
                            }



                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);
                        } else {
                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);
                            /*get all the error messages and show to the user*/
                            String message = jsonObject.getString("message");
                            Toast.makeText(QuizActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Util.dissmisDialog(dialog);
                    Toast.makeText(QuizActivity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*dismiss the dialog*/
                Util.dissmisDialog(dialog);
                /*handle network error and notify the user*/
                if (t instanceof SocketTimeoutException || t instanceof IOException) {
                    Toast.makeText(QuizActivity.this, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*replace the current fragment with new fragment*/
    public void fragmentTransition(Fragment fragment) {
        this.currentFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.vp_quiz, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


}
