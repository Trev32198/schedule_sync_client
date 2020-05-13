package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SearchScreen extends AppCompatDialogFragment {

    private EditText threadName;
    private Button threadSearch;
    private EditText courseName;
    private Button courseSearch;
    private EditText posterName;
    private Button posterSearch;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_search, null);

        threadName = view.findViewById(R.id.textName);
        threadSearch = view.findViewById(R.id.buttonName);
        courseName = view.findViewById(R.id.textCourse);
        courseSearch = view.findViewById(R.id.buttonCourse);
        posterName = view.findViewById(R.id.textPoster);
        posterSearch = view.findViewById(R.id.buttonPoster);

        threadSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                searchName();
            }
        });

        courseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                searchCourse();
            }
        });

        posterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                searchPoster();
            }
        });

        builder.setView(view).setTitle("Search");

        return  builder.create();
    }

    public void searchName(){
        Searcher.setSearchMode("NAME");
        Intent intent = new Intent(getActivity(), SearchResults.class);
        intent.putExtra("ForumThread", threadName.getText().toString());
        startActivity(intent);
    }

    public void searchPoster(){
        Searcher.setSearchMode("CREATOR");
        Intent intent = new Intent(getActivity(), SearchResults.class);
        intent.putExtra("ForumThread", posterName.getText().toString());
        startActivity(intent);
    }

    public void searchCourse(){
        Searcher.setSearchMode("COURSE");
        Intent intent = new Intent(getActivity(), SearchResults.class);
        intent.putExtra("ForumThread", courseName.getText().toString());
        startActivity(intent);
    }
}
