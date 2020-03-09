package com.example.project_leaderboard.ui.league;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_leaderboard.R;

public class LeagueFragment extends Fragment {

    private LeagueViewModel leagueViewModel;

   /* private String[] leagues= getResources().getStringArray(R.array.league);
    ListView list;
    View v;*/


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leagueViewModel =
                ViewModelProviders.of(this).get(LeagueViewModel.class);
        View root = inflater.inflate(R.layout.fragment_league, container, false);
        final TextView textView = root.findViewById(R.id.text_league);
        leagueViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
       // v=root;
        return root;
    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        leagues = v.getResources().getStringArray(R.array.league);
        list = (ListView) v.findViewById(R.id.list_leagues);

        list.setAdapter(new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_list_item_1 , leagues){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.fragment_league, parent, false);
                } else {
                    view = convertView;
                }

                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.list_text_league);
                textView1.setText(leagues[position]);

                return view;
            }
        });
    }*/
}