package com.example.project_leaderboard.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;

import java.util.List;

public class LeagueRecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<League> data;
    private RecyclerViewItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
