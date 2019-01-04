package com.quizest.quizestapp.UtilPackge;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.widget.Toast;

import com.quizest.quizestapp.R;

import java.lang.reflect.Field;
import java.util.Random;

public class Util {

    private static int UPPER_BOUND = 7;
    public static int LAST_GRADIENT = 0;

    /*this method disable shifting Animation of bottom navigation bar*/
    public static void removeShiftMode(BottomNavigationView view) {

        /*get first bottom navigation menu view from bottom navigation  */
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            /*get field of bottom view from menu view */
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            /*set accessible true to Field*/
            shiftingMode.setAccessible(true);
            /*set boolean to field with menu b*/
            shiftingMode.setBoolean(menuView, false);
            /*then set accessible false to field */
            shiftingMode.setAccessible(false);
            /*for every menu view in the bottom navigation set shifting mode false and set checked the item data is checked*/
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

   public static  int generateRandom(int lastRandomNumber) {
        Random random = new Random();
        // add-and-wrap another random number to produce a guaranteed
        // different result.
        // note the one-less-than UPPER_BOUND input
        int rotate = 1 + random.nextInt(UPPER_BOUND - 1);
        // 'rotate' the last number
        return (lastRandomNumber + rotate) % UPPER_BOUND;

    }

    public static int getRandomCategoryGradient(int num) {
        Util.LAST_GRADIENT = num;
        int gradient = 0;
        switch (num) {
            case 0:
                gradient = R.drawable.gradient_holo_brown_category;
                break;
            case 1:
                gradient = R.drawable.gradient_pink_category;
                break;

            case 2:
                gradient = R.drawable.gradient_holo_gray;
                break;
            case 3:
                gradient = R.drawable.gradient_holo_pink_cate;
                break;
            case 4:
                gradient = R.drawable.gradient_holo_yallow;
                break;
            case 5:
                gradient = R.drawable.gradient_sky_blue;
                break;
            case 6:
                gradient = R.drawable.gradient_holo_green;
                break;

            case 7:
                gradient = R.drawable.gradient_pink_category;
                break;
        }

        return gradient;
    }


    public static boolean isInternetAvaiable(Activity activity){
        final ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
        //    Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

}
