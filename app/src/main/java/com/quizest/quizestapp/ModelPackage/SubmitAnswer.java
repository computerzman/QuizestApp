package com.quizest.quizestapp.ModelPackage;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SubmitAnswer implements Serializable {

    @SerializedName("right_answer")
    private RightAnswer rightAnswer;

    @SerializedName("success")
    private boolean success;

    @SerializedName("score")
    private String score;

    @SerializedName("message")
    private String message;

    public RightAnswer getRightAnswer() {
        return rightAnswer;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getScore() {
        return score;
    }

    public class RightAnswer implements Serializable {

        @SerializedName("option_title")
        private String optionTitle;

        @SerializedName("option_id")
        private int optionId;

        public String getOptionTitle() {
            return optionTitle;
        }

        public int getOptionId() {
            return optionId;
        }
    }
}