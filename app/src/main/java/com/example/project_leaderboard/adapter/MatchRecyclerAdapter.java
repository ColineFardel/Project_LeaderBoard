package com.example.project_leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;
import com.example.project_leaderboard.ui.club.ClubViewModel;

import java.util.List;

public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {

    private List<Match> mData;
    private List<Club> clubsHome;
    private List<Club> clubsVisitor;
    private RecyclerViewItemClickListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameClubHome, nameClubVisitor, scoreClubHome, scoreClubVisitor;

        ViewHolder(View view){
            super(view);
            nameClubHome = view.findViewById(R.id.club_home);
            nameClubVisitor = view.findViewById(R.id.club_visitor);
            scoreClubHome = view.findViewById(R.id.score_home_list);
            scoreClubVisitor = view.findViewById(R.id.score_visitor_list);
        }
    }
    public MatchRecyclerAdapter(RecyclerViewItemClickListener listener){
        mListener= listener;
    }

    @NonNull
    @Override
    public MatchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_match, parent, false);


        final MatchRecyclerAdapter.ViewHolder viewHolder = new MatchRecyclerAdapter.ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchRecyclerAdapter.ViewHolder holder, int position) {
        Match item = mData.get(position);
        Club home = clubsHome.get(position);
        Club visitor = clubsVisitor.get(position);

        String scoreVisitor = Integer.toString(item.getScoreVisitor());
        String scoreHome = Integer.toString(item.getScoreHome());

        holder.nameClubHome.setText(home.getNameClub());
        holder.nameClubVisitor.setText(visitor.getNameClub());
        holder.scoreClubVisitor.setText(scoreVisitor);
        holder.scoreClubHome.setText(scoreHome);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setListClubs(List<Club> clubsHome, List<Club> clubsVisitor){
        this.clubsHome = clubsHome;
        this.clubsVisitor = clubsVisitor;
    }

    public void setMatchData(final List<Match> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, mData.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof Club) {
                        return (mData.get(oldItemPosition)).getMatchId().equals(data.get(newItemPosition).getMatchId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof Club) {
                        Match oldMatch = mData.get(newItemPosition);
                        Match newMatch = data.get(newItemPosition);
                        return newMatch.getMatchId().equals(oldMatch.getMatchId());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }



}
