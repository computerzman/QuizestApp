package com.quizest.quizestapp.NetworkPackage;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("registration")
    Call<String> doRegistration(@Field("name") String name, @Field("phone") String phone, @Field("email") String email, @Field("password") String password, @Field("password_confirmation") String confirmPassword);


    @FormUrlEncoded
    @POST("login")
    Call<String> doLogin(@Field("email") String email, @Field("password") String password);


    @GET("category")
    Call<String> getCategoryList(@Header("Authorization") String token, @Header("Accept") String type);


    @GET
    Call<String> getQuizList(@Url String url, @Header("Authorization") String token);


    @FormUrlEncoded
    @POST
    Call<String> submitAnswer(@Url String url, @Header("Authorization") String token, @Field("answer") int answer);


    @GET("leader-board")
    Call<String> getLeaderboardList(@Header("Authorization") String token);

    @GET("profile")
    Call<String> getProfileData(@Header("Authorization") String token);


    @GET("user-setting")
    Call<String> getUserSetting(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("save-user-setting")
    Call<String> postUserLang(@Header("Authorization") String token, @Field("language") String lang);


    @Multipart
    @POST("update-profile")
    Call<String> updateProfile(@Header("Authorization") String token
            , @Part("name") RequestBody name, @Part("country") RequestBody country, @Part("phone") RequestBody phone, @Part MultipartBody.Part image
    );

    @GET("set-user-device-id")
    Call<String> addUserToFirebase(@Header("Authorization")String token, @Query("user_id")int userId, @Query("device_id") String id);
}
