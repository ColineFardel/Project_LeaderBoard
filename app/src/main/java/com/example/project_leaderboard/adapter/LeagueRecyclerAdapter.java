package com.example.project_leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;

import java.util.List;

public class LeagueRecyclerAdapter extends RecyclerView.Adapter<LeagueRecyclerAdapter.LeagueViewHolder> {
    private List<League> data;
    private RecyclerViewItemClickListener listener;

    static class LeagueViewHolder extends RecyclerView.ViewHolder{
        TextView leagueName;

        LeagueViewHolder(View view) {
            super(view);
            this.leagueName = view.findViewById(R.id.title_list);
        }
    }

    public LeagueRecyclerAdapter(List<League> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leagues, parent, false);
        LeagueViewHolder viewHolder = new LeagueViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {
        League league = data.get(position);
        holder.leagueName.setText(league.getLeagueName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}