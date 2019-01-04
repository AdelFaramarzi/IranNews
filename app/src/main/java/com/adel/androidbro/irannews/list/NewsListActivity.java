package com.adel.androidbro.irannews.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.base.BaseActivity;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsRepositoryProvider;
import com.adel.androidbro.irannews.home.NewsAdapter;
import com.adel.androidbro.irannews.search.SearchActivity;


import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends BaseActivity implements NewsListContract.View {
    public static final String EXTRA_KEY_NEWS = "news";
    public static final String EXTRA_KEY_VIEW_TITLE = "title";
    private ArrayList<News> newsList;
    private NewsListContract.Presenter presenter;
    private CoordinatorLayout root;
    private ProgressBar progressBar;
    private String toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewsListPresenter(NewsRepositoryProvider.provideNewsDataSource(this));
        if (getIntent().getExtras() != null) {
            newsList = getIntent().getExtras().getParcelableArrayList(EXTRA_KEY_NEWS);
            toolbarTitle = getIntent().getExtras().getString(EXTRA_KEY_VIEW_TITLE);
        }

        setContentView(R.layout.activity_news_list);
        presenter.attachView(this);

        setupViews();

    }

    private void setupViews() {
        progressBar = findViewById(R.id.progressBar_newsList);

        setupToolbar();
        root = findViewById(R.id.coordinato_newsList);
        if (newsList == null) {
            presenter.loadNews();
        } else {
            showNewsList(newsList);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void setupToolbar() {
        View backButton = findViewById(R.id.iv_list_back);
        View searchButton = findViewById(R.id.iv_list_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsListActivity.this, SearchActivity.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (toolbarTitle != null) {
            TextView titleTextView = findViewById(R.id.tv_newsList_title);
            titleTextView.setText(toolbarTitle);
        }
    }

    @Override
    public void setProgressIndicator(boolean shouldShow) {
        progressBar.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(root, R.string.all_unknownError, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showNewsList(List<News> newsList) {
        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NewsAdapter(newsList));
    }
}
