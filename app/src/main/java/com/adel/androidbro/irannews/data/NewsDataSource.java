package com.adel.androidbro.irannews.data;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface NewsDataSource {
    Flowable<List<News>> getNews();

    Single<List<Banner>> getBanner();

    Single<List<News>> getVideoNews();

    void bookmark(News news);

    Single<List<News>> getBookmarkedNews();

    Single<List<News>> search(String keyword);
}
