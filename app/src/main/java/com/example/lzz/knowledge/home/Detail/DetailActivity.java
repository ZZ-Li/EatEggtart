package com.example.lzz.knowledge.home.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lzz.knowledge.R;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState != null){
            detailFragment = (DetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "detailFragment");
        } else {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .commit();
        }

        Intent intent = getIntent();
        DetailPresenter presenter = new DetailPresenter(DetailActivity.this, detailFragment);
        presenter.setId(intent.getIntExtra("id", 0));
        presenter.setTitle(intent.getStringExtra("title"));

    }

}
