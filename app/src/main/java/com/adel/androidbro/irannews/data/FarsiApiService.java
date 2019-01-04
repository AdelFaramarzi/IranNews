package com.adel.androidbro.irannews.data;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;


public interface FarsiApiService {
    @GET("x4scj")
    Flowable<List<News>> getNews();

    @GET("14vf7n")
    Single<List<Banner>> getBanners();

    @GET("1359k3")
    Single<List<News>> getVideoNews();
}
