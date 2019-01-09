package com.quizest.quizestapp.ActivityPackage;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.quizest.quizestapp.AdapterPackage.ImageSliderAdapter;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.R;
import com.rd.PageIndicatorView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    /*all global field instances are here*/
    ViewPager vpSplashPager;
    ImageSliderAdapter imageSliderAdapter;
    PageIndicatorView pageIndicatorView;
    TextView tvSplashTitle, tvSplashDesc;
    TextView tvSplashSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //*make the statusbar transparent if version is above kitkat*//*


        Locale locale = Locale.getDefault();
        Resources resources =this.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLayoutDirection(locale);
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        initViews();


        /*make list of onboarding image here*/

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.img_splash_blue);
        imageList.add(R.drawable.img_splash_purple);
        imageList.add(R.drawable.img_splash_orange);

        /*build the image adapter for splash view pager*/
        imageSliderAdapter = new ImageSliderAdapter(imageList, this);

        /*attach the adapter to the view pager*/
        vpSplashPager.setAdapter(imageSliderAdapter);

        pageIndicatorView.setViewPager(vpSplashPager);


        pageIndicatorView.setCount(3);

        pageIndicatorView.setSelection(vpSplashPager.getCurrentItem());

        vpSplashPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                pageIndicatorView.setSelection(position);


                switch (position) {
                    case 0:

                        setTvSplashTextDesapiarAnimator();
                        break;

                    case 1:


                        setTvSplashTextDesapiarAnimator();
                        break;

                    case 2:

                        setTvSplashTextDesapiarAnimator();
                        break;


                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvSplashSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Storage storage = new Storage(SplashActivity.this);
                if(storage.getLogInState()){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    SplashActivity.this.overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                    finish();
                }

            }
        });


    }


    private void initViews() {
        vpSplashPager = findViewById(R.id.vp_splash);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        tvSplashDesc = findViewById(R.id.tv_splash_desc);
        tvSplashSkip = findViewById(R.id.tv_splash_skip);
        tvSplashTitle = findViewById(R.id.tv_splash_title);
    }


    private void setTvSplashTextDesapiarAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(2500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                tvSplashDesc.setAlpha(alpha);
                tvSplashTitle.setAlpha(alpha);
            }
        });
        valueAnimator.start();
    }
}
