package com.example.project_leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;

import java.util.List;

/**
 * This class is used to adapt a RecyclerView
 * @author Samuel Michellod
 */
public class LeagueRecyclerAdapter extends RecyclerView.Adapter<LeagueRecyclerAdapter.ViewHolder> {
    private List<League> mData;
    private RecyclerViewItemClickListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        ViewHolder(TextView textView){
            super(textView);
            mTextView=textView;
        }
    }
    public LeagueRecyclerAdapter(RecyclerViewItemClickListener listener){
        mListener= listener;
    }

    @NonNull
    @Override
    public LeagueRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
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
        League item = mData.get(position);
        holder.mTextView.setText(item.getLeagueName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setLeagueData(final List<League> data) {
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
                    if (mData instanceof League) {
                        return  mData.get(oldItemPosition).getLeagueName().equals(data.get(newItemPosition).getLeagueName());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof League) {
                        League oldLeague = mData.get(newItemPosition);
                        League newLeague = data.get(newItemPosition);
                        return newLeague.getLeagueName().equals(oldLeague.getLeagueName());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}