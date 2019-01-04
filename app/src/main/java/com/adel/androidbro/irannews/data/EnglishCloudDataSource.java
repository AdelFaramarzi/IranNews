package com.adel.androidbro.irannews.data;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


public class EnglishCloudDataSource extends CloudDataSource {
    private EnglishApiService englishApiService;

    public EnglishCloudDataSource() {
        super();
        englishApiService = retrofit.create(EnglishApiService.class);
    }

    @Override
    public Flowable<List<News>> getNews() {
        return englishApiService.getNews();
    }

    @Override
    public Single<List<Banner>> getBanner() {
        return englishApiService.getBanners();
    }

    @Override
    public Single<List<News>> getVideoNews() {
        return englishApiService.getVideoNews();
    }

    @Override
    public void bookmark(News news) {

    }

    @Override
    public Single<List<News>> getBookmarkedNews() {
        return null;
    }

    @Override
    public Single<List<News>> search(String keyword) {
        return null;
    }
}
