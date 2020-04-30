package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (height * .6));
    }

    public void searchName(View view){
        EditText forumName = findViewById(R.id.textName);
        Intent intent = new Intent(SearchScreen.this, SearchResults.class);
        intent.putExtra("ForumThread", forumName.getText().toString());
        startActivity(intent);
        finish();
    }

    public void searchPoster(View view){
        EditText posterName = findViewById(R.id.textPoster);
        Intent intent = new Intent(SearchScreen.this, SearchResults.class);
        intent.putExtra("ForumThread", posterName.getText().toString());
        startActivity(intent);
        finish();
    }

    public void searchCourse(View view){
        EditText className = findViewById(R.id.textCourse);
        Intent intent = new Intent(SearchScreen.this, SearchResults.class);
        intent.putExtra("ForumThread", className.getText().toString());
        startActivity(intent);
        finish();
    }
}
