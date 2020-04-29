package com.example.myapplication;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AssignmentsDataAdapter extends RecyclerView.Adapter<AssignmentsDataAdapter.AssignmentsViewHolder> {

    private ArrayList<MoodleAssignment> mAssignmentsList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class AssignmentsViewHolder extends RecyclerView.ViewHolder {

        public TextView assignmentName;
        public TextView assignmentDate;

        public AssignmentsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.assignmentTitle);
            assignmentDate = itemView.findViewById((R.id.assignmentDate));

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

    public AssignmentsDataAdapter(ArrayList<MoodleAssignment> assignmentsList){
        mAssignmentsList = assignmentsList;
    }

    @NonNull
    @Override
    public AssignmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_view_assignments, parent, false);
        return new AssignmentsViewHolder(v, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AssignmentsViewHolder holder, int position) {
        MoodleAssignment currentItem = mAssignmentsList.get(position);

        holder.assignmentName.setText(currentItem.getName());
        holder.assignmentDate.setText((currentItem.getDateString()));
    }

    @Override
    public int getItemCount() {
        return mAssignmentsList.size();
    }
}