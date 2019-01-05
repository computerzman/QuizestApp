package com.quizest.quizestapp.AdapterPackage;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizest.quizestapp.ModelPackage.QuestionList;
import com.quizest.quizestapp.R;

import java.util.List;

public class QuizOptionsRecyclerRow extends RecyclerView.Adapter<QuizOptionsRecyclerRow.QuizOptionsHolder> {

    private List<QuestionList.OptionsItem> optionsItemList;
    private Activity activity;

    public QuizOptionsRecyclerRow(List<QuestionList.OptionsItem> optionsItemList, Activity activity) {
        this.optionsItemList = optionsItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public QuizOptionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_options_recycler_row, parent, false);
        return new QuizOptionsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizOptionsHolder holder, int position) {
        holder.quizOptionName.setText(optionsItemList.get(position).getOptionTitle());
    }

    @Override
    public int getItemCount() {
        return optionsItemList.size();
    }

    class QuizOptionsHolder extends RecyclerView.ViewHolder{
        ImageView quizOptionBg;
        TextView quizOptionName;
        public QuizOptionsHolder(View itemView) {
            super(itemView);
            quizOptionBg = itemView.findViewById(R.id.img_quiz_option);
            quizOptionName = itemView.findViewById(R.id.tv_quiz_option_name);
        }
    }
}
