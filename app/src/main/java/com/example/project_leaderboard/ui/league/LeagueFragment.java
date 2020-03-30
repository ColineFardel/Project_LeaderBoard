package com.example.project_leaderboard.ui.league;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.RecyclerAdapter;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.repository.LeagueRepository;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;
import com.example.project_leaderboard.ui.settings.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class LeagueFragment extends Fragment {
    public static final String EXTRA_ID_ARRAY = "to get array";
    public static final String EXTRA_TEXT = "to get league name";
    private static final String TAG = "League Fragment";
    //private String[] leagues;
    private List<League> leagues;
    private SharedPref sharedPref;
   // private LeagueViewModel leagues;
    private LeagueRepository leagueRepository;
    private Application app;
    private RecyclerAdapter<League> recyclerAdapter;
   private LeagueViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        leagueRepository = LeagueRepository.getInstance();
       // leagueViewModel = new LeagueViewModel(app, leagueRepository);
   //     allLeagues = leagueRepository.getLeagueName(context);




       /* for(int i=0;allLeagues.getValue().size()>i;i++){
            leagues[i]= String.valueOf(allLeagues.getValue().get(i));
        }
*/





       // leagues = getResources().getStringArray(R.array.league);
        leagues = new ArrayList<>();
        //leagues = (List<League>) leagueRepository.getLeagueName(getActivity());
        View view = inflater.inflate(R.layout.fragment_league,container,false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener(){
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on:" + leagues.get(position).toString());
            }

            @Override
            public void onItemClick (View v, int position){
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on : " + leagues.get(position).toString());
            }
        });

        LeagueViewModel.Factory factory = new LeagueViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(LeagueViewModel.class);
        viewModel.getLeague().observe(getViewLifecycleOwner(),leagueEntity -> {
            if(leagueEntity!=null){
                leagues = (List<League>) leagueEntity;
                recyclerAdapter.setData(leagues);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

@Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }
}