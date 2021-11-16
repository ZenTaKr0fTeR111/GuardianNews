package com.example.guardiannews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {
    private String url;

    public ArticleLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<Article> loadInBackground() {
        return QueryUtils.fetchNews(url);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
