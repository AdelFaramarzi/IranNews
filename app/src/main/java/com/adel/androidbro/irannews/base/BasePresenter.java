package com.adel.androidbro.irannews.base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}