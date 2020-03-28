package com.example.project_leaderboard.ui.club;

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

public class ClubFragment extends Fragment {

    private ClubViewModel clubViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_club,container,false);
        TextView textView = view.findViewById(R.id.text_club);

        //Make a customized spinner
        Spinner colorspinner = view.findViewById(R.id.league_spinner);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        colorspinner.setAdapter(adapter);

        return view;
        /*
        clubViewModel =
                ViewModelProviders.of(this).get(ClubViewModel.class);
        View root = inflater.inflate(R.layout.fragment_club, container, false);
        final TextView textView = root.findViewById(R.id.text_club);
        clubViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;*/
    }
}