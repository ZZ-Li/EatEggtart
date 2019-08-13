package com.example.lzz.knowledge.ui.settings;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lzz.knowledge.R;

public class SettingActivity extends AppCompatActivity {

    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null){
            FragmentManager manager = getSupportFragmentManager();
            settingFragment = (SettingFragment)manager.getFragment(savedInstanceState,"settingFragment");
        }else {
            settingFragment = SettingFragment.newInstance();

        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, settingFragment,"settingFragment")
                .commit();
        new SettingPresenter(SettingActivity.this, settingFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getSupportFragmentManager();
        if (settingFragment.isAdded()){
            manager.putFragment(outState,"settingFragment", settingFragment);
        }
    }
}
