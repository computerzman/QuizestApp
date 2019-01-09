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

    private List<Fragment> quizes = new ArrayList<>();
    private Activity activity;

    public QuizViewPagerAdapter(FragmentManager fm, List<Fragment> quizes, Activity activity) {
        super(fm);
        this.quizes = quizes;
        this.activity = activity;
    }

    public void addQuizes(Fragment fragment) {
        quizes.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return quizes.get(position);

    }

    @Override
    public int getCount() {
        return quizes.size();
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
