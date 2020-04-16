package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ZoomDataAdapter extends RecyclerView.Adapter<ZoomDataAdapter.ZoomViewHolder> {

    private ArrayList<ZoomEvent> mZoomList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ZoomViewHolder extends RecyclerView.ViewHolder {

            public TextView zoomTitle;
            public TextView zoomCourse;

            public ZoomViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
                super(itemView);
                zoomTitle = itemView.findViewById(R.id.zoomListTitle);
                zoomCourse = itemView.findViewById((R.id.zoomListCourse));

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
            }
        }

        public ZoomDataAdapter(ArrayList<ZoomEvent> zoomList){
            mZoomList = zoomList;
        }
        
    @NonNull
    @Override
    public ZoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.view_zoom_events_activity, parent, false);
        ZoomViewHolder zvh = new ZoomViewHolder(v, mListener);
        return zvh;
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