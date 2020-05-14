package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SearchScreen extends AppCompatDialogFragment {

    private EditText queryTextbox;
    private Button threadSearch;
    private Button courseSearch;
    private Button posterSearch;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_search, null);

        queryTextbox = view.findViewById(R.id.queryBox);
        threadSearch = view.findViewById(R.id.buttonName);
        courseSearch = view.findViewById(R.id.buttonCourse);
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
        ThreadSearcher.setSearchMode("NAME");
        Intent intent = new Intent(getActivity(), SearchResults.class);
        intent.putExtra("ForumThread", queryTextbox.getText().toString());
        startActivity(intent);
    }

    public void searchPoster(){
        ThreadSearcher.setSearchMode("CREATOR");
        Intent intent = new Intent(getActivity(), SearchResults.class);
        intent.putExtra("ForumThread", queryTextbox.getText().toString());
        startActivity(intent);
    }

    public void searchCourse(){
        ThreadSearcher.setSearchMode("COURSE");
        Intent intent = new Intent(getActivity(), SearchResults.class);
        intent.putExtra("ForumThread", queryTextbox.getText().toString());
        startActivity(intent);
    }
}
