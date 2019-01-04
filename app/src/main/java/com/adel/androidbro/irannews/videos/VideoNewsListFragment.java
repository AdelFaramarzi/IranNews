package com.adel.androidbro.irannews.videos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.base.BaseFragment;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsRepository;
import com.adel.androidbro.irannews.home.NewsAdapter;


import java.util.List;



public class VideoNewsListFragment extends BaseFragment implements VideoNewsContract.View {
    private VideoNewsContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new VideoNewsListPresenter(new NewsRepository(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void setupViews() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_video_news_list;
    }

    @Override
    public void showVideoNewsList(List<News> newsList) {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_videos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NewsAdapter(newsList));
    }

    @Override
    public void setProgressIndicator(boolean shouldShow) {
        getBaseActivity().setProgressIndicator(shouldShow);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(rootView, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getContext();
    }
}
