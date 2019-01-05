package com.quizest.quizestapp.ModelPackage;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CategoryList {

    @SerializedName("category_list")
    private List<CategoryListItem> categoryList;

    @SerializedName("success")
    private boolean success;

    public List<CategoryListItem> getCategoryList() {
        return categoryList;
    }

    public boolean isSuccess() {
        return success;
    }


    public class CategoryListItem {

        @SerializedName("max_limit")
        private int maxLimit;

        @SerializedName("image")
        private String image;

        @SerializedName("category_id")
        private String categoryId;

        @SerializedName("time_limit")
        private int timeLimit;

        @SerializedName("serial")
        private int serial;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("id")
        private int id;

        @SerializedName("qs_limit")
        private int qsLimit;

        @SerializedName("status")
        private int status;

        @SerializedName("question_amount")
        private int questionAmount;

        public int getMaxLimit() {
            return maxLimit;
        }

        public String getImage() {
            return image;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public int getTimeLimit() {
            return timeLimit;
        }

        public int getSerial() {
            return serial;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }

        public int getQsLimit() {
            return qsLimit;
        }

        public int getStatus() {
            return status;
        }

        public int getQuestionAmount() {
            return questionAmount;
        }
    }
}