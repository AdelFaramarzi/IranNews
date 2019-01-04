package com.adel.androidbro.irannews.home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.data.Banner;
import com.squareup.picasso.Picasso;


public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private List<Banner> banners = new ArrayList<>();

    public BannerAdapter(List<Banner> banners) {
        this.banners = banners;
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
        holder.bindBanner(banners.get(position));
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView bannerImageView;
        private TextView sourceTextView;
        private TextView titleTextView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            bannerImageView = itemView.findViewById(R.id.iv_banner_image);
            sourceTextView = itemView.findViewById(R.id.tv_banner_source);
            titleTextView = itemView.findViewById(R.id.tv_banner_title);
        }

        public void bindBanner(Banner banner) {
            Picasso.get().load(banner.getImage()).into(bannerImageView);
            sourceTextView.setText(banner.getSource());
            titleTextView.setText(banner.getTitle());
        }
    }
}
