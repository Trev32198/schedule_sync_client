package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class tempZoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        //TextView tempTxt = findViewById(R.id.enterZoomDataHere);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = new String[5];
        //myDataset[0] = "what";
        //RecyclerView.Adapter mAdapter = new DataAdapter(myDataset);
        //recyclerView.setAdapter(mAdapter);









        try {
            ClientCommunicator.getZoomEvents();
            ClientCommunicator.getLatestResult();
        }
        catch (Exception exception){
            System.out.println("what");
        }
        finally {
            //tempTxt.setText(ClientCommunicator.getLatestResult());

        }
        }
}
