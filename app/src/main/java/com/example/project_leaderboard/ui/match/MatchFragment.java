package com.example.project_leaderboard.ui.match;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_leaderboard.MatchActivity;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.ui.club.ClubFragment;
import com.example.project_leaderboard.ui.league.LeagueBoard;

import java.util.List;

public class MatchFragment extends Fragment {

    private MatchViewModel matchViewModel;
    private MatchListAdapter matchListAdapter;
    private Application application;
    ImageButton imageButton;
    Intent intent;
    private Match match;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_match,container,false);

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.getAllMatches().observe(getViewLifecycleOwner(), new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
                matchListAdapter.setMatches(matches);
            }
        });



        //Colored Spinner

        Spinner leaguespinner = root.findViewById(R.id.league_spinner);
        String[] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_dropdown_layout, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        leaguespinner.setAdapter(adapter);

        Spinner clubhome = root.findViewById(R.id.home_spinner);
        String[] list2 = getResources().getStringArray(R.array.clubs_premierLeague); //changer par rapport au choix du premier spinner !!!
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_dropdown_layout, list2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        clubhome.setAdapter(adapter2);

        Spinner clubvisitor = root.findViewById(R.id.visitor_spinner);
        clubvisitor.setAdapter(adapter2);
        return root;
    }

    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
            matchViewModel.insert(match);
                Intent myIntent = new Intent(getActivity(),MatchActivity.class);
                startActivity(myIntent);
                break;
        }
    }

}