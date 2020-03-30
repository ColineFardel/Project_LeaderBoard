package com.example.project_leaderboard.ui.match;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.ListAdapter;
import com.example.project_leaderboard.db.async.Match.CreateMatch;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.match.MatchListAdapter;
import com.example.project_leaderboard.ui.match.MatchViewModel;
import com.google.android.material.internal.NavigationMenuPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class AddMatch extends Fragment {

    private static final int CREATE_MATCH = 0;
    private static final int EDIT_MATCH = 1;
    private static final int DELETE_MATCH = 2;
    private static final String TAG = "AddMatch";

    private Toast statusToast;

    private boolean isEditable;

    private Spinner LeagueSpinner;
    private Spinner HomeSpinner;
    private Spinner VisitorSpinner;
    private MatchViewModel matchViewModel;
    private MatchListAdapter matchListAdapter;
    private ListAdapter<Match> adapterMatch;

    private MatchViewModel viewModel;
    private Application application;
    private Match match;


    private EditText ScoreHome;
    private EditText ScoreVisitor;
    private Button add, cancel;

    private List<Match> matches = new ArrayList<>();

    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    public AddMatch(Match matchEntity) {
        match = matchEntity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        initiateView(view);

        add = view.findViewById(R.id.button_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScoreHome.length() == 0) {
                    ScoreHome.setError("Please enter a score");
                    if (ScoreVisitor.length() == 0) {
                        ScoreVisitor.setError("Please enter a score");
                    }
                } else {
                    createMatch(LeagueSpinner.getId(), HomeSpinner.getSelectedItem().toString(), VisitorSpinner.getSelectedItem().toString(), ScoreHome.getId(), ScoreVisitor.getId());
                }
            }
        });

        MatchViewModel.Factory factory = new MatchViewModel.Factory(application);
        viewModel = ViewModelProviders.of(this, factory).get(MatchViewModel.class);
        viewModel.getAllMatches();
        if (match != null) {
            match = match;
            updateContent();
        }
        return view;
    }

    private void initiateView(View view) {
        isEditable = false;
        ScoreHome = view.findViewById(R.id.score_home);
        ScoreVisitor = view.findViewById(R.id.score_visitor);


        ScoreHome.setFocusable(false);
        ScoreHome.setEnabled(false);
        ScoreVisitor.setFocusable(false);
        ScoreVisitor.setEnabled(false);
    }

    private void createMatch(int IdLeague, String NameClubHome, String NameClubVisitor, int ScoreHome, int ScoreVisitor) {
        match = new Match(NameClubHome, NameClubVisitor, ScoreHome, ScoreVisitor, IdLeague);
        match.setIdLeague(IdLeague);
        match.setNameClubHome(NameClubHome);
        match.setScoreVisitor(ScoreVisitor);
        match.setNameClubVisitor(NameClubVisitor);
        match.setScoreHome(ScoreHome);
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