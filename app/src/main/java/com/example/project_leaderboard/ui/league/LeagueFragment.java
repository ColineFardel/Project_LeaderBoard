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

              //  ListView listView = view.findViewById(R.id.list_leagues);


     //   MyAdapter listViewAdapter = new MyAdapter(getContext(), leagues);


      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //try to send only the name of the league and do the switch in leagueboard ??
                Intent i;
                i = new Intent(getActivity(), LeagueBoard.class);
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

                startActivity(i);
            }});
*/
        return view;


        //return super.onCreateView(inflater, container, savedInstanceState);
    }

@Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }
    /*
    class MyAdapter extends ArrayAdapter{
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
*/
}