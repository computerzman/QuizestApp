package com.quizest.quizestapp.AdapterPackage;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quizest.quizestapp.ModelPackage.LeaderBoard;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.LeaderBoardHolder> {

    private List<LeaderBoard> leaderBoardList;
    private Activity activity;

    public LeaderboardRecyclerAdapter(List<LeaderBoard> leaderBoardList, Activity activity) {
        this.leaderBoardList = leaderBoardList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public LeaderBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_leaderboard_recycler_row, parent, false);
        return new LeaderBoardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardHolder holder, int position) {

        holder.imgPersonImage.setImageResource(leaderBoardList.get(position).getImage());
        holder.tvPersonName.setText(leaderBoardList.get(position).getName());
        holder.tvPersonRank.setText(String.valueOf(leaderBoardList.get(position).getRank()));
        holder.tvPersonRank.setTextColor(activity.getResources().getColor(R.color.color_blue));
        holder.tvPersonPoing.setText(String.valueOf(leaderBoardList.get(position).getPoints()));

    }

    @Override
    public int getItemCount() {
        return leaderBoardList.size();
    }

    class LeaderBoardHolder extends RecyclerView.ViewHolder {
        CircleImageView imgPersonImage;
        TextView tvPersonName, tvPersonPoing, tvPersonRank;

        public LeaderBoardHolder(View itemView) {
            super(itemView);
            imgPersonImage = itemView.findViewById(R.id.img_leaderboard_person);
            tvPersonName = itemView.findViewById(R.id.tv_leaderboard_name);
            tvPersonPoing = itemView.findViewById(R.id.tv_leaderboard_point);
            tvPersonRank = itemView.findViewById(R.id.tv_leaderboard_rank);
        }
    }
}
