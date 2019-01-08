package com.quizest.quizestapp.AdapterPackage;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quizest.quizestapp.ActivityPackage.MainActivity;
import com.quizest.quizestapp.LocalStorage.Storage;
import com.quizest.quizestapp.ModelPackage.QuestionList;
import com.quizest.quizestapp.ModelPackage.SubmitAnswer;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizOptionsRecyclerRow extends RecyclerView.Adapter<QuizOptionsRecyclerRow.QuizOptionsHolder> {

    private List<QuestionList.OptionsItem> optionsItemList;
    private Activity activity;
    private String questionID;
    private int point;
    private int Rightid = 0;
    ImageView imageView;
    private boolean isAnswered = false;

    public QuizOptionsRecyclerRow(List<QuestionList.OptionsItem> optionsItemList, Activity activity, String questionID, int point, ImageView imageView) {
        this.optionsItemList = optionsItemList;
        this.point = point;
        this.activity = activity;
        this.questionID = questionID;
        this.imageView = imageView;
    }

    @NonNull
    @Override
    public QuizOptionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_options_recycler_row, parent, false);
        return new QuizOptionsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuizOptionsHolder holder, final int position) {
        if (Rightid != 0) {
            if (optionsItemList.get(position).getId() == Rightid) {
                holder.quizOptionName.setTextColor(activity.getResources().getColor(R.color.color_white));
                holder.quizOptionBg.setImageResource(R.drawable.quiz_option_bg_right);
                Rightid = 0;
            }
        } else {
            holder.quizOptionBg.setEnabled(true);
        }
        holder.quizOptionName.setText(Util.convertUnCapitalized(optionsItemList.get(position).getOptionTitle()));
        holder.quizOptionBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAnswered) {
                    submitAnswer(questionID, holder.getAdapterPosition(), holder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionsItemList.size();
    }

    class QuizOptionsHolder extends RecyclerView.ViewHolder {
        ImageView quizOptionBg;
        TextView quizOptionName;

        public QuizOptionsHolder(View itemView) {
            super(itemView);
            quizOptionBg = itemView.findViewById(R.id.img_quiz_option);
            quizOptionName = itemView.findViewById(R.id.tv_quiz_option_name);
        }
    }


    public void submitAnswer(String optionID, int position, final QuizOptionsHolder holder) {
        Storage storage = new Storage(activity);
        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofit().create(RetrofitInterface.class);
        final Call<String> submitAnserCall = retrofitInterface.submitAnswer(RetrofitClient.SUBMIT_ANSWER_ULR + optionID, storage.getAccessToken(), optionsItemList.get(position).getId());
        submitAnserCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                /*handle error globally */
                ErrorHandler.getInstance().handleError(response.code(), activity, null);
                if (response.isSuccessful()) {
                    /*success true*/
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        boolean isSuccess = jsonObject.getBoolean("success");
                        if (!isSuccess) {
                            imageView.setImageResource(R.drawable.ic_cat_worng);
                            Util.playWrongMusic(activity);
                            Util.vibratePhone(900, activity);
                            /*serialize the String response  */
                            holder.quizOptionName.setTextColor(activity.getResources().getColor(R.color.color_white));
                            holder.quizOptionBg.setImageResource(R.drawable.quiz_option_wrong);
                            Gson gson = new Gson();
                            SubmitAnswer submitAnswer = gson.fromJson(response.body(), SubmitAnswer.class);
                            isAnswered = true;
                            Rightid = submitAnswer.getRightAnswer().getOptionId();
                            notifyDataSetChanged();
                        } else {
                            imageView.setImageResource(R.drawable.ic_cat);
                            Util.TOTAL_POINT = Util.TOTAL_POINT + point;
                            Util.playRightMusing(activity);
                            isAnswered = true;
                            holder.quizOptionName.setTextColor(activity.getResources().getColor(R.color.color_white));
                            holder.quizOptionBg.setImageResource(R.drawable.quiz_option_bg_right);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*handle network error and notify the user*/
                if (t instanceof SocketTimeoutException || t instanceof IOException) {
                    Toast.makeText(activity, R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
