package com.example.project_leaderboard.ui.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.MainActivity;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.repository.LeagueRepository;
import com.example.project_leaderboard.ui.league.LeagueViewModel;

/**
 * This class is used to add a new club
 */
public class ClubFragment extends Fragment {

    private ClubViewModel clubViewModel;
    private Club club;
    private EditText name_edittext;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_club,container,false);


        //Make a customized spinner
        Spinner leagueSpinner = view.findViewById(R.id.league_spinner_modify_match);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        leagueSpinner.setAdapter(adapter);

        //Looking for arguments from previous activity
        Bundle b = getArguments();
        String league;
        if(b!=null){
            league =b.getString("League");
            switch (league){
                case "Premier league":
                    leagueSpinner.setSelection(0);
                    break;
                case "Bundesliga":
                    leagueSpinner.setSelection(1);
                    break;
                case "Ligue 1":
                    leagueSpinner.setSelection(2);
                    break;
                case "Serie A":
                    leagueSpinner.setSelection(3);
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

        name_edittext = getActivity().findViewById(R.id.club_name_edittext);
        //Setting the action for add button
        LeagueRepository repository = new LeagueRepository();
        LeagueViewModel leagueViewModel = new LeagueViewModel(getActivity().getApplication(),repository);
        Button add_button = view.findViewById(R.id.button_add_addclub);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String leagueName = leagueSpinner.getSelectedItem().toString();
                LiveData<League> ld = leagueViewModel.getLeagueByName(getContext(),leagueName);
                League league = ld.getValue();
                if(name_edittext == null){
                    name_edittext.setError(getString(R.string.error_addclub));
                }
                else {
                    createClub(league.getLeagueId(),name_edittext.getText().toString());
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                }
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
        });*/
    }
    private void createClub(int idLeague, String clubName) {
        club = new Club(clubName,0,0,0,0,idLeague);
    }
}