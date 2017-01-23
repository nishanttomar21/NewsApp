package com.example.nishanttomar21.newsdigest;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewsList {
    private static final String TAG = "NewsApp";
    private static NewsList mNewsList;
    private ArrayList<News> mNews;

    private NewsList(Context context) {
        mNews = new ArrayList<>();
    }

    public static NewsList getInstance(Context context) {
        if(mNewsList == null)
            mNewsList = new NewsList(context);

        return mNewsList;
    }

    public List<News> getNews() {
        return mNews;
    }

    public void addNews(News news) {
        mNews.add(news);
    }

    public News findNews(UUID newsId) {
        for (News news : mNews) {
            if (news.getId().equals(newsId)) {
                return news;
            }
        }

        return null;
    }

}
