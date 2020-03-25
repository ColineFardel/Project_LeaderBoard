package com.example.project_leaderboard.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_leaderboard.R;

public class MatchFragment extends Fragment {

    private MatchViewModel matchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        matchViewModel =
                ViewModelProviders.of(this).get(MatchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_match, container, false);
        final TextView textView = root.findViewById(R.id.text_match);
        matchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //Colored Spinner

        Spinner leaguespinner = root.findViewById(R.id.league_spinner);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        leaguespinner.setAdapter(adapter);

        Spinner clubhome = root.findViewById(R.id.home_spinner);
        String [] list2 = getResources().getStringArray(R.array.clubs_premierLeague); //changer par rapport au choix du premier spinner !!!
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,list2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        clubhome.setAdapter(adapter2);

        Spinner clubvisitor = root.findViewById(R.id.visitor_spinner);
        clubvisitor.setAdapter(adapter2);



        return root;
    }

}