package com.adel.androidbro.irannews.data;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;



public interface EnglishApiService {
    @GET("p5gwn")
    Flowable<List<News>> getNews();

    @GET("el9en")
    Single<List<Banner>> getBanners();

    @GET("tdr8j")
    Single<List<News>> getVideoNews();
}
