package com.sample.home24.network.services;

import com.sample.home24.beans.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleServices {
    /*?appDomain=2&locale=de_DE&limit=100*/
    @GET("categories/100/articles")
    Call<ArticleResponse> getArticles(@Query("appDomain") String appDomain,
                                      @Query("locale") String locale, @Query("limit") int limit);
}
