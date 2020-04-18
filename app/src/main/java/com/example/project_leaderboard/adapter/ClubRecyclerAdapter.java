package com.example.project_leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;

import java.util.List;

/**
 * This class is used to adapt a RecyclerView
 * @author Samuel Michellod
 */
public class ClubRecyclerAdapter<T> extends RecyclerView.Adapter<ClubRecyclerAdapter.ViewHolder> {
    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, wins, draws, points, losses;

        ViewHolder(View view){
            super(view);
            name =view.findViewById(R.id.name);
            wins=view.findViewById(R.id.wins);
            points = view.findViewById(R.id.points);
            draws = view.findViewById(R.id.draws);
            losses = view.findViewById(R.id.losses);
        }
    }
    public ClubRecyclerAdapter(RecyclerViewItemClickListener listener){
        mListener= listener;
    }

    @NonNull
    @Override
    public ClubRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);


        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T item = mData.get(position);

        int wins = (((Club) item).getWins());
        String winsString = String.valueOf(wins);

        int draws = (((Club) item).getDraws());
        String drawsString = String.valueOf(draws);

        int points = (((Club) item).getPoints());
        String pointsString = String.valueOf(points);

        int losses = (((Club) item).getLosses());
        String lossesString = String.valueOf(losses);


        holder.name.setText(((Club) item).getNameClub());
        holder.wins.setText(winsString);
        holder.draws.setText(drawsString);
        holder.losses.setText(lossesString);
        holder.points.setText(pointsString);

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setClubData(final List<Club> data) {
        if (mData == null) {
            mData = (List<T>) data;
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
                        return ((Club) mData.get(oldItemPosition)).getNameClub().equals(((Club) data.get(newItemPosition)).getNameClub());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof Club) {
                        Club oldClub = (Club) mData.get(newItemPosition);
                        Club newClub = data.get(newItemPosition);
                        return newClub.getNameClub().equals(oldClub.getNameClub());
                    }
                    return false;
                }
            });
            mData = (List<T>) data;
            result.dispatchUpdatesTo(this);
        }
    }


}
