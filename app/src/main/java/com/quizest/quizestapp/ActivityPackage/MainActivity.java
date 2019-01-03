package com.quizest.quizestapp.ActivityPackage;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.quizest.quizestapp.FragmentPackage.DashboardFragments.EarnCoinFragment;
import com.quizest.quizestapp.FragmentPackage.DashboardFragments.LeaderBoardFragment;
import com.quizest.quizestapp.FragmentPackage.DashboardFragments.HomeFragment;
import com.quizest.quizestapp.FragmentPackage.DashboardFragments.ViewProfileFragment;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bv_BottomBar;
    Fragment currentFragment;
    LinearLayout topPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*make the statusbar transparent if version is above kitkat*//*

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

        /*type casting views */
        initviews();


        fragmentTransition(new HomeFragment());

        /*removing shifting animation from bottom view */
        Util.removeShiftMode(bv_BottomBar);


        bv_BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        RestMode();
                        if (!(currentFragment instanceof HomeFragment))
                            currentFragment = new HomeFragment();
                        break;
                    case R.id.navigation_earncoin:
                        RestMode();
                        currentFragment
                                = new EarnCoinFragment();
                        break;
                    case R.id.navigation_profile:
                        profileMode();
                        currentFragment = new ViewProfileFragment();
                        break;
                    case R.id.navigation_leaderboard:
                        RestMode();
                        if (!(currentFragment instanceof LeaderBoardFragment))
                            currentFragment = new LeaderBoardFragment();
                        break;
                }

                fragmentTransition(currentFragment);

                return true;
            }
        });


    }


    private void profileMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_holo_blue));// SDK21
        }
        topPanel.setVisibility(View.GONE);
    }

    private void RestMode() {

        //*make the statusbar transparent if version is above kitkat*//*

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
        topPanel.setVisibility(View.VISIBLE);
    }

    public void fragmentTransition(Fragment fragment) {
        this.currentFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();
    }


    private void initviews() {
        topPanel = findViewById(R.id.top_panel);
        bv_BottomBar = findViewById(R.id.bv_bottom_bar);
    }
}
