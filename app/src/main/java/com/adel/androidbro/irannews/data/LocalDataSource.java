package com.adel.androidbro.irannews.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class LocalDataSource implements NewsDataSource {

    @Query("SELECT * FROM tbl_news")
    @Override
    public abstract Flowable<List<News>> getNews();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void saveNewsList(List<News> newsList);

    @Override
    public Single<List<Banner>> getBanner() {
        return null;
    }

    @Override
    public Single<List<News>> getVideoNews() {
        return null;
    }

    @Update
    @Override
    public abstract void bookmark(News news);

    @Query("SELECT * FROM tbl_news WHERE is_bookmarked LIKE 1")
    @Override
    public abstract Single<List<News>> getBookmarkedNews();

    @Query("SELECT * FROM tbl_news WHERE title LIKE '%' || :keyword || '%'")
    @Override
    public abstract Single<List<News>> search(String keyword);

    @Query("DELETE FROM tbl_news")
    public abstract void removeAllRows();
}
