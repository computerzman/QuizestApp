package com.quizest.quizestapp.NetworkPackage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("registration")
    Call<String> doRegistration(@Field("name")String name, @Field("phone")String phone, @Field("email") String email, @Field("password")String password, @Field("password_confirmation")String confirmPassword);


    @FormUrlEncoded
    @POST("login")
    Call<String> doLogin(@Field("email")String email, @Field("password")String password);

}
