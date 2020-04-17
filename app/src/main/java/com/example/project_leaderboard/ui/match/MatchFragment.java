package com.example.project_leaderboard.ui.match;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_leaderboard.MainActivity;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.ListAdapter;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
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

    private static final String TAG = "AddMatch";
    private Match match;
    private boolean isEditable;
    private Toast statusToast;
    EditText ScoreHome, ScoreVisitor;
    private MatchViewModel viewModel;
    DatabaseReference databaseReference;




    //private OnAsyncEventListener callback;
    //private Application application;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_match,container,false);

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


        // Get the LeagueName from firebase in the spinner
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("League").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> leagues = new ArrayList<String>();
                for(DataSnapshot leagueSnapshot: dataSnapshot.getChildren()){
                    String leagueName = leagueSnapshot.child("LeagueName").getValue(String.class);
                    leagues.add(leagueName);
                }
                Spinner spinner = (Spinner) root.findViewById(R.id.league_spinner_modify_match);
                ArrayAdapter<String> leagueAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,leagues);
                leagueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(leagueAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // Get the Clubs from firebase in the spinner
        databaseReference.child("Club").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> clubs = new ArrayList<String>();
                for(DataSnapshot clubSnapshot: dataSnapshot.getChildren()){
                    String clubName = clubSnapshot.child("NameClub").getValue(String.class);
                    clubs.add(clubName);
                }
                Spinner spinner = (Spinner) root.findViewById(R.id.home_spinner_modify_match);
                ArrayAdapter<String> clubAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,clubs);
                clubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(clubAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        /**
         * Customized spinners
         */
        Spinner leaguespinner = root.findViewById(R.id.league_spinner_modify_match);
        String[] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_dropdown_layout, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        leaguespinner.setAdapter(adapter);

        Spinner clubhome = root.findViewById(R.id.home_spinner_modify_match);
        String[] list2 = getResources().getStringArray(R.array.clubs_premierLeague); //changer par rapport au choix du premier spinner !!!
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.spinner_dropdown_layout, list2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        clubhome.setAdapter(adapter2);

        Spinner clubvisitor = root.findViewById(R.id.visitor_spinner_modify_match);
        clubvisitor.setAdapter(adapter2);

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
                   // createMatch(LeagueSpinner.getId(), HomeSpinner.getSelectedItem().toString(), VisitorSpinner.getSelectedItem().toString(), ScoreHome.getId(), ScoreVisitor.getId());
                    Intent i = new Intent(getActivity(), MatchsOfClub.class);
                    startActivity(i);
                }
            }
        });
        return root;
    }

    public void HomeSpinner(){

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