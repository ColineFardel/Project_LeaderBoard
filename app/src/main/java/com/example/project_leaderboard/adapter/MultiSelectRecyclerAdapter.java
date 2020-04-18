package com.example.project_leaderboard.adapter;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;

import java.util.List;

public class MultiSelectRecyclerAdapter extends RecyclerView.Adapter<MultiSelectRecyclerAdapter.MyViewHolder> {
    private List<String> dataList;
    private static MultipleClickListener mClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    interface MultipleClickListener {
        void onItemClick(int position, View v);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener, View.OnLongClickListener{

        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            mClickListener.onItemClick(getAdapterPosition(),v);
            return false;
        }
    }
}
