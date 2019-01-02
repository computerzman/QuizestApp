package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.quizest.quizestapp.ActivityPackage.QuizActivity;
import com.quizest.quizestapp.DialogPackage.Congratsdialog;
import com.quizest.quizestapp.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class QuizFragment extends Fragment {

    Button btnSkip;
    ImageView ivAnswerA;
    int x;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        if (getActivity() != null && isAdded()) {
            x = ((QuizActivity) getActivity()).quizViewPager.getCurrentItem() + 1;
        }
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null && isAdded())
                    ((QuizActivity) getActivity()).quizViewPager.setCurrentItem(x++, true);
            }
        });

        ivAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Congratsdialog congratsdialog = new Congratsdialog();
                congratsdialog.show(getChildFragmentManager(), "");
            }
        });
    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            ivAnswerA = view.findViewById(R.id.iv_answer_a);
            btnSkip = view.findViewById(R.id.btn_skip);
        }
    }

}
