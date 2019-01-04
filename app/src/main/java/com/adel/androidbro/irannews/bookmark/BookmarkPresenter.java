package com.adel.androidbro.irannews.bookmark;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsDataSource;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BookmarkPresenter implements BookmarkContract.Presenter {

    private BookmarkContract.View view;
    private NewsDataSource newsDataSource;
    private Disposable disposable;

    public BookmarkPresenter(NewsDataSource newsDataSource) {
        this.newsDataSource = newsDataSource;
    }

    @Override
    public void loadBookmarkedNewsList() {
        view.setProgressIndicator(true);
        newsDataSource.getBookmarkedNews().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(List<News> news) {
                        if (news.isEmpty()) {
                            view.showEmptyView();
                        } else {
                            view.showBookmarkedNewsList(news);
                            view.hideEmptyView();
                        }
                        view.setProgressIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(view.context().getResources().getString(R.string.all_unknownError));
                        view.setProgressIndicator(false);
                    }
                });
    }

    @Override
    public void attachView(BookmarkContract.View view) {
        this.view = view;
        loadBookmarkedNewsList();
    }

    @Override
    public void detachView() {
        this.view = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
