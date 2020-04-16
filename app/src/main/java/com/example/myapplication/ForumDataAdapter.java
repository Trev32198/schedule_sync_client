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

    public static class ForumViewHolder extends RecyclerView.ViewHolder {

        public TextView forumTitle;
        public TextView forumCourse;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            forumCourse = itemView.findViewById(R.id.forumListTitle);
            forumCourse = itemView.findViewById((R.id.forumListCourse));

        }
    }

    public ForumDataAdapter(ArrayList<DiscussionThread> forumList){
        mForumList = forumList;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_view_forum_list, parent, false);
        ForumViewHolder zvh = new ForumViewHolder(v);
        return zvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        DiscussionThread currentItem = mForumList.get(position);

        System.out.println(mForumList);
        holder.forumTitle.setText(currentItem.getThreadName());
        holder.forumCourse.setText(currentItem.getAssociatedCourse());

    }

    @Override
    public int getItemCount() {
        return mForumList.size();
    }
}