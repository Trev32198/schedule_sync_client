package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ForumDataAdapter extends RecyclerView.Adapter<ForumDataAdapter.ForumViewHolder> {

    private ArrayList<DiscussionThread> mForumList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    static class ForumViewHolder extends RecyclerView.ViewHolder {

        TextView ForumTitle;
        TextView ForumCourse;

        ForumViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            ForumTitle = itemView.findViewById(R.id.forumListTitle);
            ForumCourse = itemView.findViewById((R.id.forumListCourse));

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

    ForumDataAdapter(ArrayList<DiscussionThread> ForumList){
        mForumList = ForumList;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_view_forum_list, parent, false);
        return new ForumViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        DiscussionThread currentItem = mForumList.get(position);
        holder.ForumTitle.setText(currentItem.getThreadName());
        holder.ForumCourse.setText(currentItem.getAssociatedCourse());
    }

    @Override
    public int getItemCount() {
        return mForumList.size();
    }
}