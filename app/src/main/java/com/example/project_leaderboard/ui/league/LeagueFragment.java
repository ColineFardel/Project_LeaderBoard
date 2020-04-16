package com.example.project_leaderboard.ui.league;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.LeagueRecyclerAdapter;
import com.example.project_leaderboard.adapter.RecyclerAdapter;
import com.example.project_leaderboard.adapter.TestRecyclerAdapter;
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

    private LeagueRecyclerAdapter leagueAdapter;


    public static final String EXTRA_ID_ARRAY = "to get array";
    public static final String EXTRA_TEXT = "to get league name";



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

            //Open the activity LeagueBoard when you click on one league and give it the array of the clubs and the name of the league
            @Override
            public void onItemClick (View v, int position){
                Intent i;
                i = new Intent(getActivity(), LeagueBoard.class);
                /*
                switch (position){
                    case 0:

                        i.putExtra(EXTRA_ID_ARRAY, R.array.clubs_premierLeague);
                        i.putExtra(EXTRA_TEXT, "Premier league");
                        break;
                    case 1:
                        i.putExtra(EXTRA_ID_ARRAY, R.array.clubs_budesliga);
                        i.putExtra(EXTRA_TEXT, "Bundesliga");
                        break;
                    case 2:
                        i.putExtra(EXTRA_ID_ARRAY, R.array.clubs_ligue1);
                        i.putExtra(EXTRA_TEXT, "Ligue 1");
                        break;
                    case 3:
                        i.putExtra(EXTRA_ID_ARRAY, R.array.clubs_seria);
                        i.putExtra(EXTRA_TEXT, "Serie A");
                        break;
                }

                 */
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


        //leagueAdapter = new LeagueRecyclerAdapter(leagues);
        //recyclerView.setAdapter(leagueAdapter);


        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    //Create an adapter for the list view
    class MyAdapter extends ArrayAdapter {
        Context context;
        String name[];
        MyAdapter (Context c, String name[]){
            super(c,R.layout.row_leagues,R.id.title_list, name);
            this.context=c;
            this.name=name;
        }
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_leagues, parent, false);
            TextView myName = row.findViewById(R.id.title_list);
            myName.setText(name[position]);
            return row;
        }
    }
}