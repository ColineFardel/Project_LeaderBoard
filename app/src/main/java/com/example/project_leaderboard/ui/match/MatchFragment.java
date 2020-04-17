package com.example.project_leaderboard.ui.match;

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
    private Spinner clubsHomeSpinner;
    private Spinner clubsVisitorSpinner;
    private Spinner leaguesSpinner;

    private String leagueIdChosen;
    private String clubHomeId;
    private String clubVisitorId;

    private static final String TAG = "AddMatch";
    private Match match;
    private boolean isEditable;
    private Toast statusToast;
    EditText ScoreHome, ScoreVisitor;
    private MatchViewModel matchViewModel;
    DatabaseReference databaseReference;





    //private OnAsyncEventListener callback;
    //private Application application;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_match,container,false);

        MatchViewModel.Factory factory = new MatchViewModel.Factory(getActivity().getApplication());
        matchViewModel = new ViewModelProvider(getActivity(),factory).get(MatchViewModel.class);

        /* matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.getAllMatches().observe(getViewLifecycleOwner(), new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
                matchListAdapter.setMatches(matches);
            }
        });
        */
        /*
        ScoreHome = root.findViewById(R.id.score_home);
        ScoreVisitor = root.findViewById(R.id.score_visitor);
        add = root.findViewById(R.id.button_add);
        */

        /*add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ScoreHome.getText().length()==0){
                    add.setEnabled(false);
                    add.setError("Please enter a score");
                }
                if(ScoreVisitor.getText().length()==0){
                    add.setEnabled(false);
                    add.setError("Please enter a score");
                }
                else{
                    add.setEnabled(true);
                }
            }
        });
        */

        /**
         * Spinners
         */
        leaguesSpinner = root.findViewById(R.id.league_spinner_modify_match);
        clubsHomeSpinner = root.findViewById(R.id.home_spinner_modify_match);
        clubsVisitorSpinner = root.findViewById(R.id.visitor_spinner_modify_match);

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
                ArrayAdapter<String> leagueAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,leagues);
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
        Button add_button = root.findViewById(R.id.button_add_addmatch);
        ScoreHome = root.findViewById(R.id.score_home);
        ScoreVisitor= root.findViewById(R.id.score_visitor);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add the club in the database
                if (ScoreHome.length() == 0) {
                    ScoreHome.setError("Please enter a score");
                    if (ScoreVisitor.length() == 0) {
                        ScoreVisitor.setError("Please enter a score");
                    }
                } else {
                    Match match = new Match();

                    Club clubVisitor =listClubs.get(clubsVisitorSpinner.getSelectedItemPosition());
                    Club clubHome = listClubs.get(clubsHomeSpinner.getSelectedItemPosition());

                    clubVisitor.setLeagueId(leagueIdChosen);
                    clubHome.setLeagueId(leagueIdChosen);

                    clubHomeId = clubHome.getClubId();
                    clubVisitorId = clubVisitor.getClubId();
                    int scoreVisitor = Integer.parseInt(ScoreVisitor.getText().toString());
                    int scoreHome = Integer.parseInt(ScoreHome.getText().toString());

                    match.setIdClubHome(clubHomeId);
                    match.setIdClubVisitor(clubVisitorId);
                    match.setIdLeague(leagueIdChosen);
                    match.setScoreVisitor(scoreVisitor);
                    match.setScoreHome(scoreHome);

                    matchViewModel.createMatch(match, new OnAsyncEventListener() {//SI SA PLANTE C A CAUSE DU UPDATE QUI A DECIDER DE ME CASSER LES COUILLES (tu peux check si tu veux apparemment le league id est null alors que je le set mais bon hyn sa marche jamais comme d'hab)
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "createMatch: success");

                            /**
                             * Setting the values to update it
                             */
                            if(scoreHome>scoreVisitor){
                                clubHome.setWins(clubHome.getWins()+1);
                                clubHome.setPoints(clubHome.getPoints()+3); //SAMUEL JE SAIS PLUS COMBIEN ON GAGNE DE POINTS SI ON GAGNE OU PERT OU EGALITE ET C 23H05 DONC CONTROLE ET MET LES BON NUM MERCI !
                                clubVisitor.setLosses(clubVisitor.getLosses()+1);
                                clubVisitor.setPoints(clubVisitor.getPoints()+1); //ICI
                            }
                            else {
                                if(scoreHome<scoreVisitor){
                                    clubHome.setLosses(clubHome.getLosses()+1);
                                    clubHome.setPoints(clubHome.getPoints()+1); //ICI
                                    clubVisitor.setWins(clubVisitor.getWins()+1);
                                    clubVisitor.setPoints(clubVisitor.getPoints()+3); //ICI
                                }
                                else {
                                    clubHome.setDraws(clubHome.getDraws()+1);
                                    clubHome.setPoints(clubHome.getPoints()+2); //ICI
                                    clubVisitor.setDraws(clubVisitor.getDraws()+1);
                                    clubVisitor.setPoints(clubVisitor.getPoints()+2); //ICI
                                }
                            }

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
                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "updateClub: failure", e);
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(Exception e) {
                                    Log.d(TAG, "updateClub: failure", e);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "createMatch: failure", e);
                        }
                    });
                }
            }
        });
        return root;
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
                final List<String> clubs = new ArrayList<String>();
                for(DataSnapshot clubSnapshot: dataSnapshot.getChildren()){
                    String clubName = clubSnapshot.child("nameClub").getValue(String.class);
                    clubs.add(clubName);
                }
                ArrayAdapter<String> clubAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,clubs);
                clubAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                spinner.setAdapter(clubAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Method to create a match
     * @param IdLeague
     * @param NameClubHome
     * @param NameClubVisitor
     * @param ScoreHome
     * @param ScoreVisitor
     */

   /* private void createMatch(int IdLeague, String NameClubHome, String NameClubVisitor, int ScoreHome, int ScoreVisitor) {
      viewModel.createMatch();
    }
*/


    /**
     * Method to update the database
     * @param IdLeague
     * @param NameClubHome
     * @param NameClubVisitor
     * @param ScoreHome
     * @param ScoreVisitor
     */
    /*
    private void saveChanges(int IdLeague, String NameClubHome, String NameClubVisitor, int ScoreHome, int ScoreVisitor) {

        match.setIdLeague(IdLeague);
        match.setNameClubHome(NameClubHome);
        match.setNameClubVisitor(NameClubVisitor);
        match.setScoreHome(ScoreHome);
        match.setScoreVisitor(ScoreVisitor);

        viewModel.updateMatch(match, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateMatch: success");
                setResponse(true);
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateMatch: failure", e);
                setResponse(false);
            }
        });
    }

     */

    /**
     * Set the response depending if the update succeeded
     * @param response
     */
    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(getActivity(), getString(R.string.match_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(getActivity(), getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }
}