package com.example.project_leaderboard.ui.match;

import android.content.Context;
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
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.club.ClubListViewModel;
import com.example.project_leaderboard.ui.club.ClubViewModel;
import com.example.project_leaderboard.ui.league.LeagueListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to add a new match to the database
 * @author Samuel Michellod
 */
public class MatchFragment extends Fragment {

    private List<League> listLeagues;
    private List<Club> listClubs;

    private LeagueListViewModel leagueviewModel;
    private ClubListViewModel clubListViewModel;
    private MatchViewModel matchViewModel;

    private Spinner clubsHomeSpinner, clubsVisitorSpinner, leaguesSpinner;
    private EditText scoreHomeEditText, scoreVisitorEditText;
    private Button addMatchButton;

    private Context context;

    private String leagueIdChosen, clubHomeId, clubVisitorId;
    private int scoreHome, scoreVisitor;

    private Match match;
    private Club clubHome;
    private Club clubVisitor;

    private static final String TAG = "AddMatch";

    private Toast statusToast;

    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_match,container,false);

        MatchViewModel.Factory factory = new MatchViewModel.Factory(getActivity().getApplication());
        matchViewModel = new ViewModelProvider(getActivity(),factory).get(MatchViewModel.class);

        context = getContext();

        /**
         * Setting the ui components
         */
        leaguesSpinner = root.findViewById(R.id.league_spinner_modify_match);
        clubsHomeSpinner = root.findViewById(R.id.home_spinner_modify_match);
        clubsVisitorSpinner = root.findViewById(R.id.visitor_spinner_modify_match);

        addMatchButton = root.findViewById(R.id.button_add_addmatch);
        scoreHomeEditText = root.findViewById(R.id.score_home);
        scoreVisitorEditText = root.findViewById(R.id.score_visitor);

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
         * Put the names of the leagues from firebase in the spinner
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
                ArrayAdapter<String> leagueAdapter = new ArrayAdapter<>(context,R.layout.spinner_dropdown_layout,leagues);
                leagueAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                leaguesSpinner.setAdapter(leagueAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /**
         * Setting a listener on the spinner to change the clubs list in the spinners
         */
        leaguesSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int index, long id) {
                leagueIdChosen=listLeagues.get(index).getLeagueId();

                chargeClubSpinner(leagueIdChosen,clubsHomeSpinner);
                chargeClubSpinner(leagueIdChosen,clubsVisitorSpinner);

                /**
                 * Creating a list of clubs in order to get the id of the club chosen in the spinner
                 */
                ClubListViewModel.Factory factory = new ClubListViewModel.Factory(getActivity().getApplication(),leagueIdChosen);
                clubListViewModel = new ViewModelProvider(getActivity(),factory).get(ClubListViewModel.class);
                clubListViewModel.getClubs().observe(getActivity(), clubs -> {
                    if(clubs!=null){
                        listClubs = clubs;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        /**
         * Setting the action for cancel button
         */
        Button cancel_button = root.findViewById(R.id.button_cancel_addmatch);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        /**
         * Setting the action for add button
         */
        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the scores are not null
                if (scoreHomeEditText.length() == 0) {
                    scoreHomeEditText.setError("Please enter a score");
                    if (scoreVisitorEditText.length() == 0) {
                        scoreVisitorEditText.setError("Please enter a score");
                    }
                } else {
                    //create the match and the clubs to put in the createMatch method
                    match = new Match();

                    clubVisitor = listClubs.get(clubsVisitorSpinner.getSelectedItemPosition());
                    clubHome = listClubs.get(clubsHomeSpinner.getSelectedItemPosition());

                    databaseReference = FirebaseDatabase.getInstance().getReference("Club");

                    clubHomeId = clubHome.getClubId();
                    clubVisitorId = clubVisitor.getClubId();
                    scoreVisitor = Integer.parseInt(MatchFragment.this.scoreVisitorEditText.getText().toString());
                    scoreHome = Integer.parseInt(MatchFragment.this.scoreHomeEditText.getText().toString());

                    match.setIdClubHome(clubHomeId);
                    match.setIdClubVisitor(clubVisitorId);
                    match.setIdLeague(leagueIdChosen);
                    match.setScoreVisitor(scoreVisitor);
                    match.setScoreHome(scoreHome);

                    matchViewModel.createMatch(match, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "createMatch: success");
                            setClubsValues();

                            ClubViewModel clubViewModel;
                            ClubViewModel.Factory fac = new ClubViewModel.Factory(getActivity().getApplication(),leagueIdChosen);
                            clubViewModel = new ViewModelProvider(getActivity(),fac).get(ClubViewModel.class);
                            clubViewModel.updateClub(clubHome, new OnAsyncEventListener() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "updateClub: success");

                                    clubViewModel.updateClub(clubVisitor, new OnAsyncEventListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.d(TAG, "updateClub: success");
                                            getActivity().onBackPressed();
                                            setResponse(true);
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "updateClub: failure", e);
                                            setResponse(false);
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(Exception e) {
                                    Log.d(TAG, "updateClub: failure", e);
                                    setResponse(false);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "createMatch: failure", e);
                            setResponse(false);
                        }
                    });
                }
            }
        });
        return root;
    }

    /**
     * Set the values in the clubs depending on the scores
     */
    public void setClubsValues(){
        if(scoreHome> scoreVisitor){
            clubHome.setWins(clubHome.getWins()+1);
            clubHome.setPoints();
            clubVisitor.setLosses(clubVisitor.getLosses()+1);
            clubVisitor.setPoints();
        }
        else {
            if(scoreHome< scoreVisitor){
                clubHome.setLosses(clubHome.getLosses()+1);
                clubHome.setPoints();
                clubVisitor.setWins(clubVisitor.getWins()+1);
                clubVisitor.setPoints();
            }
            else {
                clubHome.setDraws(clubHome.getDraws()+1);
                clubHome.setPoints();
                clubVisitor.setDraws(clubVisitor.getDraws()+1);
                clubVisitor.setPoints();
            }
        }
    }

    /**
     * Put the list of clubs in the spinner depending of the league chosen
     * @param leagueId
     * @param spinner
     */
    private void chargeClubSpinner(String leagueId,Spinner spinner){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Club").child(leagueId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> clubs = new ArrayList<>();
                for(DataSnapshot clubSnapshot: dataSnapshot.getChildren()){
                    String clubName = clubSnapshot.child("nameClub").getValue(String.class);
                    clubs.add(clubName);
                }
                ArrayAdapter<String> clubAdapter = new ArrayAdapter<>(context,R.layout.spinner_dropdown_layout,clubs);
                clubAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                spinner.setAdapter(clubAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Set the response depending if the update succeeded
     * @param response
     */
    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(getActivity(), getString(R.string.match_created), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(getActivity(), getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }
}