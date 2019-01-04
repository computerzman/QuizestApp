package com.quizest.quizestapp.NetworkPackage;

import android.app.Activity;

import com.quizest.quizestapp.R;
import com.quizest.quizestapp.UtilPackge.Util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkCheckInterceptor implements Interceptor {

    Activity activity;

    public NetworkCheckInterceptor(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!Util.isInternetAvaiable(activity)) {
            return new Response.Builder()
                    .code(1007)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_2)
                    .body(ResponseBody.create(MediaType.parse("{}"),"{}"))
                    .message(activity.getString(R.string.warning_no_internet))
                    .build();
        }
        return chain.proceed(chain.request());
    }
}
