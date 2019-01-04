package com.adel.androidbro.irannews.list;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsDataSource;

import org.reactivestreams.Subscription;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter implements NewsListContract.Presenter {
    private NewsListContract.View view;
    private NewsDataSource newsDataSource;
    private Subscription subscription;

    public NewsListPresenter(NewsDataSource newsDataSource) {
        this.newsDataSource = newsDataSource;
    }

    @Override
    public void loadNews() {
        view.setProgressIndicator(true);
        newsDataSource.getNews().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<News>>() {
                    @Override
                    public void accept(List<News> news) {
                        view.showNewsList(news);
                        view.setProgressIndicator(false);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        view.setProgressIndicator(false);
                        view.showError(view.context().getString(R.string.all_unknownError));
                    }
                })
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) {
                        NewsListPresenter.this.subscription = subscription;
                    }
                })
                .subscribe();
    }

    @Override
    public void attachView(NewsListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (subscription != null)
            subscription.cancel();
    }
}
