package com.example.guardiannews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>> {
    private static final String link = "https://content.guardianapis.com/search?api-key=test";

    private int pageCounter = 1;
    List<Article> articles;
    private ArticleAdapter adapter;
    ArticleLoader loader;
    private final AdapterView.OnItemClickListener itemClickListener =
            (parent, view, position, id) -> openWebPage(articles.get(position).getUrl());
    View footer;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = findViewById(R.id.list);
        emptyTextView = findViewById(R.id.empty_view);

        articles = new ArrayList<>();
        adapter = new ArticleAdapter(this, articles);

        footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.page_footer, null, false);

        Button bForward = footer.findViewById(R.id.button_forward);
        bForward.setOnClickListener((view) -> pageForward());

        newsListView.addFooterView(footer);
        newsListView.setEmptyView(emptyTextView);
        newsListView.setAdapter(adapter);
        newsListView.setOnItemClickListener(itemClickListener);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            loader = (ArticleLoader) getLoaderManager().initLoader(0, null, this);
        } else {
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            emptyTextView.setText(getString(R.string.no_internet));
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(this, link);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> earthquakes) {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        adapter.addAll(earthquakes);
        emptyTextView.setText(getString(R.string.no_news));
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        adapter.clear();
    }

    public void pageForward() {
        pageCounter++;

        loader.setUrl(buildUrl());
        loader.forceLoad();
    }
    
    public String buildUrl() {
        Uri baseUri = Uri.parse(link);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("page", String.valueOf(pageCounter));
        return uriBuilder.toString();
    }
}