package com.adel.androidbro.irannews.search;


import com.adel.androidbro.irannews.base.BasePresenter;
import com.adel.androidbro.irannews.base.BaseView;
import com.adel.androidbro.irannews.data.News;

import java.util.List;


public interface SearchContract {

    interface View extends BaseView {
        void showResults(List<News> newsList);

        void showNoResultMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void search(String keyword);
    }
}
