package com.quizest.quizestapp.FragmentPackage.AuthFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quizest.quizestapp.ActivityPackage.AuthActivity;
import com.quizest.quizestapp.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class RegisterFragment extends Fragment {

    TextView tv_RegisterSignIn;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        tv_RegisterSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() != null && isAdded())
                ((AuthActivity) getActivity()).fragmentTransition(new LogInFragment());
            }
        });
    }

    private void initViews(){
        View view = getView();
        if(view != null){
            tv_RegisterSignIn = view.findViewById(R.id.tv_register_sign_in);
        }
    }

}
