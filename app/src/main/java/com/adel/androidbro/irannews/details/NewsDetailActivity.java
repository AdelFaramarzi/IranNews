package com.adel.androidbro.irannews.details;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.base.BaseActivity;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.data.NewsDataSource;
import com.adel.androidbro.irannews.data.NewsRepositoryProvider;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class NewsDetailActivity extends BaseActivity {
    public static final String EXTRA_KEY_NEWS = "news";
    private static final int VIDEO_WIDTH = 254;
    private static final int VIDEO_HEIGHT = 400;
    private News news;
    private JZVideoPlayerStandard videoPlayer;
    private NewsDataSource newsDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        newsDataSource = NewsRepositoryProvider.provideNewsDataSource(this);

        news = getIntent().getParcelableExtra(EXTRA_KEY_NEWS);
        setupViews();
    }

    private void setupViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setExpandedTitleTextColor(ContextCompat.getColorStateList(this, android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColorStateList(this, android.R.color.white));
        //Typeface typeface = ResourcesCompat.getFont(this, R.font.iran_yekan);
        //collapsingToolbarLayout.setCollapsedTitleTypeface(typeface);
        //collapsingToolbarLayout.setExpandedTitleTypeface(typeface);
        collapsingToolbarLayout.setTitle(news.getTitle());

        if (news.isVideoNews()) {
            final FrameLayout frameLayout = findViewById(R.id.frame_details_videoContainer);
            frameLayout.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
                    layoutParams.height = frameLayout.getWidth() * 254 / 400;
                    frameLayout.setLayoutParams(layoutParams);
                }
            });
            frameLayout.setVisibility(View.VISIBLE);
            videoPlayer = findViewById(R.id.videoPlayer_details);
            videoPlayer.setUp(news.getVideo(), JZVideoPlayer.SCREEN_WINDOW_NORMAL);
            Picasso.get().load(news.getImage()).into(videoPlayer.thumbImageView);
            videoPlayer.fullscreenButton.setVisibility(View.GONE);
        } else {
            ImageView imageView = findViewById(R.id.iv_details_newsImage);
            Picasso.get().load(news.getImage()).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

        TextView newsTitleTextView = findViewById(R.id.tv_details_title);
        newsTitleTextView.setText(news.getTitle());

        TextView newsContentTextView = findViewById(R.id.tv_details_content);
        newsContentTextView.setText(news.getContent());

        TextView dateTextView = findViewById(R.id.tv_details_date);
        dateTextView.setText(news.getDate());

        final ImageView bookmarkImageView = findViewById(R.id.iv_details_bookmark);
        bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.setBookmarked(!news.isBookmarked());
                newsDataSource.bookmark(news);
                EventBus.getDefault().post(news);
                bookmarkImageView.setImageResource(news.isBookmarked() ? R.drawable.bookmark_check : R.drawable.bookmark_outline);
            }
        });

        bookmarkImageView.setImageResource(news.isBookmarked() ? R.drawable.bookmark_check : R.drawable.bookmark_outline);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void setProgressIndicator(boolean shouldShow) {

    }
}
