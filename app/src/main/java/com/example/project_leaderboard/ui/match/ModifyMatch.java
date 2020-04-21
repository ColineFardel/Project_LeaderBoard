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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.club.ClubListViewModel;
import com.example.project_leaderboard.ui.club.ClubViewModel;
import com.example.project_leaderboard.ui.league.LeagueListViewModel;
import com.example.project_leaderboard.ui.settings.SharedPref;

import java.util.List;
import java.util.Locale;

/**
 * This class is used to modify a match
 * @author Coline Fardel
 */
public class ModifyMatch extends AppCompatActivity {

    private static final String TAG = "ModifyMatch";

    private SharedPref sharedPref;

    private MatchViewModel matchViewModel;
    private MatchListViewModel matchListViewModel;
    private ClubViewModel clubViewModel;

    private EditText scoreHome,scoreVisitor;
    private TextView leagueTextView, clubHomeTextView, clubVisitorTextView;

    private List<Match> matches;

    private Club clubHome;
    private Club clubVisitor;
    private Match match;

    private String clubHomeId;
    private String clubVisitorId;
    private String matchId;






    private Club club;
    private Club clubsHome;
    private Club clubsVisitor;
    private Toast statusToast;

    private String leagueId;
    private String clubId;


    private LeagueListViewModel leagueviewModel;
    private List<League> listLeagues;
    int ScoreHome , ScoreVisitor;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        leagueTextView = findViewById(R.id.league_modify_match2);
        clubHomeTextView = findViewById(R.id.club_home);
        clubVisitorTextView = findViewById(R.id.club_visitor);
        scoreHome = findViewById(R.id.score_home);
        scoreVisitor = findViewById(R.id.score_visitor);

        /**
         * Getting the id of the match and the league
         */
        Intent i = getIntent();
        matchId = i.getStringExtra("matchId");
        leagueId = i.getStringExtra("leagueId");
        scoreVisitor = findViewById(R.id.score_visitor);
        scoreHome = findViewById(R.id.score_home);

        /**
         * Get the match to modify
         */
        ClubViewModel.Factory factory = new ClubViewModel.Factory(this.getApplication(),leagueId);
        clubViewModel = new ViewModelProvider(this,factory).get(ClubViewModel.class);
        clubViewModel.getClub( leagueId,clubId).observe(this, clubEntity -> {
            if(clubEntity!=null)
                club = clubEntity;
        });


        MatchListViewModel.Factory factory2 = new MatchListViewModel.Factory(this.getApplication(),leagueId);
        matchListViewModel = new ViewModelProvider(this,factory2).get(MatchListViewModel.class);
        matchListViewModel.getMatches().observe(this, matches -> {
            if(matches!=null){
                this.matches = matches;
            }
        });

        MatchViewModel.Factory fa = new MatchViewModel.Factory(this.getApplication(), leagueId);
        matchViewModel = new ViewModelProvider(this, fa).get(MatchViewModel.class);
        matchViewModel.getMatch(matchId,leagueId).observe(this, match1 -> {
            match = match1;
            if(match1!=null){
                match = match1;
            }
        });

        /*
        MatchViewModel.Factory factory = new MatchViewModel.Factory(this.getApplication(),leagueId);
        matchViewModel = new ViewModelProvider(this,factory).get(MatchViewModel.class);
        matchViewModel.getMatch(matchId).observe(this, matchEntity -> {
            match = matchEntity;

            if(matchEntity!=null) {
                match = matchEntity;

                clubHomeId = match.getIdClubHome();
                clubVisitorId = match.getIdClubVisitor();

                //ScoreVisitor = match.getScoreVisitor();
                //ScoreHome = match.getScoreHome();
                //clubsHome = filterClubsByHome(club);
                //clubsVisitor = filterClubsByVisitor(club);

                scoreVisitor.setText(Integer.toString(match.getScoreVisitor()));
                scoreHome.setText(Integer.toString(match.getScoreHome()));
            }
        });

         */

        /**
         * Get the home club
         */
        ClubViewModel.Factory fac = new ClubViewModel.Factory(this.getApplication(), leagueId);
        clubViewModel = new ViewModelProvider(this, fac).get(ClubViewModel.class);
        clubViewModel.getClub(leagueId,clubHomeId).observe(this, club -> {
            if (club != null) {
                clubHome = club;
            }
        });

        /**
         * Get the visitor club
         */
        clubViewModel.getClub(leagueId,clubVisitorId).observe(this, club -> {
            if (club != null) {
                clubVisitor = club;
            }
        });

        /**
         * Populate the list of clubs to have the names to display
         */
        /*
        ClubViewModel.Factory fac = new ClubViewModel.Factory(this.getApplication(), leagueId);
        clubViewModel = new ViewModelProvider(this, fac).get(ClubViewModel.class);
        clubViewModel.getClub(leagueId,clubId).observe(this, clubEntities -> {
            if (clubEntities != null) {
                clubs = clubEntities;
            }
        });

         */





        /**
         * Creating a list of leagues in order to get the id of the league chosen in the spinner
         */
        LeagueListViewModel.Factory factory1 = new LeagueListViewModel.Factory(this.getApplication());
        leagueviewModel = new ViewModelProvider(this,factory1).get(LeagueListViewModel.class);
        leagueviewModel.getAllLeagues().observe(this,league -> {
            if(league!=null){
                listLeagues = league;
                leagueTextView.setText(getNameOfChosenLeague(leagueId,listLeagues));
            }
        });





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
                ScoreVisitor= match.getScoreVisitor();
                ScoreHome= match.getScoreHome();
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
     * Create a list of home clubs
     * @param club
     * @return
     */
    public Club filterClubsByHome(Club club){
        if(club.getClubId().equals(match.getIdClubHome())){
            match.setIdClubHome(club.getNameClub());
        }
        return club;
    }

    /**
     * Create a list of visitor clubs
     * @param club
     * @return
     */
    public Club filterClubsByVisitor(Club club){
        if(club.getClubId().equals(match.getIdClubVisitor())){
            match.setIdClubVisitor(club.getNameClub());
        }

        return club;
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
