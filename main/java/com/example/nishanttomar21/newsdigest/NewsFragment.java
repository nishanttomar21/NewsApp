package com.example.nishanttomar21.newsdigest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsFragment extends Fragment {
    private static final String TAG = "NewsApp";
    private static final String ARG_NEWS_ID = "news_id";
    @BindView(R.id.image1)
    NetworkImageView thumbnail;
    @BindView(R.id.text1)
    TextView title;
    @BindView(R.id.text2)
    TextView description;
    @BindView(R.id.text3)
    TextView article;
    private News news;
    private ImageLoader mImageLoader;

    public static NewsFragment newInstantiate(UUID newsId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS_ID, newsId);

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID newsId = (UUID) getArguments().getSerializable(ARG_NEWS_ID);
        news = NewsList.getInstance(getActivity()).findNews(newsId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);

        ButterKnife.bind(this, view);

        mImageLoader = getImageLoader(news.getImageUrl());
        thumbnail.setImageUrl(news.getImageUrl(), mImageLoader);
        title.setText(news.getTitle());
        description.setText(news.getDescription());

        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getUrl()));
                startActivity(intent);
            }
        });

        return view;

    }

    private ImageLoader getImageLoader(String URL) {
        ImageLoader imageLoader = CustomVolleyImageLoader.getInstance(getActivity()).getImageLoader();

        imageLoader.get(URL, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                response.getBitmap();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, String.valueOf(error));
            }
        });

        return imageLoader;
    }

}
