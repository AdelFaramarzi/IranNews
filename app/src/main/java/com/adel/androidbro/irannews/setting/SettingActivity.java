package com.adel.androidbro.irannews.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.adel.androidbro.irannews.R;
import com.adel.androidbro.irannews.base.BaseActivity;
import com.adel.androidbro.irannews.data.NewsRepository;

public class SettingActivity extends BaseActivity {
    private boolean isLocaleChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setupViews();
    }

    private void setupViews() {
        final TextView restartMessageTextView = findViewById(R.id.tv_setting_restartMessage);
        RadioGroup radioGroup = findViewById(R.id.radioGroup_setting);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_setting_english:
                        SettingSharedPrefManager.getInstance(SettingActivity.this).saveDefaultLanguage("en");
                        break;
                    case R.id.radio_setting_farsi:
                        SettingSharedPrefManager.getInstance(SettingActivity.this).saveDefaultLanguage("fa");
                        break;
                }

                if (restartMessageTextView.getAlpha() == 0) {
                    restartMessageTextView.animate().alpha(1).setDuration(250).start();
                    isLocaleChanged = true;
                }
            }
        });

        View backButton = findViewById(R.id.iv_setting_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isLocaleChanged) {
            NewsRepository newsRepository = new NewsRepository(this);
            newsRepository.clearCache();
        }
    }

    @Override
    public void setProgressIndicator(boolean shouldShow) {

    }
}
