package com.adel.androidbro.irannews;


 import android.app.Application;
import android.content.Context;

 import com.adel.androidbro.irannews.setting.LocaleManager;


public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.updateResources(base, LocaleManager.getLanguage(base)));
    }
}
