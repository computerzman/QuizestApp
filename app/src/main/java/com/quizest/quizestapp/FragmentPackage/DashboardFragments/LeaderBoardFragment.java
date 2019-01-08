package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.AdapterPackage.LeaderboardRecyclerAdapter;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.ModelPackage.LeaderBoard;
import com.quizest.quizestapp.ModelPackage.UserLogIn;
import com.quizest.quizestapp.NetworkPackage.ErrorHandler;
import com.quizest.quizestapp.NetworkPackage.RetrofitClient;
import com.quizest.quizestapp.NetworkPackage.RetrofitInterface;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LeaderBoardFragment extends Fragment {

    Activity activity;
    RecyclerView leadboardRecyclerView;
    TextView tvLeaderBoardTabAll, tvLeaderBoardTabFriend;
    LeaderboardRecyclerAdapter leaderboardRecyclerAdapter;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getActivity() != null) {

            activity = getActivity();

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        if (getActivity() != null) {
            activity = getActivity();
        }

        tvLeaderBoardTabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvLeaderBoardTabAll.setBackgroundResource(R.drawable.leader_board_tab);
                tvLeaderBoardTabFriend.setBackgroundResource(R.drawable.leaderboard_tab_white);
                tvLeaderBoardTabAll.setTextColor(getResources().getColor(R.color.color_white));
                tvLeaderBoardTabFriend.setTextColor(getResources().getColor(R.color.color_text));
            }
        });

        tvLeaderBoardTabFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvLeaderBoardTabFriend.setBackgroundResource(R.drawable.leader_board_tab);
                tvLeaderBoardTabAll.setBackgroundResource(R.drawable.leaderboard_tab_white);
                tvLeaderBoardTabFriend.setTextColor(getResources().getColor(R.color.color_white));
                tvLeaderBoardTabAll.setTextColor(getResources().getColor(R.color.color_text));
            }
        });


        /*set options to recycler view*/
        leadboardRecyclerView.setHasFixedSize(true);
        leadboardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        getLeaderBoardData();

    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            leadboardRecyclerView = view.findViewById(R.id.rv_leaderboard);
            tvLeaderBoardTabAll = view.findViewById(R.id.tv_leaderboard_tab_all);
            tvLeaderBoardTabFriend = view.findViewById(R.id.tv_leaderboard_tab_friend);
        }

    }

    private void getLeaderBoardData() {
        final ProgressDialog dialog = Util.showDialog(activity);
        Storage storage = new Storage(activity);
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        Call<String> leaderCall = retrofitInterface.getLeaderboardList(storage.getAccessToken());
        leaderCall.enqueue(new Callback<String>() {
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
                            LeaderBoard leaderBoard = gson.fromJson(response.body(), LeaderBoard.class);
                            leaderboardRecyclerAdapter = new LeaderboardRecyclerAdapter(leaderBoard.getLeaderList(), activity);
                            leadboardRecyclerView.setAdapter(leaderboardRecyclerAdapter);

                            Util.dissmisDialog(dialog);

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

}
