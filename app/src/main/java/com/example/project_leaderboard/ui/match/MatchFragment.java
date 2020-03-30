package com.example.project_leaderboard.ui.match;

import android.app.Application;
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

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

/**
 * This class is used to add a new match to the database
 */
public class MatchFragment extends Fragment {

    private MatchViewModel matchViewModel;
    private MatchListAdapter matchListAdapter;
    private Application application;
    private Match match;
    private OnAsyncEventListener callback;
    Button add;
    EditText ScoreHome, ScoreVisitor;

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
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add the club in the database
            }
        });

        return root;
    }
/*
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                //Intent myIntent = new Intent(getActivity(),MatchActivity.class);
             //   startActivity(myIntent);
                matchViewModel.insert(match);
                break;
            case R.id.button3 :
                matchViewModel.delete(match, callback);
        }
    }
*/
}