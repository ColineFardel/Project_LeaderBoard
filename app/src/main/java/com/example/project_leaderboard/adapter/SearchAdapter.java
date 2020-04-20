package com.example.project_leaderboard.adapter;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Club;

import java.text.BreakIterator;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{
    ArrayList<Club> list;
    Button search;
    Context context;

    public SearchAdapter(ArrayList<Club> list){
        this.list=list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_league,parent,false);
        search = view.findViewById(R.id.app_bar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
            }
        });

        return new MyViewHolder(view);
      //  return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.clubId.setText(list.get(position).getClubId());
        holder.nameClub.setText(list.get(position).getNameClub());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView clubId,nameClub;
        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            clubId = itemView.findViewById(R.id.clubId);
            nameClub = itemView.findViewById(R.id.nameClub);
        }
    }

}
