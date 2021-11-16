package com.example.guardiannews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {
    public static final String LOG_TAG = ArticleAdapter.class.getName();

    private final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
    private final SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMM d, yyyy - HH:mm");

    public ArticleAdapter(@NonNull Context context, @NonNull List<Article> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article currentArticle = getItem(position);

        View currentView = convertView;
        if (currentView == null) {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Setting title
        TextView titleView = currentView.findViewById(R.id.title);
        titleView.setText(currentArticle.getTitle());

        // Setting section name
        TextView sectionView = currentView.findViewById(R.id.section);
        sectionView.setText(currentArticle.getSectionName());

        // Setting date
        String[] dateAndTime = currentArticle.getDateAndTime().split("T");

        TextView dateView = currentView.findViewById(R.id.date);
        Date date = null;
        try {
            date = inputDateFormat.parse(dateAndTime[0] + dateAndTime[1].substring(0, dateAndTime[1].length() - 1));
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        dateView.setText(outputDateFormat.format(date) + " UTC");

        return currentView;
    }
}
