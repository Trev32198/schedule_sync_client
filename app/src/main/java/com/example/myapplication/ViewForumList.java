package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewForumList extends AppCompatActivity implements DialogSearch.DialogSort {

    private RecyclerView mRecyclerView;
    private ForumDataAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<DiscussionThread> forumEvents;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);


        ClientCommunicator.getThreads();
        forumEvents = ExpirationFilter.removeExpired(Sorter.sort(ServerResponseParser.parseDiscussionThreads()));
        //forumEvents.add( new DiscussionThread("what", "csc123"));

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ForumDataAdapter(forumEvents);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        final ArrayList<DiscussionThread> finalForumEvents = forumEvents;
        mAdapter.setOnItemClickListener(new ForumDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent2 = new Intent(ViewForumList.this, ExpandForum.class);
                intent2.putExtra("ForumThread", finalForumEvents.get(position));
                startActivity(intent2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.searchIcon) {
            openDialog();
            //startActivity(new Intent(ViewForumList.this, PopSearch.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog() {
        DialogSearch dialogSearch = new DialogSearch();
        dialogSearch.show(getSupportFragmentManager(), "Sort");
    }

    @Override
    public void applySort(String spinnerSelection) {
        switch (spinnerSelection) {
            case "Sort by thread name":
                Sorter.setSortMode("NAME");
                Sorter.setReverse(false);
                startActivity(new Intent(ViewForumList.this, ViewForumList.class));
                finish();
                break;
            case "Sort by course":
                Sorter.setSortMode("COURSE");
                Sorter.setReverse(false);
                startActivity(new Intent(ViewForumList.this, ViewForumList.class));
                finish();
                break;
            case "Sort by newest post time":
                Sorter.setSortMode("TIME");
                Sorter.setReverse(true);
                startActivity(new Intent(ViewForumList.this, ViewForumList.class));
                finish();
                break;
            case "Sort by oldest post time":
                Sorter.setSortMode("TIME");
                Sorter.setReverse(false);
                startActivity(new Intent(ViewForumList.this, ViewForumList.class));
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + spinnerSelection);
        }
    }
}
