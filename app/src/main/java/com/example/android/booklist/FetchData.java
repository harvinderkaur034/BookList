package com.example.android.booklist;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FetchData {
    private FetchData() {
    }

    private static final String LOG_TAG = FetchData.class.getSimpleName();

    public static List<Books> fetchData(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }
        List<Books> book = extraData(jsonResponse);
        return book;
    }

    private static URL createUrl(String url) {
        URL finl_url = null;
        try {
            finl_url = new URL(url);
        } catch (MalformedURLException e) {

        }
        return finl_url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = getStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response - " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem in retrieving data ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String getStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String reads = reader.readLine();
            while (reads != null) {
                builder.append(reads);
                reads = reader.readLine();
            }
        }
        return builder.toString();
    }

    private static List<Books> extraData(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        List<Books> list_book = new ArrayList<>();
        try {
            JSONObject Resp = new JSONObject(data);
            JSONArray array = Resp.getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {
                JSONObject feature;
                feature = array.optJSONObject(i);
                JSONObject information = feature.optJSONObject("volumeInfo");
                String book_name = information.optString("title");
                String book_author = information.optString("authors");
                String book_category = information.optString("categories");
                list_book.add(new Books(book_category, book_author, book_name));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "problem in data fetching ", e);
        }
        return list_book;
    }
}
