package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ZoomViewHolder> {

    private ArrayList<ZoomEvent> mZoomList;

    public static class ZoomViewHolder extends RecyclerView.ViewHolder {

            public TextView mTextView1;
            public TextView mTextView2;

            public ZoomViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextView1 = itemView.findViewById(R.id.textView1);
            }
        }

        public DataAdapter(ArrayList<ZoomEvent> zoomList){
            mZoomList = zoomList;
        }
        
    @NonNull
    @Override
    public ZoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.view_zoom_events_activity, parent, false);
        ZoomViewHolder zvh = new ZoomViewHolder(v);
        return zvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ZoomViewHolder holder, int position) {
        ZoomEvent currentItem = mZoomList.get(position);

        holder.mTextView1.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return mZoomList.size();
    }
}