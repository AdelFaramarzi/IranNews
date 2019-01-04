package com.adel.androidbro.irannews.bookmark;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.base.BaseFragment;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsRepositoryProvider;
import com.adel.androidbro.irannews.home.NewsAdapter;

import java.util.List;

public class BookmarkFragment extends BaseFragment implements BookmarkContract.View {
    private BookmarkContract.Presenter presenter;
    private View emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BookmarkPresenter(NewsRepositoryProvider.provideNewsDataSource(getContext()));
    }

    @Override
    public void setupViews() {
        emptyView = rootView.findViewById(R.id.frame_bookmark_emptyState);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_bookmarks;
    }

    @Override
    public void showBookmarkedNewsList(List<News> newsList) {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_bookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NewsAdapter(newsList));
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
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
