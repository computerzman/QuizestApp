package com.quizest.quizestapp.AdapterPackage;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.GlideException;
import com.quizest.quizestapp.ModelPackage.LeaderBoard;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.GlideApp;

import java.io.FileNotFoundException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.LeaderBoardHolder> {

//    global field instances
    private List<LeaderBoard.LeaderListItem> leaderBoardList;
    private Activity activity;

    /*this is the constructor for the leaderboard */
    public LeaderboardRecyclerAdapter(List<LeaderBoard.LeaderListItem> leaderBoardList, Activity activity) {
        this.leaderBoardList = leaderBoardList;
        this.activity = activity;
    }


    /*this is the function where every row layout inflate for the recycler view */
    @NonNull
    @Override
    public LeaderBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_leaderboard_recycler_row, parent, false);
        return new LeaderBoardHolder(view);
    }


    /*this is the place for data binding for recycler view */
    @Override
    public void onBindViewHolder(@NonNull LeaderBoardHolder holder, int position) {


        GlideApp.with(activity).load(leaderBoardList.get(position).getPhoto()).placeholder(R.drawable.avater).into(holder.imgPersonImage);

        holder.tvPersonName.setText(leaderBoardList.get(position).getName());
        holder.tvPersonRank.setText(String.valueOf(leaderBoardList.get(position).getRanking()));
        holder.tvPersonRank.setTextColor(activity.getResources().getColor(R.color.color_blue));
        holder.tvPersonPoing.setText(leaderBoardList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return leaderBoardList.size();
    }

    /*this is the custom view holder class of leader board*/
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
