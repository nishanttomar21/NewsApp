package com.example.nishanttomar21.newsdigest;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

public abstract class JSONRequest {
    public static final String JSON_ARRAY = "articles";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE_URL = "urlToImage";
    public static final String KEY_AUTHOR = "author";
    private final static String TAG = "NewsApp";
    String URL = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=3eef9af06d964786bbf2fc8a1c049668";
    private JSONArray object = null;


    public abstract void onRequestReceived(boolean success);

    public void sendRequest(final Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response, context);
                onRequestReceived(true);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, String.valueOf(error));
            }
        });

        stringRequest.setShouldCache(false);
        CustomVolleyImageLoader.getInstance(context).getVolleyRequestQueue().add(stringRequest);
    }

    private void showJSON(String response, Context context) {
        JSONParser jp = new JSONParser(response);
        jp.startParsing(context);
    }
}
