package com.adel.androidbro.irannews.home;

import com.adel.androidbro.irannews.base.BasePresenter;
import com.adel.androidbro.irannews.base.BaseView;
import com.adel.androidbro.irannews.data.Banner;
import com.adel.androidbro.irannews.data.News;
import java.util.List;


public interface HomeContract {
    interface View extends BaseView {
        void showNews(List<News> newsList);

        void showBanners(List<Banner> banners);
    }

    interface Presenter extends BasePresenter<View> {
        void getNewsList();

        void getBanners();
    }
}
