package com.example.android.booklist;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Books>> {
    private String mURL;

    public BookLoader(Context context, String URL) {
        super(context);
        mURL = URL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        List<Books> books = FetchData.fetchData(mURL);
        return books;
    }
}
