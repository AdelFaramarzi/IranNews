package com.adel.androidbro.irannews.videos;

import com.adel.androidbro.irannews.base.BasePresenter;
import com.adel.androidbro.irannews.base.BaseView;
import com.adel.androidbro.irannews.data.News;


import java.util.List;


public interface VideoNewsContract {

    interface View extends BaseView {
        void showVideoNewsList(List<News> newsList);
    }

    interface Presenter extends BasePresenter<View> {
        void loadVideoNewsList();
    }
}
