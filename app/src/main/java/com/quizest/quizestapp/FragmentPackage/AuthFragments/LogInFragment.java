package com.quizest.quizestapp.FragmentPackage.AuthFragments;


import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quizest.quizestapp.ActivityPackage.AuthActivity;
import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.ModelPackage.UserLogin;
import com.quizest.quizestapp.NetworkPackage.ErrorHandler;
import com.quizest.quizestapp.NetworkPackage.RetrofitClient;
import com.quizest.quizestapp.NetworkPackage.RetrofitInterface;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;
import com.quizest.quizestapp.UtilPackge.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LogInFragment extends Fragment {

    /*global field instances*/
    EditText edtEmail, edtPassword;
    TextView tvLogInChangePassword, tvLogInSignUp;
    Button btnSignIn;
    Activity activity;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getActivity() != null && isAdded()) {
            activity = getActivity();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (getActivity() != null && isAdded()) {
            activity = getActivity();
        }

        /*view type casting*/
        initViews();

        /*sign up button click*/
        tvLogInSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ((AuthActivity) Objects.requireNonNull(getActivity())).fragmentTransition(new RegisterFragment());
                }
            }
        });


        /*change password button click*/
        tvLogInChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ((AuthActivity) Objects.requireNonNull(getActivity())).fragmentTransition(new ForgotPasswordFragment());
                }
            }
        });


//        action for the sign in button click
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Util.isInternetAvaiable(activity)) {

                    if (Validator.validateInputField(new EditText[]{edtEmail, edtPassword}, activity)) {

                        doLogIn(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());

                    }

                } else {

                    Toast.makeText(activity, R.string.no_internet, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    /*log in api call*/
    private void doLogIn(String email, String password) {
        final Storage storage = new Storage(activity);
        final ProgressDialog dialog = Util.showDialog(activity);
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        Call<String> doLoginCall = retrofitInterface.doLogin(email, password);
        doLoginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                /*handle error globally */
                ErrorHandler.getInstance().handleError(response.code(), activity, dialog);
                if (response.isSuccessful()) {
                    /*success true*/
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean isSuccess = jsonObject.getBoolean("success");
                        if (isSuccess) {
                            /*serialize the String response  */
                            Gson gson = new Gson();
                            UserLogin userLogIn = gson.fromJson(response.body(), UserLogin.class);
                            /*save the user data to local storage*/
                            storage.SaveAccessToken(userLogIn.getData().getAccessToken());
                            storage.SaveAccessType(userLogIn.getData().getAccessType());
                            storage.SaveLogInSate(true);
                            storage.saveUserName(userLogIn.getData().getUserInfo().getName());
                            storage.saveUserId(userLogIn.getData().getUserInfo().getId());
                            storage.saveUserTotalCoin(userLogIn.getData().getTotalCoin());
                            storage.saveUserTotalPoint(Integer.parseInt(userLogIn.getData().getTotalPoint()));
                            storage.saveUserAdmobPoint(userLogIn.getData().getAdmobCoin());
                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);
                            /*take the user to the dashboard activity*/
                            Intent intent = new Intent(activity, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                            activity.finish();
                        } else {
                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);
                            /*get all the error messages and show to the user*/
                            String message = jsonObject.getString("message");
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    /*dismiss the dialog*/
                    Util.dissmisDialog(dialog);
                    Toast.makeText(activity, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*dismiss the dialog*/
                Util.dissmisDialog(dialog);
                /*handle network error and notify the user*/
                if (t instanceof SocketTimeoutException || t instanceof IOException) {
                    Toast.makeText(activity, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*view type casting*/
    private void initViews() {
        View view = getView();
        if (view != null) {
            edtEmail = view.findViewById(R.id.edt_login_email);
            edtEmail.setTag("Email");
            edtPassword = view.findViewById(R.id.edt_login_password);
            edtPassword.setTag("Password");
            tvLogInSignUp = view.findViewById(R.id.tv_login_signup);
            btnSignIn = view.findViewById(R.id.btn_sign_in);
            tvLogInChangePassword = view.findViewById(R.id.tv_login_change_password);
        }
    }

}
