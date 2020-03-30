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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_leaderboard.MainActivity;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.ListAdapter;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.ArrayList;

/**
 * This class is used to add a new match to the database
 */
public class MatchFragment extends Fragment {

    private static final String TAG = "AddMatch";
    private MatchViewModel viewModel;
    private MatchListAdapter matchListAdapter;
    private Application application;
    private Match match;
    private boolean isEditable;
    private Toast statusToast;
    private OnAsyncEventListener callback;
    Button add;
    EditText ScoreHome, ScoreVisitor;
    Spinner LeagueSpinner,HomeSpinner,VisitorSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_match,container,false);
        initiateView(root);

       /* matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.getAllMatches().observe(getViewLifecycleOwner(), new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
                matchListAdapter.setMatches(matches);
            }
        });
*/      /*
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
        //Colored Spinner
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

        //Setting the action for cancel button
        Button cancel_button = root.findViewById(R.id.button_cancel_addmatch);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Setting the action for add button
        Button add_button = root.findViewById(R.id.button_add_addmatch);
      //  ScoreHome = root.findViewById(R.id.score_home);
      //  ScoreVisitor= root.findViewById(R.id.score_visitor);
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

    private void createMatch(int IdLeague, String NameClubHome, String NameClubVisitor, int ScoreHome, int ScoreVisitor) {
        match = new Match(NameClubHome, NameClubVisitor, ScoreHome, ScoreVisitor, IdLeague);
        match.setIdLeague(IdLeague);
        match.setNameClubHome(NameClubHome);
        match.setScoreVisitor(ScoreVisitor);
        match.setNameClubVisitor(NameClubVisitor);
        match.setScoreHome(ScoreHome);
    }


    private void initiateView(View view) {
        isEditable = false;
        ScoreHome = view.findViewById(R.id.score_home);
        ScoreVisitor = view.findViewById(R.id.score_visitor);


        ScoreHome.setFocusable(true);
        ScoreHome.setEnabled(true);
        ScoreVisitor.setFocusable(true);
        ScoreVisitor.setEnabled(true);
    }

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

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(getActivity(), getString(R.string.match_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(getActivity(), getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }
   /* private void setupLeagueSpinner() {
        LeagueSpinner = view.findViewById(R.id.league_spinner_modify_match);
        LeagueSpinner = new ListAdapter<>(this, R.layout.list_match, new ArrayList<>());
        spinnerFromAccount.setAdapter(adapterFromAccount);
    }
*/
    private void updateContent() {
        if (match != null) {
            //  HomeSpinner.getSelectedItem().toString() = match.getNameClubHome();
            //   VisitorSpinner.getSelectedItem().toString(match.getNameClubVisitor());
            // LeagueSpinner.getSelectedItem(match.getIdLeague());
            ScoreHome.setText(match.getScoreHome());
            ScoreVisitor.setText(match.getScoreVisitor());
        }
    }


}