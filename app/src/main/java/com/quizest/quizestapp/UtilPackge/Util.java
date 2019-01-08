package com.quizest.quizestapp.UtilPackge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.widget.Toast;

import com.quizest.quizestapp.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Util {

    public static int TOTAL_POINT = 0;
    public static final String QUIZLIST = "Quiz";
    public static final String QUESTION = "question";
    public static String REQUEST_TYPE = "application/json";
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

    public static int generateRandom(int lastRandomNumber) {
        Random random = new Random();
        // add-and-wrap another random number to produce a guaranteed
        // different result.
        // note the one-less-than UPPER_BOUND input
        int rotate = 1 + random.nextInt(UPPER_BOUND - 1);
        // 'rotate' the last number
        return (lastRandomNumber + rotate) % UPPER_BOUND;

    }


    public static long getMillisecondsFromMinutes(int min) {
        return (min * 60) * 1000;
    }

    public static HashMap<String, Integer> getTimeFromMillisecond(long millis) {

        /*make a placeholder hash map object*/
        HashMap<String, Integer> holder = new HashMap<>();

        /*get total days from the Milliseconds with TimeUnit Object */
        int days = (int) TimeUnit.MILLISECONDS.toDays(millis);
        /*minus the days from the total seconds*/
        millis -= TimeUnit.DAYS.toMillis(days);
        /*get total hours from the Milliseconds with TimeUnit Object */
        int hours = (int) TimeUnit.MILLISECONDS.toHours(millis);
        /*minus the hours from the total seconds*/
        millis -= TimeUnit.HOURS.toMillis(hours);
        /*get total minutes from the Milliseconds with TimeUnit Object */
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(millis);
        /*minus the minutes from the total seconds*/
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        /*get total seconds from the Milliseconds with TimeUnit Object */
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(millis);


        /*put the days, hours, minutes and seconds to the HashMap */
        holder.put("day", days);
        holder.put("hour", hours);
        holder.put("min", minutes);
        holder.put("sec", seconds);

        /*return the hashMap*/
        return holder;
    }

    public static void playWrongMusic(Activity activity) {
        MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.music_wrong);
        mediaPlayer.start();

    }

    public static void playRightMusing(Activity activity) {
        MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.song_correct);
        mediaPlayer.start();

    }


    @SuppressLint("SimpleDateFormat")
    public static String getFormattedDate(String date) {
        String[] section = date.split(" ");
        return section[0];
    }


    public static String convertUnCapitalized(String str) {

        // Create a char array of given String
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {

                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {

                    // Convert into Upper-case
                    ch[i] = (char) (ch[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (ch[i] >= 'A' && ch[i] <= 'Z')

                // Convert into Lower-Case
                ch[i] = (char) (ch[i] + 'a' - 'A');
        }

        // Convert the char array to equivalent String
        String st = new String(ch);
        return st;
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


    public static void vibratePhone(int time, Activity activity) {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
        if (v != null)
            v.vibrate(time);
    }

    public static boolean isInternetAvaiable(Activity activity) {
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

    public static ProgressDialog showDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("Loading...");
        dialog.show();
        return dialog;
    }


    public static void dissmisDialog(ProgressDialog dialog) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
