package com.example.lzz.knowledge.ui.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.service.CacheService;
import com.example.lzz.knowledge.ui.about.AboutActivity;
import com.example.lzz.knowledge.ui.bookmarks.BookmarksFragment;
import com.example.lzz.knowledge.ui.bookmarks.BookmarksPresenter;
import com.example.lzz.knowledge.ui.settings.SettingActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    private MainFragment mainFragment;
    private MeizhiFragment meizhiFragment;

    private ZhihuDailyFragment zhihuDailyFragment;
    private BookmarksFragment bookmarksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "MainActivity init");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null){
            FragmentManager manager = getSupportFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState, "ZhihuDailyFragment");
            bookmarksFragment = (BookmarksFragment)manager.getFragment(savedInstanceState,"BookmarksFragment");
            meizhiFragment = (MeizhiFragment)manager.getFragment(savedInstanceState, "MeizhiFragment");
        } else {
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            bookmarksFragment = BookmarksFragment.newInstance();
            meizhiFragment = MeizhiFragment.newInstance();
        }

        if (!zhihuDailyFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, zhihuDailyFragment, "ZhihuDailyFragment")
                    .commit();
        }

//        if (!meizhiFragment.isAdded()){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.layout_fragment, meizhiFragment,"MeizhiFragment")
//                    .commit();
//        }

        if (!bookmarksFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, bookmarksFragment,"BookmarksFragment")
                    .commit();
        }

        new ZhihuDailyPresenter(MainActivity.this, zhihuDailyFragment);
        new BookmarksPresenter(MainActivity.this, bookmarksFragment);

        showZhihuFragment();

        startService(new Intent(this, CacheService.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        if (item.getItemId() == R.id.nav_home){
            showZhihuFragment();

        } else if (item.getItemId() == R.id.nav_bookmarks){
            showBookmarksFragment();

        }
//        else if (item.getItemId() == R.id.nav_image){
//            showMeizhiFragment();
//
//        }
        else if (item.getItemId() == R.id.nav_settings){
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.nav_about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void showZhihuFragment(){
        fab.show();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!bookmarksFragment.isHidden()){
            fragmentTransaction.hide(bookmarksFragment);
        }
//        if (!meizhiFragment.isHidden()){
//            fragmentTransaction.hide(meizhiFragment);
//        }
        fragmentTransaction.show(zhihuDailyFragment);
        fragmentTransaction.commit();


        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void showBookmarksFragment(){
        fab.hide();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (!zhihuDailyFragment.isHidden()){
            fragmentTransaction.hide(zhihuDailyFragment);
        }
//        if (!meizhiFragment.isHidden()){
//            fragmentTransaction.hide(meizhiFragment);
//        }

        fragmentTransaction.show(bookmarksFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.nav_bookmarks));
        if (bookmarksFragment.isAdded()){
            bookmarksFragment.notifyDataChanged();
        }
    }

    private void showMeizhiFragment(){
        fab.hide();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!zhihuDailyFragment.isHidden()){
            fragmentTransaction.hide(zhihuDailyFragment);
        }
        if (!bookmarksFragment.isHidden()){
            fragmentTransaction.hide(bookmarksFragment);
        }
        fragmentTransaction.show(meizhiFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.nav_image));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!bookmarksFragment.isHidden()){
            bookmarksFragment.notifyDataChanged();
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, CacheService.class));
        super.onDestroy();
    }

    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if (zhihuDailyFragment.isHidden()){
            showZhihuFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        } else {
            if(System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getSupportFragmentManager();
        if (zhihuDailyFragment.isAdded()){
            manager.putFragment(outState, "ZhihuDailyFragment", zhihuDailyFragment);
        }
        if (bookmarksFragment.isAdded()){
            manager.putFragment(outState,"BookmarksFragment", bookmarksFragment);
        }
        if (meizhiFragment.isAdded()){
            manager.putFragment(outState, "MeizhiFragment", meizhiFragment);
        }
    }

}
