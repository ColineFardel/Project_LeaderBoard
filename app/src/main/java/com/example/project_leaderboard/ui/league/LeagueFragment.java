package com.example.project_leaderboard.ui.league;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.project_leaderboard.R;

public class LeagueFragment extends Fragment {
    public static final String EXTRA_ID_ARRAY = "to get array";
    public static final String EXTRA_TEXT = "to get league name";
    private String[] leagues;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        leagues = getResources().getStringArray(R.array.league);

        View view = inflater.inflate(R.layout.fragment_league,container,false);
        ListView listView = view.findViewById(R.id.list_leagues);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1,
                    leagues);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
}