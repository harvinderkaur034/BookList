package com.example.android.booklist;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {
    public String Book_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public TextView emptyTextView;
    public BookAdapter adapter;
    private static final int bookLoader = 1;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ListView ViewBook = (ListView) findViewById(R.id.list);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        ViewBook.setEmptyView(emptyTextView);
        adapter = new BookAdapter(this, new ArrayList<Books>());
        ViewBook.setAdapter(adapter);


        LoaderManager loader = null;
        Intent intent = getIntent();
        String query = intent.getStringExtra("word");
        url = Book_URL + query + "&maxResults=10";
        ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo network = connect.getActiveNetworkInfo();
        if (network != null && network.isConnected()) {
            loader = getLoaderManager();
            loader.initLoader(bookLoader, null, this);
        } else

        {
            View loading = findViewById(R.id.indicator);
            loading.setVisibility(View.GONE);
            emptyTextView.setText(R.string.Internet_not_found);
        }

    }

    @Override
    public android.content.Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Books>> loader, List<Books> books) {
        View indicator = findViewById(R.id.indicator);
        indicator.setVisibility(View.GONE);
        emptyTextView.setText(R.string.Books_not_available);
        adapter.clear();
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Books>> loader) {
        adapter.clear();
    }
}

