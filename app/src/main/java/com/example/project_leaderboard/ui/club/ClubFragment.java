package com.example.project_leaderboard.ui.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_leaderboard.R;

/**
 * This class is used to add a new club
 */
public class ClubFragment extends Fragment {

    private ClubViewModel clubViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_club,container,false);

        //Make a customized spinner
        Spinner colorspinner = view.findViewById(R.id.league_spinner_modify_match);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        colorspinner.setAdapter(adapter);

        //Looking for arguments from previous activity
        Bundle b = getArguments();
        String league;
        if(b!=null){
            league =b.getString("League");
            switch (league){
                case "Premier league":
                    colorspinner.setSelection(0);
                    break;
                case "Bundesliga":
                    colorspinner.setSelection(1);
                    break;
                case "Ligue 1":
                    colorspinner.setSelection(2);
                    break;
                case "Serie A":
                    colorspinner.setSelection(3);
                    break;
            }
        }

        //Setting the action for cancel button
        Button cancel_button = view.findViewById(R.id.button_cancel_addclub);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Setting the action for add button
        Button add_button = view.findViewById(R.id.button_add_addclub);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add the club in the database
            }
        });


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