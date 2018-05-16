package com.example.android.booklist;

public class Books {

    private String mName;
    private String mAuthor;
    private String mCategory;


    Books(String name, String author, String category) {
        mName = name;
        mAuthor = author;
        mCategory = category;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getName() {
        return mName;
    }

    public String getCategory() {
        return mCategory;
    }

}

