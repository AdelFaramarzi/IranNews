package com.adel.androidbro.irannews.data;

import android.content.Context;

public class NewsRepositoryProvider {

    public static NewsDataSource provideNewsDataSource(Context context) {
        return new NewsRepository(context);
    }
}
