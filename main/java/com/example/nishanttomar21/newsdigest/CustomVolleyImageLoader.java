package com.example.nishanttomar21.newsdigest;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class CustomVolleyImageLoader {
    private static CustomVolleyImageLoader mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private CustomVolleyImageLoader(Context context) {
        mRequestQueue = getNewRequestQueue(context);

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(100);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static CustomVolleyImageLoader getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CustomVolleyImageLoader(context);
        }
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    @NonNull
    private RequestQueue getNewRequestQueue(Context context) {
        return Volley.newRequestQueue(context);
    }

    public RequestQueue getVolleyRequestQueue() {
        return mRequestQueue;
    }
}
