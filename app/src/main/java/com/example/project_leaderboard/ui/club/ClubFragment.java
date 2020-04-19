package com.example.project_leaderboard.ui.club;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.ClubModel;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.league.LeagueListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to add a new club
 * @author Coline Fardel
 */
public class ClubFragment extends Fragment {

    private Spinner leagueSpinner;
    private EditText clubNameEditText;
    private Button addClubButton;

    private LeagueListViewModel leagueviewModel;
    private ClubViewModel clubViewModel;

    private List<League> listLeagues;
    private String leagueIdChosen;
    private Toast statusToast;
    private Club club;
    private List<ClubModel> clubModelList;

    DatabaseReference databaseReference;
    private static final String TAG = "ClubFragment";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club,container,false);

        ClubViewModel.Factory factory = new ClubViewModel.Factory(getActivity().getApplication(),"asdlf","hjl");
        clubViewModel = new ViewModelProvider(getActivity(),factory).get(ClubViewModel.class);

        /**
         * Setting the ui components
         */
        clubNameEditText = view.findViewById(R.id.club_name_edittext);
        addClubButton = view.findViewById(R.id.button_add_addclub);
        leagueSpinner = view.findViewById(R.id.league_spinner_modify_match);

        /**
         * Get the LeagueName from firebase in the spinner
         */
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("League").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> leagues = new ArrayList<>();
                for(DataSnapshot leagueSnapshot: dataSnapshot.getChildren()){
                    String leagueName = leagueSnapshot.child("LeagueName").getValue(String.class);
                    leagues.add(leagueName);
                }
                ArrayAdapter<String> leagueAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,leagues);
                leagueAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                leagueSpinner.setAdapter(leagueAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         * Looking for arguments from previous activity
         */
        /*
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

         */



        /**
         * Creating a list of leagues in order to get the id of the league chosen in the spinner
         */
        LeagueListViewModel.Factory fac = new LeagueListViewModel.Factory(getActivity().getApplication());
        leagueviewModel = new ViewModelProvider(getActivity(),fac).get(LeagueListViewModel.class);
        leagueviewModel.getAllLeagues().observe(getActivity(),league -> {
            if(league!=null){
                listLeagues = league;
            }
        });

        /**
         * Setting a listener on the spinner to know the id of the league chosen
         */
        leagueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leagueIdChosen=listLeagues.get(position).getLeagueId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Setting the action for cancel button
         */
        Button cancel_button = view.findViewById(R.id.button_cancel_addclub);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        /**
         * Setting the action for add button
         */
        addClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                club= new Club();

                club.setNameClub(clubNameEditText.getText().toString().trim());
                club.setWins(0);
                club.setDraws(0);
                club.setLosses(0);
                club.setPoints();
                club.setLeagueId(leagueIdChosen);

                clubViewModel.createClub(club, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "createClub: success");
                        getActivity().onBackPressed();
                        setResponse(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "createClub: failure",e);
                        setResponse(false);
                    }
                });
            }
        });
        return view;
    }

    /**
     * Set the response depending if the update succeeded
     * @param response
     */
    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(getActivity(), getString(R.string.club_created), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(getActivity(), getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }
}