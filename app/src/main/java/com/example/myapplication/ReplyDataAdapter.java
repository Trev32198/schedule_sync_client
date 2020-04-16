package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ReplyDataAdapter extends RecyclerView.Adapter<ReplyDataAdapter.ReplyViewHolder> {

    private ArrayList<ThreadReply> mReplyList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ReplyViewHolder extends RecyclerView.ViewHolder {

        public TextView ReplyBody;
        public TextView ReplyCreator;
        public TextView ReplyDate;

        public ReplyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            ReplyBody = itemView.findViewById(R.id.replyBody);
            ReplyCreator = itemView.findViewById((R.id.replyCreator));
            ReplyDate = itemView.findViewById(R.id.replyDate);

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

    public ReplyDataAdapter(ArrayList<ThreadReply> ReplyList){
        mReplyList = ReplyList;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_view_reply_list, parent, false);
        ReplyViewHolder zvh = new ReplyViewHolder(v, mListener);
        return zvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        ThreadReply currentItem = mReplyList.get(position);
        holder.ReplyBody.setText(currentItem.getBody());
        holder.ReplyDate.setText(currentItem.getDatetime().toString());
        holder.ReplyCreator.setText(currentItem.getUsername());
    }

    @Override
    public int getItemCount() {
        return mReplyList.size();
    }
}