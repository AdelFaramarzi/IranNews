package com.adel.androidbro.irannews.search;


import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsDataSource;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private NewsDataSource newsDataSource;
    private Disposable disposable;

    public SearchPresenter(NewsDataSource newsDataSource) {
        this.newsDataSource = newsDataSource;
    }

    @Override
    public void search(String keyword) {
        newsDataSource.search(keyword).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(List<News> news) {
                        if (news.isEmpty())
                            view.showNoResultMessage();
                        else
                            view.showResults(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(view.context().getString(R.string.all_unknownError));
                    }
                });
    }

    @Override
    public void attachView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        if (disposable!=null && !disposable.isDisposed())
            disposable.dispose();
    }
}
