package com.example.lzz.knowledge.home;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.bookmarks.BookmarksFragment;
import com.example.lzz.knowledge.bookmarks.BookmarksPresenter;
import com.example.lzz.knowledge.home.MainFragment;
import com.example.lzz.knowledge.home.MeizhiFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private MainFragment mainFragment;
    private MeizhiFragment meizhiFragment;
    private BookmarksFragment bookmarksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setCheckedItem(R.id.nav_home);
        navView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null){
            mainFragment = (MainFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "MainFragment");
//            meizhiFragment = (MeizhiFragment)getSupportFragmentManager()
//                    .getFragment(savedInstanceState, "MeizhiFragment");
            bookmarksFragment = (BookmarksFragment)getSupportFragmentManager()
                    .getFragment(savedInstanceState,"BookmarksFragment");
        } else {
            mainFragment = MainFragment.newInstance();
//            meizhiFragment = MeizhiFragment.newInstance();
            bookmarksFragment = BookmarksFragment.newInstance();
        }

        if (!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
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

        new BookmarksPresenter(MainActivity.this, bookmarksFragment);

        showMainFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        if (item.getItemId() == R.id.nav_home){
            showMainFragment();
        } else if (item.getItemId() == R.id.nav_bookmarks){
            showBookmarksFragment();
        }
        return true;
    }

    private void showMainFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!bookmarksFragment.isHidden()){
            fragmentTransaction.hide(bookmarksFragment);
        }
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

//    private void showMeizhiFragment(){
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        if (!mainFragment.isHidden()){
//            fragmentTransaction.hide(mainFragment);
//        }
//        if (!bookmarksFragment.isHidden()){
//            fragmentTransaction.hide(bookmarksFragment);
//        }
//        fragmentTransaction.show(meizhiFragment);
//        fragmentTransaction.commit();
//
//        toolbar.setTitle(getResources().getString(R.string.nav_image));
//    }

    private void showBookmarksFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!mainFragment.isHidden()){
            fragmentTransaction.hide(mainFragment);
        }
        fragmentTransaction.show(bookmarksFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(getResources().getString(R.string.nav_bookmarks));
        if (bookmarksFragment.isAdded()){
            bookmarksFragment.notifyDataChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
//        if (meizhiFragment.isAdded()){
//            getSupportFragmentManager().putFragment(outState, "MeizhiFragment", meizhiFragment);
//        }
        if (bookmarksFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"BookmarksFragment", bookmarksFragment);
        }
    }
}
