package com.example.nishanttomar21.newsdigest;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListFragment extends Fragment {
    private static final String TAG = "NewsApp";
    private static NewsAdapter mAdapter;
    @BindView(R.id.news_recycler_view)
    RecyclerView mNewsRecyclerView;
    ProgressDialog progressDialog;

    public static void notifyRecycler() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        ButterKnife.bind(this, view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        startDialog();
        
        JSONRequest jsonRequest = new JSONRequest() {
            @Override
            public void onRequestReceived(boolean success) {
                endDialog();
                updateUI();
            }
        };

        jsonRequest.sendRequest(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void startDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void endDialog() {
        progressDialog.dismiss();
    }

    public void updateUI() {
        if (mAdapter == null) {
            mAdapter = new NewsAdapter(new ArrayList<News>());
            mNewsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setList(NewsList.getInstance(getActivity().getApplicationContext()).getNews());
            mAdapter.notifyDataSetChanged();
        }

    }

    private ImageLoader getImageLoader(String url) {
        ImageLoader imageLoader = CustomVolleyImageLoader.getInstance(getActivity().getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
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

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.TitleView)
        TextView mTitle;
        @BindView(R.id.AuthorView)
        TextView mAuthor;
        @BindView(R.id.SourceView)
        TextView mSource;
        @BindView(R.id.ThumbnailView)
        NetworkImageView mThumbNail;

        private ImageLoader mImageLoader;
        private News mNews;

        public NewsHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindNews(News news) {
            mNews = news;
            mImageLoader = getImageLoader(mNews.getImageUrl());

            mAuthor.setText("    By - " + mNews.getAuthor());
            mTitle.setText(mNews.getTitle());
            mThumbNail.setImageUrl(mNews.getImageUrl(), mImageLoader);
            mSource.setText(mNews.getSource());
        }

        @Override
        public void onClick(View v) {
            Intent intent = NewsPagerActivity.newIntent(getActivity(), mNews.getId());
            startActivity(intent);
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private List<News> mNews;

        public NewsAdapter(List<News> news) {
            mNews = news;
        }

        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_news, parent, false);

            return new NewsHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            News news = mNews.get(position);
            holder.bindNews(news);
        }

        @Override
        public int getItemCount() {
            return mNews.size();
        }

        public void setList(List<News> news) {
            mNews = news;
        }
    }
}
