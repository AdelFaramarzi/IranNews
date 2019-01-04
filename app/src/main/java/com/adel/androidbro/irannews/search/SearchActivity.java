package com.adel.androidbro.irannews.search;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.base.BaseActivity;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsRepositoryProvider;
import com.adel.androidbro.irannews.home.NewsAdapter;
import java.util.List;

public class SearchActivity extends BaseActivity implements TextWatcher,
        SearchContract.View {
    private EditText searchEditTextView;
    private ImageView clearButton;
    private ImageView backButton;
    private RecyclerView searchResultRecyclerView;
    private NewsAdapter newsAdapter;
    private View noResultMessage;
    private CoordinatorLayout root;
    private SearchContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        presenter = new SearchPresenter(NewsRepositoryProvider.provideNewsDataSource(this));
        presenter.attachView(this);
        setupViews();
    }

    private void setupViews() {
        searchResultRecyclerView = findViewById(R.id.rv_search);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        newsAdapter = new NewsAdapter();
        searchResultRecyclerView.setAdapter(newsAdapter);

        searchEditTextView = findViewById(R.id.et_search);
        searchEditTextView.addTextChangedListener(this);

        root = findViewById(R.id.coordinator_search);

        noResultMessage = findViewById(R.id.tv_search_noResultMessage);

        backButton = findViewById(R.id.iv_search_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clearButton=findViewById(R.id.iv_search_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditTextView.setText("");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            presenter.search(s.toString());
            clearButton.setVisibility(View.VISIBLE);
        } else {
            noResultMessage.setVisibility(View.INVISIBLE);
            searchResultRecyclerView.setVisibility(View.GONE);
            clearButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showResults(List<News> newsList) {
        noResultMessage.setVisibility(View.INVISIBLE);
        searchResultRecyclerView.setVisibility(View.VISIBLE);
        newsAdapter.setNewsList(newsList);
    }

    @Override
    public void showNoResultMessage() {
        searchResultRecyclerView.setVisibility(View.INVISIBLE);
        noResultMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressIndicator(boolean shouldShow) {

    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(root, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return this;
    }
}
