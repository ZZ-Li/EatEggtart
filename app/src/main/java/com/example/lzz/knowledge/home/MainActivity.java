package com.example.lzz.knowledge.home;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lzz.knowledge.R;
import com.example.lzz.knowledge.bookmarks.BookmarksFragment;
import com.example.lzz.knowledge.bookmarks.BookmarksPresenter;
import com.example.lzz.knowledge.home.MainFragment;
import com.example.lzz.knowledge.home.MeizhiFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null){
            FragmentManager manager = getSupportFragmentManager();
            mainFragment = (MainFragment)manager.getFragment(savedInstanceState, "MainFragment");
            bookmarksFragment = (BookmarksFragment)manager.getFragment(savedInstanceState,"BookmarksFragment");
//            meizhiFragment = (MeizhiFragment)manager.getFragment(savedInstanceState, "MeizhiFragment");
        } else {
            mainFragment = MainFragment.newInstance();
            bookmarksFragment = BookmarksFragment.newInstance();
//            meizhiFragment = MeizhiFragment.newInstance();
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
    protected void onStart() {
        super.onStart();
        if (!bookmarksFragment.isHidden()){
            bookmarksFragment.notifyDataChanged();
        }
        Log.d("MainTest","MainActivity.onStart" );
    }

    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if (mainFragment.isHidden()){
            showMainFragment();
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
        if (mainFragment.isAdded()){
            manager.putFragment(outState, "MainFragment", mainFragment);
        }
        if (bookmarksFragment.isAdded()){
            manager.putFragment(outState,"BookmarksFragment", bookmarksFragment);
        }
//        if (meizhiFragment.isAdded()){
//            manager.putFragment(outState, "MeizhiFragment", meizhiFragment);
//        }
    }

}
