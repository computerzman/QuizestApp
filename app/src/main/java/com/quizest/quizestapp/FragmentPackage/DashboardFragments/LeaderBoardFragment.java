package com.quizest.quizestapp.FragmentPackage.DashboardFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quizest.quizestapp.AdapterPackage.LeaderboardRecyclerAdapter;
import com.quizest.quizestapp.ModelPackage.LeaderBoard;
import com.quizest.quizestapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LeaderBoardFragment extends Fragment {

    RecyclerView leadboardRecyclerView;
    TextView tvLeaderBoardTabAll, tvLeaderBoardTabFriend;
    LeaderboardRecyclerAdapter leaderboardRecyclerAdapter;
    List<LeaderBoard> leaderBoardList;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();

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

        leaderBoardList = new ArrayList<>();
        leaderBoardList.add(new LeaderBoard("Charles Daikin", 3435, 1, R.drawable.img_person));
        leaderBoardList.add(new LeaderBoard("Jopny Daikin", 3435, 2, R.drawable.img_person));
        leaderBoardList.add(new LeaderBoard("Charles Done", 254, 3, R.drawable.img_person));
        leaderBoardList.add(new LeaderBoard("Jopny Daikin", 424524, 4, R.drawable.img_person));
        leaderBoardList.add(new LeaderBoard("Rahim Daikin", 2543, 5, R.drawable.img_person));
        leaderBoardList.add(new LeaderBoard("Morgal Daikin", 3435, 6, R.drawable.img_person));
        leaderBoardList.add(new LeaderBoard("Charles Daikin", 3435, 7, R.drawable.img_person));


        leaderboardRecyclerAdapter = new LeaderboardRecyclerAdapter(leaderBoardList, getActivity());

        leadboardRecyclerView.setAdapter(leaderboardRecyclerAdapter);

    }

    private void initViews() {
        View view = getView();
        if (view != null) {
            leadboardRecyclerView = view.findViewById(R.id.rv_leaderboard);
            tvLeaderBoardTabAll = view.findViewById(R.id.tv_leaderboard_tab_all);
            tvLeaderBoardTabFriend = view.findViewById(R.id.tv_leaderboard_tab_friend);
        }

    }

}
