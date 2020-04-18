package com.example.project_leaderboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder>{

    private List<Club> clubData;
    private RecyclerViewItemClickListener mListener;

    private Context context;

    public ClubAdapter(List<Club> clubs, Context context){
        clubData=clubs;
        this.context=context;
    }



    @NonNull
    @Override
    public ClubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view

        View tamere = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        return new ClubAdapter.ViewHolder(tamere);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubAdapter.ViewHolder holder, int position) {
        Club item = clubData.get(position);
        if (item.getClass().equals(Club.class)){
            holder.setClubName(item.getNameClub());
            holder.setWins(Integer.toString(item.getWins()));
            holder.setDraws(Integer.toString(item.getDraws()));
            holder.setLosses(Integer.toString(item.getLosses()));
            holder.setPoints(Integer.toString(item.getPoints()));
        }

    }

    @Override
    public int getItemCount() {
        if (clubData != null) {
            return clubData.size();
        } else {
            return 0;
        }
    }

    public void setLeagueData(final List<Club> data) {
        if (clubData == null) {
            clubData = data;
            notifyItemRangeInserted(0, clubData.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return clubData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (clubData instanceof Club) {
                        return ((Club) clubData.get(oldItemPosition)).getNameClub().equals(((Club) data.get(newItemPosition)).getNameClub());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (clubData instanceof Club) {
                        Club oldClub = clubData.get(newItemPosition);
                        Club newClub = data.get(newItemPosition);
                        return newClub.getNameClub().equals(oldClub.getNameClub());
                    }
                    return false;
                }
            });
            clubData = data;
            result.dispatchUpdatesTo(this);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView clubName;
        TextView points;
        TextView draws;
        TextView losses;
        TextView wins;

        ViewHolder(View view){
            super(view);
            clubName=view.findViewById(R.id.name);
            points = view.findViewById(R.id.points);
            wins = view.findViewById(R.id.wins);
            losses = view.findViewById(R.id.losses);
            draws = view.findViewById(R.id.draws);
        }

        public void setClubName(String clubName) {
            this.clubName.setText(clubName);
        }

        public void setPoints(String points) {
            this.points.setText(points);
        }

        public void setDraws(String draws) {
            this.draws.setText(draws);
        }

        public void setLosses(String losses) {
            this.losses.setText(losses);
        }

        public void setWins(String wins) {
            this.wins.setText(wins);
        }
    }
}
