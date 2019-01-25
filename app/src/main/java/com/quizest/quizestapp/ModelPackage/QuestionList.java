package com.quizest.quizestapp.ModelPackage;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class QuestionList implements Serializable {

	@SerializedName("totalPoint")
	private int totalPoint;

	@SerializedName("data")
	private List<Object> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("hints_coin")
	private int hintsCoin;

	@SerializedName("totalCoin")
	private int totalCoin;

	@SerializedName("user_available_point")
	private String user_available_point;

	@SerializedName("user_available_coin")
	private int user_available_coin;

	@SerializedName("availableQuestionList")
	private List<AvailableQuestionListItem> availableQuestionList;

	@SerializedName("message")
	private String message;

	@SerializedName("totalQuestion")
	private int totalQuestion;

    public String getUser_available_point() {
        return user_available_point;
    }

    public int getUser_available_coin() {
        return user_available_coin;
    }

    public int getTotalPoint(){
		return totalPoint;
	}

	public List<Object> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public int getHintsCoin(){
		return hintsCoin;
	}

	public int getTotalCoin(){
		return totalCoin;
	}

	public List<AvailableQuestionListItem> getAvailableQuestionList(){
		return availableQuestionList;
	}

	public String getMessage(){
		return message;
	}

	public int getTotalQuestion(){
		return totalQuestion;
	}

	public class AvailableQuestionListItem implements Serializable{

		@SerializedName("image")
		private String image;

		@SerializedName("skip_coin")
		private int skipCoin;

		@SerializedName("time_limit")
		private int timeLimit;

		@SerializedName("hints")
		private String hints;

		@SerializedName("title")
		private String title;

		@SerializedName("type")
		private int type;

		@SerializedName("question_id")
		private String questionId;

		@SerializedName("point")
		private int point;

		@SerializedName("category_id")
		private int categoryId;

		@SerializedName("options")
		private List<OptionsItem> options;

		@SerializedName("id")
		private int id;

		@SerializedName("category")
		private String category;

		@SerializedName("coin")
		private int coin;

		@SerializedName("status")
		private int status;

		public String getImage(){
			return image;
		}

		public int getSkipCoin(){
			return skipCoin;
		}

		public int getTimeLimit(){
			return timeLimit;
		}

		public String getHints(){
			return hints;
		}

		public String getTitle(){
			return title;
		}

		public int getType(){
			return type;
		}

		public String getQuestionId(){
			return questionId;
		}

		public int getPoint(){
			return point;
		}

		public int getCategoryId(){
			return categoryId;
		}

		public List<OptionsItem> getOptions(){
			return options;
		}

		public int getId(){
			return id;
		}

		public String getCategory(){
			return category;
		}

		public int getCoin(){
			return coin;
		}

		public int getStatus(){
			return status;
		}
	}

	public class OptionsItem implements Serializable{

		@SerializedName("option_title")
		private String optionTitle;

		@SerializedName("id")
		private int id;

		public String getOptionTitle(){
			return optionTitle;
		}

		public int getId(){
			return id;
		}
	}
}