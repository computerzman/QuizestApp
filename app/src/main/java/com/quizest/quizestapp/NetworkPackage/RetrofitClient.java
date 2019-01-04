package com.quizest.quizestapp.NetworkPackage;

import android.app.Activity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {


    private static final String BASE_URL = "http://local.quiz.com/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new NetworkCheckInterceptor()).build();

        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

}
