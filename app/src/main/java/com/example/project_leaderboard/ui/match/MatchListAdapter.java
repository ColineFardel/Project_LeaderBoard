package com.example.project_leaderboard.ui.match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Match;

import java.util.List;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MatchViewHolder>{

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Match> mMatch;


    public MatchListAdapter(Context context){
    layoutInflater=LayoutInflater.from(context);
    mContext=context;
    }
    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.fragment_match, parent,false);
        MatchViewHolder viewHolder = new MatchViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        if(mMatch !=null){
            Match match = mMatch.get(position);
            holder.setData(match.getId(),position);
        }else{
            holder.matchItemView.setText("Nothing");
        }
    }

    @Override
    public int getItemCount() {
        if(mMatch !=null)
            return mMatch.size();
        else return 0;
    }
    public void setMatches (List<Match> matches){
        mMatch = matches;
        notifyDataSetChanged();
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder{
        private TextView matchItemView;
        private int mPosition;
        public MatchViewHolder (View itemView){
            super (itemView);
           // matchItemView = itemView.findViewById(R.id.txvNote);
        }
        public void setData (int id, int postition){
            matchItemView.setId(id);
            mPosition=postition;
        }
    }
}
