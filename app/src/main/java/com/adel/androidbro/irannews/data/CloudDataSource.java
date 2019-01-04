package com.adel.androidbro.irannews.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class CloudDataSource implements NewsDataSource {
    protected Retrofit retrofit;

    public CloudDataSource() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.myjson.com/bins/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
