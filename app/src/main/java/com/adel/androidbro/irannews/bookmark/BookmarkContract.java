package com.adel.androidbro.irannews.bookmark;


import com.adel.androidbro.irannews.base.BasePresenter;
import com.adel.androidbro.irannews.base.BaseView;
import com.adel.androidbro.irannews.data.News;

import java.util.List;


public interface BookmarkContract {

    interface View extends BaseView {
        void showBookmarkedNewsList(List<News> newsList);

        void showEmptyView();

        void hideEmptyView();
    }

    interface Presenter extends BasePresenter<View> {
        void loadBookmarkedNewsList();
    }
}
