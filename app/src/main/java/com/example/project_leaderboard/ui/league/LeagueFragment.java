package com.example.project_leaderboard.ui.league;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_leaderboard.R;

public class LeagueFragment extends Fragment {

    private LeagueViewModel leagueViewModel;

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
        return root;
    }
}