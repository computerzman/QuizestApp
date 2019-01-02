package com.quizest.quizestapp.FragmentPackage.AuthFragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.quizest.quizestapp.ActivityPackage.AuthActivity;
import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LogInFragment extends Fragment {

    TextView tvLogInChangePassword, tvLogInSignUp;
    Button btnSignIn;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();

        tvLogInSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ((AuthActivity) Objects.requireNonNull(getActivity())).fragmentTransition(new RegisterFragment());
                }
            }
        });

        tvLogInChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ((AuthActivity) Objects.requireNonNull(getActivity())).fragmentTransition(new ForgotPasswordFragment());
                }
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                if (getActivity() != null && isAdded())
                    getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                getActivity().finish();
            }
        });

    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            tvLogInSignUp = view.findViewById(R.id.tv_login_signup);
            btnSignIn = view.findViewById(R.id.btn_sign_in);
            tvLogInChangePassword = view.findViewById(R.id.tv_login_change_password);
        }
    }

}
