package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.Nullable;

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

    public void searchForums(View view){
        Spinner searchChoice = findViewById(R.id.searchSpinner);
        switch (searchChoice.getSelectedItem().toString()){
            case "Course Name":
                //
                finish();
                break;
            case "Post Time":
                //TODO
                finish();
                break;
            case "Thread Name":
                //TODO;
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + searchChoice.getSelectedItem().toString());
        }
    }
}
