package com.adel.androidbro.irannews.categories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.list.NewsListActivity;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Category[] categories = new Category[6];
    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
        Category topStories = new Category();
        topStories.setId(0);
        topStories.setTitle(context.getString(R.string.category_topStories));
        topStories.setIcon(R.drawable.newspaper);
        categories[0] = topStories;

        Category world = new Category();
        world.setId(1);
        world.setTitle(context.getString(R.string.category_World));
        world.setIcon(R.drawable.earth);
        categories[1] = world;

        Category business = new Category();
        business.setId(2);
        business.setTitle(context.getString(R.string.category_business));
        business.setIcon(R.drawable.domain);
        categories[2] = business;

        Category iran = new Category();
        iran.setId(3);
        iran.setTitle(context.getString(R.string.category_iran));
        iran.setIcon(R.drawable.flag);
        categories[3] = iran;

        Category health = new Category();
        health.setId(4);
        health.setTitle(context.getString(R.string.category_health));
        health.setIcon(R.drawable.heart_pulse);
        categories[4] = health;

        Category technology = new Category();
        technology.setId(5);
        technology.setTitle(context.getString(R.string.category_technology));
        technology.setIcon(R.drawable.chip);
        categories[5] = technology;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.bindCategory(categories[position]);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconImageView;
        private TextView titleTextView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iv_category_icon);
            titleTextView = itemView.findViewById(R.id.tv_category_title);
        }

        public void bindCategory(final Category category) {
            iconImageView.setImageResource(category.getIcon());
            titleTextView.setText(category.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsListActivity.class);
                    intent.putExtra(NewsListActivity.EXTRA_KEY_VIEW_TITLE, category.getTitle());
                    context.startActivity(intent);
                }
            });
        }
    }
}
