package com.example.lzz.knowledge.ui.settings;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;
import android.widget.Toast;

import com.example.lzz.knowledge.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 2018/3/6.
 */

public class SettingFragment extends PreferenceFragmentCompat implements SettingContract.View{

    private SettingContract.Presenter  presenter;

    private Preference timePreference;

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.setting_preference_fragment);

        findPreference("no_picture_mode").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.setNoPictureMode(preference);
                return false;
            }
        });

        findPreference("clear_image_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.cleanImageCache();
                return false;
            }
        });

        timePreference = findPreference("time_of_saving_articles");
        timePreference.setSummary(presenter.getTimeSummary());
        timePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                presenter.setTimeOfSavingArticles(preference, (String)newValue);
                timePreference.setSummary(presenter.getTimeSummary());
                return true;
            }
        });

    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {

        this.presenter = presenter;

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showCleanImageCacheDone() {
        Toast.makeText(getContext(), R.string.clear_image_cache_successfully, Toast.LENGTH_SHORT).show();
    }
}
