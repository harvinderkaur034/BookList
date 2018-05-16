package com.example.android.booklist;

import android.content.Context;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Books> {
    public BookAdapter(Context context, List<Books> book) {
        super(context, 0, book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, parent, false);
        }
        Books cBook = getItem(position);
        TextView bName = (TextView) listView.findViewById(R.id.name);
        String book_Name = cBook.getName();
        bName.setText(book_Name);

        TextView aName = (TextView) listView.findViewById(R.id.author);
        String author_Name = cBook.getAuthor();
        aName.setText(author_Name);

        TextView dName = (TextView) listView.findViewById(R.id.category);
        String category_Name = cBook.getCategory();
        dName.setText(category_Name);
        return listView;
    }
}
