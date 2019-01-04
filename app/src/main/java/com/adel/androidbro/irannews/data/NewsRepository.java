package com.adel.androidbro.irannews.data;

import android.content.Context;
import android.util.Log;

import com.adel.androidbro.irannews.setting.SettingSharedPrefManager;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class NewsRepository implements NewsDataSource {
    private CloudDataSource cloudDataSource;
    private LocalDataSource localDataSource;

    public NewsRepository(Context context) {
        localDataSource = AppDatabase.getInstance(context).getLocalDataSource();
        if (SettingSharedPrefManager.getInstance(context).getDefaultLanguage().equalsIgnoreCase("fa")) {
            cloudDataSource = new FarsiCloudDataSource();
        } else {
            cloudDataSource = new EnglishCloudDataSource();
        }
    }

    @Override
    public Flowable<List<News>> getNews() {
        cloudDataSource.getNews().subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new FlowableSubscriber<List<News>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<News> newsList) {
                        localDataSource.saveNewsList(newsList);

                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("NewsRepo", "onError: " + t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return localDataSource.getNews();
    }

    @Override
    public Single<List<Banner>> getBanner() {
        return cloudDataSource.getBanner();
    }

    @Override
    public Single<List<News>> getVideoNews() {
        return cloudDataSource.getVideoNews();
    }

    @Override
    public void bookmark(News news) {
        localDataSource.bookmark(news);
    }

    @Override
    public Single<List<News>> getBookmarkedNews() {
        return localDataSource.getBookmarkedNews();
    }

    @Override
    public Single<List<News>> search(String keyword) {
        return localDataSource.search(keyword);
    }

    public void clearCache() {
        localDataSource.removeAllRows();
    }
}
