package com.quizest.quizestapp.ActivityPackage;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quizest.quizestapp.AdapterPackage.QuizViewPagerAdapter;
import com.quizest.quizestapp.CustomViews.ViewPagerCustomDuration;
import com.quizest.quizestapp.FragmentPackage.DashboardFragments.QuizFragment;
import com.quizest.quizestapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public ViewPagerCustomDuration quizViewPager;
    List<Fragment> quizList;
    QuizViewPagerAdapter quizViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initView();

        quizList = new ArrayList<>();
        quizList.add(new QuizFragment());
        quizList.add(new QuizFragment());
        quizList.add(new QuizFragment());


        quizViewPagerAdapter = new QuizViewPagerAdapter(getSupportFragmentManager(), quizList, this);
        quizViewPager.setAdapter(quizViewPagerAdapter);
        quizViewPager.setScrollDurationFactor(5);

    }


    private void initView() {
        quizViewPager = findViewById(R.id.vp_quiz);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
