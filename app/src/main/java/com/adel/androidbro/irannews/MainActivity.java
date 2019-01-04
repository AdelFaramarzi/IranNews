package com.adel.androidbro.irannews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.adel.androidbro.irannews.base.BaseActivity;
import com.adel.androidbro.irannews.bookmark.BookmarkFragment;
import com.adel.androidbro.irannews.categories.CategoriesFragment;
import com.adel.androidbro.irannews.home.HomeFragment;
import com.adel.androidbro.irannews.videos.VideoNewsListFragment;
import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

import java.util.Stack;

public class MainActivity extends BaseActivity {
    private View progressBar;
    private BottomNavigation bottomNavigation;
    private Fragment homeFragment;
    private Fragment categoryListFragment;
    private Fragment bookmarkFragment;
    private Fragment videoNewsListFragment;
    private Stack<Integer> horizontalStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        progressBar = findViewById(R.id.frame_main_progressBarContainer);

        bottomNavigation = findViewById(R.id.bottomNavigation_main);
        //bottomNavigation.setTypeface(ResourcesCompat.getFont(this, R.font.iran_yekan));
        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int i) {
                progressBar.setVisibility(View.GONE);
                horizontalStack.add(i);
                switch (i) {
                    case R.id.tab_home:
                        if (homeFragment == null)
                            homeFragment = new HomeFragment();
                        replaceTransaction(homeFragment);
                        break;
                    case R.id.tab_category:
                        if (categoryListFragment == null)
                            categoryListFragment = new CategoriesFragment();
                        replaceTransaction(categoryListFragment);
                        break;
                    case R.id.tab_bookmarks:
                        if (bookmarkFragment == null)
                            bookmarkFragment = new BookmarkFragment();
                        replaceTransaction(bookmarkFragment);
                        break;
                    case R.id.tab_videos:
                        if (videoNewsListFragment == null)
                            videoNewsListFragment = new VideoNewsListFragment();
                        replaceTransaction(videoNewsListFragment);
                        break;
                }
            }
        });
    }

    private void replaceTransaction(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main_fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void setProgressIndicator(boolean shouldShow) {
        progressBar.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (horizontalStack.size() > 1) {
            horizontalStack.pop();
            progressBar.setVisibility(View.GONE);
            switch (horizontalStack.peek()) {
                case R.id.tab_home:
                    replaceTransaction(homeFragment);
                    bottomNavigation.setSelectedItemWithoutNotifyListener(0);
                    break;
                case R.id.tab_category:
                    replaceTransaction(categoryListFragment);
                    bottomNavigation.setSelectedItemWithoutNotifyListener(1);
                    break;
                case R.id.tab_bookmarks:
                    replaceTransaction(bookmarkFragment);
                    bottomNavigation.setSelectedItemWithoutNotifyListener(2);

                    break;
                case R.id.tab_videos:
                    replaceTransaction(videoNewsListFragment);
                    bottomNavigation.setSelectedItemWithoutNotifyListener(3);
                    break;
            }
        } else {
            finish();
        }
    }
}