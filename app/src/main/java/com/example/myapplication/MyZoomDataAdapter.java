package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyZoomDataAdapter extends RecyclerView.Adapter<MyZoomDataAdapter.ZoomViewHolder> {

    private ArrayList<ZoomEvent> mZoomList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ZoomViewHolder extends RecyclerView.ViewHolder {

        public TextView zoomTitle;
        public TextView zoomCourse;
        public Button deleteButton;

        public ZoomViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            zoomTitle = itemView.findViewById(R.id.myZoomListTitle);
            zoomCourse = itemView.findViewById((R.id.myZoomListCourse));
            deleteButton = itemView.findViewById(R.id.deleteZoom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public MyZoomDataAdapter(ArrayList<ZoomEvent> zoomList){
        mZoomList = zoomList;
    }

    @NonNull
    @Override
    public ZoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_zoom_events, parent, false);
        return new ZoomViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoomViewHolder holder, int position) {
        ZoomEvent currentItem = mZoomList.get(position);

        holder.zoomTitle.setText(currentItem.getTitle());
        holder.zoomCourse.setText((currentItem.getCourse()));
    }

    @Override
    public int getItemCount() {
        return mZoomList.size();
    }
}