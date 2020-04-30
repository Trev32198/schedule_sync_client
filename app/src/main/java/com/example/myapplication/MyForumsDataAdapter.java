package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyForumsDataAdapter extends RecyclerView.Adapter<MyForumsDataAdapter.MyForumsViewHolder> {

    private ArrayList<DiscussionThread> mForumList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class MyForumsViewHolder extends RecyclerView.ViewHolder {

        TextView MyForumTitle;

        MyForumsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            MyForumTitle = itemView.findViewById(R.id.myForumTitle);

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

    MyForumsDataAdapter(ArrayList<DiscussionThread> ForumList){
        mForumList = ForumList;
    }

    @NonNull
    @Override
    public MyForumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_view_forum_list, parent, false);
        return new MyForumsViewHolder(v, mListener);
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