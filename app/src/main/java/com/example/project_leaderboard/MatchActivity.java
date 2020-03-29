package com.example.project_leaderboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_leaderboard.adapter.ListAdapter;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.match.MatchListAdapter;
import com.example.project_leaderboard.ui.match.MatchViewModel;

import java.util.ArrayList;

public class MatchActivity extends BaseActivity {

    private static final int EDIT_MATCH = 1;
    private static final int DELETE_MATCH = 2;

    private Toast toast;

    private boolean isEditable;

    private Spinner LeagueSpinner;
    private Spinner HomeSpinner;
    private Spinner VisitorSpinner;
    private MatchViewModel matchViewModel;
    private MatchListAdapter matchListAdapter;
    private ListAdapter<Match> adapterMatch;

    private MatchViewModel viewModel;

    private Match match;


    private EditText ScoreHome;
    private EditText ScoreVisitor;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationView.setCheckedItem(position);
        setContentView(R.layout.fragment_club);

    //    setupLeagueSpinner();
   //     setupClubSpinner();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
      //  matchListAdapter = new MatchListAdapter(this);
      //  recyclerView.setAdapter(matchListAdapter);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
       /* matchViewModel.getAllMatches().observe(this, new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
                matchListAdapter.setMatches(matches);
            }
        });
*/


      //  getLayoutInflater().inflate(R.layout.list_match, frameLayout);
      //  setContentView(R.layout.list_match);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_MATCH) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_edit_white_24dp);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_MATCH) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
               viewModel.delete(match, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(Exception e) {}
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.id.button_add), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void switchEditableMode() {
        if (!isEditable) {
            LinearLayout linearLayout = findViewById(R.id.nav_match);
            linearLayout.setVisibility(View.VISIBLE);
            LeagueSpinner.setFocusable(true);
            LeagueSpinner.setEnabled(true);
            LeagueSpinner.setFocusableInTouchMode(true);

            HomeSpinner.setFocusable(true);
            HomeSpinner.setEnabled(true);
            HomeSpinner.setFocusableInTouchMode(true);

            VisitorSpinner.setFocusable(true);
            VisitorSpinner.setEnabled(true);
            VisitorSpinner.setFocusableInTouchMode(true);

            ScoreVisitor.setFocusable(true);
            ScoreVisitor.setEnabled(true);
            ScoreVisitor.setFocusableInTouchMode(true);
            ScoreVisitor.requestFocus();


            ScoreHome.setFocusable(true);
            ScoreHome.setEnabled(true);
            ScoreHome.setFocusableInTouchMode(true);
            ScoreHome.requestFocus();
        } else {
            saveChanges(
                    LeagueSpinner.getId(),
                    HomeSpinner.getId(),
                    VisitorSpinner.getId(),
                    ScoreHome.getId(),
                    ScoreVisitor.getId()
            );
            LinearLayout linearLayout = findViewById(R.id.nav_match);
            linearLayout.setVisibility(View.GONE);
            LeagueSpinner.setFocusable(false);
            LeagueSpinner.setEnabled(false);
            HomeSpinner.setFocusable(false);
            HomeSpinner.setEnabled(false);
            VisitorSpinner.setFocusable(false);
            VisitorSpinner.setEnabled(false);
            ScoreVisitor.setFocusable(false);
            ScoreVisitor.setEnabled(false);
            ScoreHome.setFocusable(false);
            ScoreHome.setEnabled(false);
        }
        isEditable = !isEditable;
    }


    private void saveChanges(int LeagueSpinner, int HomeSpinner, int VisitorSpinner, int ScoreHome, int ScoreVisitor) {
        LeagueSpinner = R.id.league_spinner;
        HomeSpinner = R.id.home_spinner;
        VisitorSpinner= R.id.visitor_spinner;
        ScoreHome= R.id.score_home;
        ScoreVisitor=R.id.score_visitor;

        match.setScoreVisitor(ScoreVisitor);
        match.setScoreHome(ScoreHome);
        match.setIdClubHome(HomeSpinner);
        match.setScoreVisitor(VisitorSpinner);
        match.setIdLeague(LeagueSpinner);


        viewModel.update(match, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
        }
    }

    private void setupLeagueSpinner() {
        LeagueSpinner = findViewById(R.id.league_spinner);
        adapterMatch = new ListAdapter<>(this, R.layout.list_match,new ArrayList<>());
        LeagueSpinner.setAdapter(adapterMatch);
    }

    private void setupHomeSpinner() {
        HomeSpinner = findViewById(R.id.home_spinner);
        adapterMatch = new ListAdapter<>(this,R.layout.list_match,new ArrayList<>());
        HomeSpinner.setAdapter(adapterMatch);

    }

    private void setupVisitorSpinner() {
    VisitorSpinner = findViewById(R.id.visitor_spinner);
    adapterMatch = new ListAdapter<>(this,R.layout.list_match,new ArrayList<>());
    VisitorSpinner.setAdapter(adapterMatch);
    }

    private void updateContent() {
        if (match != null) {
            ScoreHome.setText(match.getScoreHome());
            ScoreVisitor.setText(match.getScoreVisitor());
        }
    }


}
