package com.quizest.quizestapp.FragmentPackage.AuthFragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quizest.quizestapp.ActivityPackage.AuthActivity;
import com.quizest.quizestapp.NetworkPackage.ErrorHandler;
import com.quizest.quizestapp.NetworkPackage.RetrofitClient;
import com.quizest.quizestapp.NetworkPackage.RetrofitInterface;
import com.quizest.quizestapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class RegisterFragment extends Fragment {


    Activity activity;
    TextView tv_RegisterSignIn;

    EditText edtUserName, edtEmail, edtPhone, edtPassword, edtConfirmPassword;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(getActivity() != null && isAdded()){
            activity = getActivity();
        }
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        if(getActivity() != null && isAdded()){
            activity = getActivity();
        }

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
            edtConfirmPassword = view.findViewById(R.id.edt_reg_confim_password);
            edtEmail = view.findViewById(R.id.edt_reg_email);
            edtPhone = view.findViewById(R.id.edt_reg_phone);
            tv_RegisterSignIn = view.findViewById(R.id.tv_register_sign_in);
            edtUserName = view.findViewById(R.id.edt_reg_name);
        }
    }


    private void doRegistration(String name, String email, String phone, String password, String confirmPassword){

        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);

        Call<String> doRegisterCall = retrofitInterface.doRegistration(name, phone, email, password, confirmPassword);

        doRegisterCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                /*globally handle error*/

                ErrorHandler.getInstance().handleError(response.code(), activity);

                if(response.isSuccessful()){

                    /*success true*/




                }else{

                    Toast.makeText(activity, "Data Not Found!", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }



}
