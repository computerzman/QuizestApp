package com.quizest.quizestapp.ModelPackage;

import com.google.gson.annotations.SerializedName;


public class UserLogIn{

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("key")
	private String key;

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public String getKey(){
		return key;
	}


	public class Data{

		@SerializedName("access_token")
		private String accessToken;

		@SerializedName("access_type")
		private String accessType;

		public String getAccessToken(){
			return accessToken;
		}

		public String getAccessType(){
			return accessType;
		}
	}
}