package com.adel.androidbro.irannews.home;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.data.News;
import com.adel.androidbro.irannews.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> newsList = new ArrayList<>();
    private int pendingNewsPosition;

    public NewsAdapter() {

    }

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindNews(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsImageView;
        private TextView titleTextView;
        private TextView dateTextView;
        private View videoIndicator;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.iv_news_image);
            videoIndicator = itemView.findViewById(R.id.iv_news_videoIndicator);
            titleTextView = itemView.findViewById(R.id.tv_news_title);
            dateTextView = itemView.findViewById(R.id.tv_news_date);
        }

        public void bindNews(final News news) {
            Picasso.get().load(news.getImage()).into(newsImageView);
            videoIndicator.setVisibility(news.isVideoNews() ? View.VISIBLE : View.GONE);
            if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                titleTextView.setGravity(Gravity.RIGHT);
            } else {
                titleTextView.setGravity(Gravity.LEFT);
            }
            titleTextView.setText(news.getTitle());
            dateTextView.setText(news.getDate());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pendingNewsPosition = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext(), NewsDetailActivity.class);
                    intent.putExtra(NewsDetailActivity.EXTRA_KEY_NEWS, news);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsBookmarked(News news) {
        newsList.set(pendingNewsPosition, news);
        notifyItemChanged(pendingNewsPosition);
    }
}
