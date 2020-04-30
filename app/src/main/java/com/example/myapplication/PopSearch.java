package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class PopSearch extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_search);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .6), (int) (height * .6));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void searchForums(View view){
        Spinner searchChoice = findViewById(R.id.searchSpinner);
        switch (searchChoice.getSelectedItem().toString()){
            case "Sort by thread name":
                Sorter.setSortMode("NAME");
                Sorter.setReverse(false);
                startActivity(new Intent(PopSearch.this, ViewForumList.class));
                finish();
                break;
            case "Sort by course":
                Sorter.setSortMode("COURSE");
                Sorter.setReverse(false);
                startActivity(new Intent(PopSearch.this, ViewForumList.class));
                finish();
                break;
            case "Sort by newest post time":
                Sorter.setSortMode("TIME");
                Sorter.setReverse(true);
                startActivity(new Intent(PopSearch.this, ViewForumList.class));
                finish();
                break;
            case "Sort by oldest post time":
                Sorter.setSortMode("TIME");
                Sorter.setReverse(false);
                startActivity(new Intent(PopSearch.this, ViewForumList.class));
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + searchChoice.getSelectedItem().toString());
        }
    }
}
