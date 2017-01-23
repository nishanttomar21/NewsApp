package com.example.nishanttomar21.newsdigest;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONParser {
    public static final String JSON_ARRAY = "articles";
    public static final String KEY_SOURCE = "source";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMAGE_URL = "urlToImage";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    private final static String TAG = "NewsApp";
    private static JSONArray object = null;
    private String Response;
    private String Source;

    public JSONParser(String response) {
        this.Response = response;
    }

    public void startParsing(Context context) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(Response);
            Source = jsonObject.getString(KEY_SOURCE);

            object = jsonObject.getJSONArray(JSON_ARRAY);

            for (int i = 0; i < object.length(); i++) {
                News news = new News();
                JSONObject jo = object.getJSONObject(i);

                news.setSource(jsonObject.getString(KEY_SOURCE));
                news.setImageUrl(jo.getString(KEY_IMAGE_URL));
                news.setTitle(jo.getString(KEY_TITLE));
                news.setAuthor(jo.getString(KEY_AUTHOR));
                news.setUrl(jo.getString(KEY_URL));
                news.setDescription(jo.getString(KEY_DESCRIPTION));

                NewsList.getInstance(context).addNews(news);
            }
        } catch (JSONException exception) {
            Log.d(TAG, String.valueOf(exception));
        }
    }

}
