package com.example.lzz.knowledge.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.lzz.knowledge.R;

/**
 * Created by ASUS on 2018/3/7.
 */

public class AboutFragment extends PreferenceFragmentCompat {

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.about_preference_fragment);

        findPreference("github").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.github_url)));
                startActivity(intent);
                return false;
            }
        });
    }
}
