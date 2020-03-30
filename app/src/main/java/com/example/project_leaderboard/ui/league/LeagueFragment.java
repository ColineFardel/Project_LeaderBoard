package com.example.project_leaderboard.ui.league;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.RecyclerAdapter;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.ui.settings.SharedPref;
import java.util.List;

/**
 * This class shows all the leagues
 * When you click on one league it will open the class LeagueBoard to show all the clubs of this league
 */
public class LeagueFragment extends Fragment {
    public static final String EXTRA_ID_ARRAY = "to get array";
    public static final String EXTRA_TEXT = "to get league name";
    private String[] leagues;
    private LiveData<List<League>> allLeagues;
    private SharedPref sharedPref;
    private LeagueViewModel leagueViewModel;
    private Application app;
    private RecyclerAdapter recyclerAdapter;
   private LeagueViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        app= getActivity().getApplication();
        leagueViewModel = new LeagueViewModel(app);
        allLeagues = leagueViewModel.getLeagueName();



        for(int i=0;allLeagues.getValue().size()>i;i++){
            leagues[i]= String.valueOf(allLeagues.getValue().get(i));
        }


         */

        leagues = getResources().getStringArray(R.array.league);

        View view = inflater.inflate(R.layout.fragment_league,container,false);
        ListView listView = view.findViewById(R.id.list_leagues);

        //Assign an adapter to the list view
        MyAdapter listViewAdapter = new MyAdapter(getContext(), leagues);
        listView.setAdapter(listViewAdapter);

        //Open the activity LeagueBoard when you click on one league and give it the array of the clubs and the name of the league
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        return view;
    }

    //Create an adapter for the list view
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

}