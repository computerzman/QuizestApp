package com.quizest.quizestapp.NetworkPackage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("registration")
    Call<String> doRegistration(@Field("name")String name, @Field("phone")String phone, @Field("email") String email, @Field("password")String password, @Field("password_confirmation")String confirmPassword);


    @FormUrlEncoded
    @POST("login")
    Call<String> doLogin(@Field("email")String email, @Field("password")String password);


    @GET("category")
    Call<String> getCategoryList(@Header("Authorization")String token, @Header("Accept")String type);


    @GET
    Call<String> getQuizList(@Url String url, @Header("Authorization")String token);


    @FormUrlEncoded
    @POST
    Call<String> submitAnswer(@Url String url, @Header("Authorization")String token, @Field("answer")int answer);


}
