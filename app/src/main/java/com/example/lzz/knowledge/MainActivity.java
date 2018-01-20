package com.example.lzz.knowledge;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private MainFragment mainFragment;
    private ZhihuDailyFragment zhihuDailyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setCheckedItem(R.id.nav_home);
        navView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null){
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
            zhihuDailyFragment = (ZhihuDailyFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ZhihuDailyFragment");
        } else {
            mainFragment = MainFragment.newInstance();
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
        }


        if (!zhihuDailyFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, zhihuDailyFragment, "ZhihuDailyFragment")
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }

        if (zhihuDailyFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "ZhihuDailyFragment", zhihuDailyFragment);
        }
    }
}
