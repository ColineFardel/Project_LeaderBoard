package com.example.project_leaderboard.ui.league;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.RecyclerAdapter;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class shows all the leagues
 * When you click on one league it will open the class LeagueBoard to show all the clubs of this league
 */
public class LeagueFragment extends Fragment {
    private List<League> leagues;
    private LeagueListViewModel viewModel;
    private RecyclerAdapter<League> recyclerAdapter;
    private static final String TAG = "League Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_league,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.leaguesrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        leagues = new ArrayList<>();

        recyclerAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener(){
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on:" + leagues.get(position).toString());
            }

            /**
             * Open the activity LeagueBoard and pass the id of the league which is clicked on
             * @param v
             * @param position
             */
            @Override
            public void onItemClick (View v, int position){
                Intent i;
                i = new Intent(getActivity(), LeagueBoard.class);
                i.putExtra("leagueId",leagues.get(position).getLeagueId());
                startActivity(i);
            }});

        LeagueListViewModel.Factory fac = new LeagueListViewModel.Factory(getActivity().getApplication());
        viewModel = new ViewModelProvider(getActivity(),fac).get(LeagueListViewModel.class);
        viewModel.getAllLeagues().observe(getActivity(),league -> {
            if(league!=null){
                leagues = league;
                recyclerAdapter.setLeagueData(leagues);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }
}