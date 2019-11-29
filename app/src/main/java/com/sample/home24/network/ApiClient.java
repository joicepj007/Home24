package com.sample.home24.network;

import com.sample.home24.network.services.ArticleServices;
import com.sample.home24.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static OkHttpClient.Builder builder;

    private static Retrofit getRetrofit() {
        builder = getHttpClient();
        return new Retrofit.Builder().baseUrl(AppConstants.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    }

    private static OkHttpClient.Builder getHttpClient() {
        if (builder == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(loggingInterceptor);
            client.writeTimeout(60000, TimeUnit.MILLISECONDS);
            client.readTimeout(60000, TimeUnit.MILLISECONDS);
            client.connectTimeout(60000, TimeUnit.MILLISECONDS);
            return client;
        }
        return builder;
    }

    public static ArticleServices getArticleService(){
        return getRetrofit().create(ArticleServices.class);
    }
}
