package com.example.lzz.knowledge;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lzz.knowledge.home.MainFragment;
import com.example.lzz.knowledge.home.MeizhiFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private MainFragment mainFragment;
    private MeizhiFragment meizhiFragment;

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
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "MainFragment");
            meizhiFragment = (MeizhiFragment)getSupportFragmentManager().getFragment(savedInstanceState, "MeizhiFrament");
        } else {
            mainFragment = MainFragment.newInstance();
            meizhiFragment = MeizhiFragment.newInstance();
        }

        if (!mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment, mainFragment, "MainFragment")
                    .commit();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawers();
        if (item.getItemId() == R.id.nav_home){
            showMainFragment();
        } else if (item.getItemId() == R.id.nav_image){
            showMeizhiFragment();
        }
        return true;
    }

    private void showMainFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(meizhiFragment);
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.commit();
        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void showMeizhiFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!meizhiFragment.isAdded()){
            fragmentTransaction
                    .add(R.id.layout_fragment, meizhiFragment, "MeizhiFragment")
                    .hide(mainFragment)
                    .show(meizhiFragment)
                    .commit();
        } else {
            fragmentTransaction
                    .hide(mainFragment)
                    .show(meizhiFragment)
                    .commit();
        }
        toolbar.setTitle(getResources().getString(R.string.nav_image));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "MainFragment", mainFragment);
        }
        if (meizhiFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, "MeizhiFragment", meizhiFragment);
        }
    }
}
