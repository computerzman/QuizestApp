package com.quizest.quizestapp.ActivityPackage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

   public QuestionList questionList;
    public CustomViewPager quizViewPager;
    List<Fragment> quizList;
    public int x  = 0;
    QuizViewPagerAdapter quizViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        Util.TOTAL_POINT = 0;

        quizList = new ArrayList<>();

        initView();


        String QUESTION_ID = getIntent().getStringExtra(Util.QUIZLIST);
        if (QUESTION_ID != null) {
            getQuestionList(QUESTION_ID);
        }

        ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float position) {
                if (position <= -1.0F || position >= 1.0F) {
                    view.setTranslationX(view.getWidth() * position);
                    view.setAlpha(0.0F);
                } else if (position == 0.0F) {
                    view.setTranslationX(view.getWidth() * position);
                    view.setAlpha(1.0F);
                } else {
                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    view.setTranslationX(view.getWidth() * -position);
                    view.setAlpha(1.0F - Math.abs(position));
                }
            }
        };

        quizViewPager.setPageTransformer(false, transformer);
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


                            /*set available question list size*/
                         //

                            for (QuestionList.AvailableQuestionListItem questions : questionList.getAvailableQuestionList()) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Util.QUESTION, (Serializable) questions);
                                Fragment fragment = new QuizFragment();
                                fragment.setArguments(bundle);
                                quizList.add(fragment);

                                /*build view pager with option list */
                                quizViewPagerAdapter = new QuizViewPagerAdapter(getSupportFragmentManager(), quizList, QuizActivity.this);
                                quizViewPager.setAdapter(quizViewPagerAdapter);
                                quizViewPager.setPagingEnabled(false);


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
