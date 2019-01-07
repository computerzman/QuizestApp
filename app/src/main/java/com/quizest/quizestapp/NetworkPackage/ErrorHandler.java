package com.quizest.quizestapp.NetworkPackage;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quizest.quizestapp.ActivityPackage.AuthActivity;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.UtilPackge.Util;

public class ErrorHandler extends Application {
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

    public void handleError(int code, Activity activity, ProgressDialog dialog) {
        switch (code) {
            case 500:
                Toast.makeText(activity, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                Util.dissmisDialog(dialog);
                break;

            case 400:
                Toast.makeText(activity, "Invalid Request!", Toast.LENGTH_SHORT).show();
                Util.dissmisDialog(dialog);
                break;

            case 429:
                Toast.makeText(activity, "Too Many Request, Please Try Again Later!", Toast.LENGTH_SHORT).show();
                Util.dissmisDialog(dialog);
                break;

            case 401:
                Toast.makeText(activity, "Session Expired!", Toast.LENGTH_SHORT).show();
                if (dialog != null) {
                    Util.dissmisDialog(dialog);
                }
                goToLogInActivity(activity);
                break;
        }
    }


    private void goToLogInActivity(Activity activity) {
        /*make the current user logged out*/
        Storage storage = new Storage(activity);
        storage.SaveLogInSate(false);
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

}
