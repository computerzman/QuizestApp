package com.quizest.quizestapp.ActivityPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quizest.quizestapp.AdapterPackage.QuizViewPagerAdapter;
import com.quizest.quizestapp.CustomViews.ViewPagerCustomDuration;
import com.quizest.quizestapp.FragmentPackage.DashboardFragments.QuizFragment;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private String QUESTION_ID;
    public ViewPagerCustomDuration quizViewPager;
    List<Fragment> quizList;
    QuizViewPagerAdapter quizViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initView();
        QUESTION_ID = getIntent().getStringExtra(Util.QUIZLIST);
        if(QUESTION_ID != null){
            getQuestionList(QUESTION_ID);
        }

    }


    private void initView() {
        quizViewPager = findViewById(R.id.vp_quiz);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void getQuestionList(String QUESTION_ID) {
        final ProgressDialog dialog = Util.showDialog(this);
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        final Call<String> questionCall = retrofitInterface.getQuizList(RetrofitClient.CATEGORY_URL + QUESTION_ID);
        questionCall.enqueue(new Callback<String>() {
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
                            QuestionList questionList = gson.fromJson(response.body(), QuestionList.class);
                            for (QuestionList.AvailableQuestionListItem questions : questionList.getAvailableQuestionList()) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Util.QUESTION, (Serializable) questions);
                                Fragment fragment = new QuizFragment();
                                fragment.setArguments(bundle);
                                quizList.add(fragment);

                                /*build view pager with option list */
                                quizViewPagerAdapter = new QuizViewPagerAdapter(getSupportFragmentManager(), quizList, QuizActivity.this);
                                quizViewPager.setAdapter(quizViewPagerAdapter);
                                quizViewPager.setScrollDurationFactor(2);


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
}
