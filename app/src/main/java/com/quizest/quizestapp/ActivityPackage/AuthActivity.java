package com.quizest.quizestapp.ActivityPackage;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.quizest.quizestapp.FragmentPackage.AuthFragments.ForgotPasswordFragment;
import com.quizest.quizestapp.FragmentPackage.AuthFragments.LogInFragment;
import com.quizest.quizestapp.FragmentPackage.AuthFragments.RegisterFragment;
import com.quizest.quizestapp.R;

public class AuthActivity extends AppCompatActivity {

    Fragment currentFragment;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

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


        fragmentTransition(new RegisterFragment());
    }


    public void fragmentTransition(Fragment fragment) {
        this.currentFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fl_auth_container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {


        if (currentFragment instanceof RegisterFragment || currentFragment instanceof ForgotPasswordFragment) {

            this.fragmentTransition(new LogInFragment());

        } else if (currentFragment instanceof LogInFragment) {

            count++;

            if (count == 1) {

                Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();

            } else if (count == 2) {

                super.onBackPressed();
            }
        }


    }


}
