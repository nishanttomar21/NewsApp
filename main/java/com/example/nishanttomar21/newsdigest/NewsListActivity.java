package com.example.nishanttomar21.newsdigest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;

public class NewsListActivity extends SingleFragmentActivity {
    private final static String TAG = "NewsApp";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_news_list);
    }

    @Override
    public Fragment createFragment() {
        return new NewsListFragment();
    }
}
