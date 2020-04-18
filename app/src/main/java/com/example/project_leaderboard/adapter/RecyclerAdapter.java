package com.example.project_leaderboard.adapter;

import android.view.LayoutInflater;
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
import java.util.Objects;

/**
 * This class is used to adapt a RecyclerView
 * @author Samuel Michellod
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<T> mData;
    private RecyclerViewItemClickListener mListener;



    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        ViewHolder(TextView textView){
            super(textView);
            mTextView=textView;
        }


    }
    public RecyclerAdapter(RecyclerViewItemClickListener listener){
        mListener= listener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view

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
        T item = mData.get(position);
        if (item.getClass().equals(League.class))
            holder.mTextView.setText(((League) item).getLeagueName());
        if (item.getClass().equals(Club.class))
            holder.mTextView.setText(((Club) item).getNameClub());
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
                    if (mData instanceof League) {
                        return ((League) mData.get(oldItemPosition)).getLeagueName().equals(((League) data.get(newItemPosition)).getLeagueName());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof League) {
                        League oldLeague = (League) mData.get(newItemPosition);
                        League newLeague = data.get(newItemPosition);
                        return newLeague.getLeagueName().equals(oldLeague.getLeagueName());
                    }
                    return false;
                }
            });
            mData = (List<T>) data;
            result.dispatchUpdatesTo(this);
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
                    return RecyclerAdapter.this.mData.size();
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