package com.adel.androidbro.irannews.list;

import com.adel.androidbro.irannews.base.BasePresenter;
import com.adel.androidbro.irannews.base.BaseView;
import com.adel.androidbro.irannews.data.News;

import java.util.List;

public interface NewsListContract {
    interface View extends BaseView {
        void showNewsList(List<News> newsList);
    }

    interface Presenter extends BasePresenter<View> {
        void loadNews();
    }
}
