package com.example.project_leaderboard.ui.league;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.settings.SharedPref;

public class LeagueFragment extends Fragment {
    public static final String EXTRA_ID_ARRAY = "to get array";
    public static final String EXTRA_TEXT = "to get league name";
    private String[] leagues;
    private SharedPref sharedPref;
    private LeagueViewModel leagueViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        leagues = getResources().getStringArray(R.array.league);

        View view = inflater.inflate(R.layout.fragment_league,container,false);
        ListView listView = view.findViewById(R.id.list_leagues);

        MyAdapter listViewAdapter = new MyAdapter(getContext(), leagues);

        listView.setAdapter(listViewAdapter);

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
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
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