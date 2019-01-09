package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quizest.quizestapp.ActivityPackage.SettingActivity;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.ModelPackage.ProfileSection;
import com.quizest.quizestapp.NetworkPackage.ErrorHandler;
import com.quizest.quizestapp.NetworkPackage.RetrofitClient;
import com.quizest.quizestapp.NetworkPackage.RetrofitInterface;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.GlideApp;
import com.quizest.quizestapp.UtilPackge.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class EditProfileFragment extends Fragment {

    CircleImageView profileImage;
    TextView tvRanking, tvPoint, tvName, tvEmail;
    ImageButton btn_setting_edit;
    EditText edtUserName;
    EditText CountryName, Phone;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        btn_setting_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                if(getActivity() != null)
                    getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                getActivity().finish();
            }
        });

        getProfileData();
    }

    private void initViews(){
            View view = getView();
            if(view != null){
                profileImage = view.findViewById(R.id.img_edit_profile);
                edtUserName = view.findViewById(R.id.edt_username_edit);
                CountryName = view.findViewById(R.id.edt_country_edit);
                Phone = view.findViewById(R.id.edt_phone_edit);
                tvEmail = view.findViewById(R.id.tv_email_edit);
                tvName = view.findViewById(R.id.tv_name_edit);
                tvPoint = view.findViewById(R.id.tv_total_point_edit);
                tvRanking = view.findViewById(R.id.tv_rank_edit);
                btn_setting_edit = view.findViewById(R.id.btn_setting_edit);
            }
    }

    private void getProfileData() {
        final ProgressDialog dialog = Util.showDialog(getActivity());
        Storage storage = new Storage(getActivity());
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        final Call<String> profileCall = retrofitInterface.getProfileData(storage.getAccessToken());
        profileCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                /*handle error globally */
                ErrorHandler.getInstance().handleError(response.code(), getActivity(), dialog);
                if (response.isSuccessful()) {
                    /*success true*/
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean isSuccess = jsonObject.getBoolean("success");
                        if (isSuccess) {
                            /*serialize the String response  */
                            Gson gson = new Gson();
                            ProfileSection profileSection = gson.fromJson(response.body(), ProfileSection.class);

                            /*simple data binding for profile section*/
                            tvEmail.setText(profileSection.getData().getUser().getEmail());
                            tvName.setText(profileSection.getData().getUser().getName());
                            tvPoint.setText(profileSection.getData().getUser().getPoints());
                            edtUserName.setText(profileSection.getData().getUser().getName());
                            if(profileSection.getData().getUser().getCountry() != null){
                                CountryName.setText(profileSection.getData().getUser().getCountry());
                            }
                            Phone.setText(profileSection.getData().getUser().getPhone());
                            tvRanking.setText(String.valueOf(profileSection.getData().getUser().getRanking()));
                            if (getActivity() != null)
                                GlideApp.with(getActivity()).load(profileSection.getData().getUser().getPhoto()).into(profileImage);
                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);

                        } else {
                            /*dismiss the dialog*/
                            Util.dissmisDialog(dialog);
                            /*get all the error messages and show to the user*/
                            String message = jsonObject.getString("message");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    /*dismiss the dialog*/
                    Util.dissmisDialog(dialog);
                    Toast.makeText(getActivity(), R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*dismiss the dialog*/
                Util.dissmisDialog(dialog);
                /*handle network error and notify the user*/
                if (t instanceof SocketTimeoutException || t instanceof IOException) {
                    if (getActivity() != null && isAdded())
                        Toast.makeText(getActivity(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
