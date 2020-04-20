package com.example.project_leaderboard.ui.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.club.ClubViewModel;
import com.example.project_leaderboard.ui.league.LeagueListViewModel;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

/**
 * This class is used to modify a match
 * @author Coline Fardel
 */
public class ModifyMatch extends AppCompatActivity {

    SharedPref sharedPref;
    MatchViewModel matchViewModel;
    Match match;
    private Toast statusToast;
    MatchViewModel matchListViewModel;
    DatabaseReference databaseReference;
    MatchListViewModel viewModel;
    private String matchId;
    private String leagueId;
    private String clubId;
    private EditText scoreHome,scoreVisitor;
    private TextView leagueName, clubHome, clubVisitor;
    private LeagueListViewModel leagueviewModel;
    private List<League> listLeagues;
    int ScoreHome, ScoreVisitor;



    private static final String TAG = "ModifyMatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Getting the id of the match
         */
        Intent i = getIntent();
        matchId = i.getStringExtra("matchId");
        leagueId = i.getStringExtra("leagueId");
        scoreVisitor = findViewById(R.id.score_visitor);
        scoreHome = findViewById(R.id.score_home);

        MatchViewModel.Factory factory = new MatchViewModel.Factory(this.getApplication(),leagueId);
        matchViewModel = new ViewModelProvider(this,factory).get(MatchViewModel.class);
        matchViewModel.getMatch( matchId).observe(this, matchEntity -> {
            if(matchEntity!=null) {
                match = matchEntity;
                ScoreVisitor = match.getScoreVisitor();
                ScoreHome = match.getScoreHome();

                //Ici faut cast le int en string sinon tu peux pas set text
                scoreVisitor.setText(Integer.toString(ScoreVisitor));
                scoreHome.setText(Integer.toString(ScoreHome));
            }
        });


        /**
         * Loading the language from the preferences
         */
        sharedPref = new SharedPref(this);
        String languageToLoad = sharedPref.getLanguage();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        DisplayMetrics dm= getResources().getDisplayMetrics();
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, dm);

        /**
         * Loading the Night mode from the preferences
         */
        if(sharedPref.loadNightMode()==true){
            setTheme(R.style.NightTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_modify_match);

        leagueName = findViewById(R.id.league_modify_match2);
        clubHome = findViewById(R.id.club_home);
        clubVisitor = findViewById(R.id.club_visitor);
        scoreHome = findViewById(R.id.score_home);
        scoreVisitor = findViewById(R.id.score_visitor);


        /**
         * Creating a list of leagues in order to get the id of the league chosen in the spinner
         */
        LeagueListViewModel.Factory fac = new LeagueListViewModel.Factory(this.getApplication());
        leagueviewModel = new ViewModelProvider(this,fac).get(LeagueListViewModel.class);
        leagueviewModel.getAllLeagues().observe(this,league -> {
            if(league!=null){
                listLeagues = league;
                leagueName.setText(getNameOfChosenLeague(leagueId,listLeagues));
            }
        });


        /**
         * Creating a list of leagues in order to get the id of the league chosen in the spinner
         */

        //Intent intent = getIntent();;
        //matchId = intent.getStringExtra("MatchId");

        MatchViewModel.Factory fact = new MatchViewModel.Factory(this.getApplication(),leagueId);
        matchViewModel = new ViewModelProvider(this,fact).get(MatchViewModel.class);
        matchViewModel.getMatch(matchId).observe(this,matchEntity -> {
            if(matchEntity!=null){
                match = matchEntity;
              // clubVisitor.setText(club_visitor);
             //  clubHome.setText(club_home);
             //  scoreVisitor.setText(match.getScoreVisitor());
            //   scoreHome.setText(match.getScoreHome());
                clubHome.setText(matchId);
            }
        });


        /**
         * Customized spinners
         */
        /*
        Spinner leaguespinner = findViewById(R.id.league_spinner_modify_match);
        String[] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_layout, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        leaguespinner.setAdapter(adapter);

        Spinner clubhome = findViewById(R.id.home_spinner_modify_match);
        String[] list2 = getResources().getStringArray(R.array.clubs_premierLeague); //changer par rapport au choix du premier spinner !!!
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_dropdown_layout, list2);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        clubhome.setAdapter(adapter2);

        Spinner clubvisitor = findViewById(R.id.visitor_spinner_modify_match);
        clubvisitor.setAdapter(adapter2);
*/
        /**
         * Setting the action for cancel button
         */
        Button cancel_button = findViewById(R.id.button_cancel_modifymatch);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * Setting the action for save button
         */
        Button add_button = findViewById(R.id.button_save_modifymatch);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the match in the database
                match.setScoreVisitor(ScoreVisitor);
                match.setScoreHome(ScoreHome);
                matchViewModel.updateMatch(match, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "updateMatch: success");
                        setResponse(true);
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "updateMatch: failure",e);
                        setResponse(false);
                    }
                });
            }
        });
    }

    private String getNameOfChosenLeague(String leagueId, List<League> list){
        for(League league : list){
            if(league.getLeagueId().equals(leagueId))
                return league.getLeagueName();
        }
        return "";
    }

    /**
     * Set the response depending if the update succeeded
     * @param response
     */
    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(getApplicationContext(), getString(R.string.match_updated), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(getApplicationContext(), getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }
}
