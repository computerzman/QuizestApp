package com.quizest.quizestapp.NetworkPackage;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.quizest.quizestapp.ActivityPackage.AuthActivity;

public class ErrorHandler extends Application{

   static ErrorHandler mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static synchronized ErrorHandler getInstance() {
        if (mInstance == null) {
            mInstance = new ErrorHandler();
        }
        return mInstance;
    }

    public void handleError(int code, Activity activity){

        switch (code){
            case 500:
                Toast.makeText(activity, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                break;

            case 400:
                Toast.makeText(activity, "Invalid Request!", Toast.LENGTH_SHORT).show();
                break;

            case 429:
                Toast.makeText(activity, "Too Many Request, Please Try Again Later!", Toast.LENGTH_SHORT).show();
                break;

            case 401:
                Toast.makeText(activity, "Session Expired!", Toast.LENGTH_SHORT).show();
                goToLogInActivity(activity);
                break;
        }

    }


    private void goToLogInActivity(Activity activity){
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
