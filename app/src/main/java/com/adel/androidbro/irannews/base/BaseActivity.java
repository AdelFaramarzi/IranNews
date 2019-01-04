package com.adel.androidbro.irannews.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.adel.androidbro.irannews.setting.LocaleManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.updateResources(newBase, LocaleManager.getLanguage(newBase)));
    }

    public abstract void setProgressIndicator(boolean shouldShow);
}

