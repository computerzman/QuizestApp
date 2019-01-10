package com.quizest.quizestapp.AdapterPackage;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class QuizViewPagerAdapter extends FragmentStatePagerAdapter {

    /*all global field instances*/
    private List<Fragment> quizes = new ArrayList<>();
    private Activity activity;

    /*constractor of viewpager*/
    public QuizViewPagerAdapter(FragmentManager fm, List<Fragment> quizes, Activity activity) {
        super(fm);
        this.quizes = quizes;
        this.activity = activity;
    }

    /*add the view pager*/
    public void addQuizes(Fragment fragment) {
        quizes.add(fragment);
    }

    /*get the fragment with position*/
    @Override
    public Fragment getItem(int position) {
        return quizes.get(position);

    }

    /*total view pager count*/
    @Override
    public int getCount() {
        return quizes.size();
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
