package com.example.project_leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to adapt the recycler view of a list of matches
 * @author Coline Fardel
 */
public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {

    private List<Match> mData;
    private List<Club> clubsHome;
    private List<Club> clubsVisitor;
    private List<MatchModel> matchModelList;
    private RecyclerViewItemClickListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameClubHome, nameClubVisitor, scoreClubHome, scoreClubVisitor;
        View view;

        ViewHolder(View view){
            super(view);
            this.view = view;
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

        final MatchModel matchModel = matchModelList.get(position);

        String scoreVisitor = Integer.toString(item.getScoreVisitor());
        String scoreHome = Integer.toString(item.getScoreHome());

        holder.nameClubHome.setText(home.getNameClub());
        holder.nameClubVisitor.setText(visitor.getNameClub());
        holder.scoreClubVisitor.setText(scoreVisitor);
        holder.scoreClubHome.setText(scoreHome);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                matchModel.setSelected(!matchModel.isSelected());
                String msg = getNumberSelected() + holder.view.getContext().getString(R.string.item_selected);
                Toast statusToast = Toast.makeText(holder.view.getContext(), msg, Toast.LENGTH_LONG);
                statusToast.show();
                return true;
            }
        });
    }

    /**
     * Method to get all the clubs selected
     * @return
     */
    public List<Match> getSelectedMatches(){
        List<Match> matches = new ArrayList<>();
        for(MatchModel matchModel : matchModelList){
            if(matchModel.isSelected())
                matches.add(matchModel.getMatch());
        }
        return matches;
    }

    /**
     * Method to get the club selected
     * @return
     */
    public Match getSelectedMatch(){
        return getSelectedMatches().get(0);
    }

    /**
     * Method to get the number of clubs selected
     * @return
     */
    public int getNumberSelected(){
        return getSelectedMatches().size();
    }

    public void setMatchModelList(List<MatchModel> matchModelList){
        this.matchModelList = matchModelList;
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
                    return (mData.get(oldItemPosition)).getMatchId().equals(data.get(newItemPosition).getMatchId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Match oldMatch = mData.get(newItemPosition);
                    Match newMatch = data.get(newItemPosition);
                    return newMatch.getMatchId().equals(oldMatch.getMatchId());
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
