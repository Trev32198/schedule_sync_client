package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyForumsDataAdapter extends RecyclerView.Adapter<MyForumsDataAdapter.MyForumsViewHolder> {

    private ArrayList<DiscussionThread> mForumList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MyForumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_my_forums, parent, false);
        return new MyForumsViewHolder(v, mListener);
    }

    MyForumsDataAdapter(ArrayList<DiscussionThread> ForumList) {
        mForumList = ForumList;
    }

    static class MyForumsViewHolder extends RecyclerView.ViewHolder {

        TextView MyForumTitle;
        Button DeleteButton;

        MyForumsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            MyForumTitle = itemView.findViewById(R.id.myForumTitle);
            DeleteButton = itemView.findViewById(R.id.deleteForum);

            MyForumTitle.setOnClickListener(new View.OnClickListener() {
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

            DeleteButton.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onBindViewHolder(@NonNull MyForumsViewHolder holder, int position) {
        DiscussionThread currentItem = mForumList.get(position);
        holder.MyForumTitle.setText(currentItem.getThreadName());
    }

    @Override
    public int getItemCount() {
        return mForumList.size();
    }
}