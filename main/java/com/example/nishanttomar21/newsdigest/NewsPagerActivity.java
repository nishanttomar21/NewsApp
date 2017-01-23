package com.example.nishanttomar21.newsdigest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsPagerActivity extends AppCompatActivity {
    private static final String Extra_NEWS_ID = "extra_news_id";
    private static final String TAG = "NewsApp";
    @BindView(R.id.view_pager_activity)
    ViewPager mViewPager;
    private List<News> mNews;

    public static Intent newIntent(Context packageContext, UUID newsId) {
        Intent intent = new Intent(packageContext, NewsPagerActivity.class);
        intent.putExtra(Extra_NEWS_ID, newsId);

        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view_pager);

        mNews = NewsList.getInstance(this).getNews();
        UUID newsId = (UUID) getIntent().getSerializableExtra(Extra_NEWS_ID);

        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                News news = mNews.get(position);
                return NewsFragment.newInstantiate(news.getId());
            }

            @Override
            public int getCount() {
                return mNews.size();
            }
        });

        for (int i = 0; i < mNews.size(); i++) {
            if (mNews.get(i).getId().equals(newsId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
