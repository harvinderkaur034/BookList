package com.example.android.booklist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void search(View view) {
        EditText editText = (EditText) findViewById(R.id.search);
        String gotText = editText.getText().toString();
        Intent newIntent = new Intent(MainActivity.this, UpdateActivity.class);
        newIntent.putExtra("word", gotText);
        startActivity(newIntent);
    }
}
