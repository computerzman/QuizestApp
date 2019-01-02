package com.quizest.quizestapp.AdapterPackage;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizest.quizestapp.ActivityPackage.AuthActivity;
import com.quizest.quizestapp.ActivityPackage.QuizActivity;
import com.quizest.quizestapp.ActivityPackage.SplashActivity;
import com.quizest.quizestapp.ModelPackage.CategoryModel;
import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryHolder> {

    Activity activity;
    private List<CategoryModel> categoryModelList;


    public CategoryRecyclerAdapter(List<CategoryModel> categoryModelList, Activity activity) {
        this.categoryModelList = categoryModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_recycler_row, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.categoryName.setText(categoryModelList.get(position).getName());
        holder.categoryImageView.setImageResource(categoryModelList.get(position).getImage());
        Log.e("MKGRADIENT", String.valueOf(Util.generateRandom(Util.LAST_GRADIENT)));
        holder.categoryBackgroundImage.setImageResource(Util.getRandomCategoryGradient(Util.generateRandom(Util.LAST_GRADIENT)));
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }


    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView categoryName;
        ImageView categoryImageView, categoryBackgroundImage;

        public CategoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            categoryImageView = itemView.findViewById(R.id.iv_category_img);
            categoryName = itemView.findViewById(R.id.tv_category_name);
            categoryBackgroundImage = itemView.findViewById(R.id.iv_category_bg);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activity, QuizActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            activity.finish();
        }
    }
}
