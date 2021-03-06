package com.example.project_leaderboard.ui.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.adapter.MatchModel;
import com.example.project_leaderboard.adapter.MatchRecyclerAdapter;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;
import com.example.project_leaderboard.ui.club.ClubListViewModel;
import com.example.project_leaderboard.ui.club.ClubViewModel;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class is used to display matches of clubs
 * @author Coline Fardel
 */

public class MatchsOfClub extends AppCompatActivity {
    private static final String TAG = "MatchsOfClub";

    private ClubViewModel clubViewModel;
    private ClubListViewModel clubListViewModel;
    private MatchListViewModel matchListViewModel;
    private MatchRecyclerAdapter matchRecyclerAdapter;

    private TextView clubName;

    private List<Match> matches;
    private List<Club> allClubs;
    private List<Club> clubsHome;
    private List<Club> clubsVisitor;
    private List<Club> clubsHomeToUpdate;
    private List<Club> clubsVisitorToUpdate;
    private List<MatchModel> matchModelList;

    private String leagueId;
    private String clubId;

    private ImageButton deleteButton;
    private FloatingActionButton addFloatButton;

    private SharedPref sharedPref;
    private Toast statusToast;

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
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, dm);

        /**
         * Loading the Night mode from the preferences
         */
        if (sharedPref.loadNightMode() == true) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_matchs_of_club);

        clubName = findViewById(R.id.club_name);

        /**
         * Getting the id of the club and the id of the league to set the title and get the matches from database
         */
        Intent i = getIntent();
        clubId = i.getStringExtra("Club");
        leagueId = i.getStringExtra("League");

        ClubViewModel.Factory factory = new ClubViewModel.Factory(this.getApplication(),leagueId);
        clubViewModel = new ViewModelProvider(this,factory).get(ClubViewModel.class);
        clubViewModel.getClub( leagueId,clubId).observe(this, club -> {
            if(club!=null)
                clubName.setText(club.getNameClub());
        });

        /**
         * Firebase
         */
        RecyclerView recyclerView = findViewById(R.id.matchsrecyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        matches = new ArrayList<>();

        /**
         * Populate the list of clubs to have the names to display
         */
        ClubListViewModel.Factory fac = new ClubListViewModel.Factory(getApplication(), leagueId);
        clubListViewModel = new ViewModelProvider(this, fac).get(ClubListViewModel.class);
        clubListViewModel.getClubsByLeague(leagueId).observe(this, clubEntities -> {
            if (clubEntities != null) {
                allClubs = clubEntities;
            }
        });

        /**
         * Put the list of matches in the adapter to display it
         */
        MatchListViewModel.Factory fac1 = new MatchListViewModel.Factory(getApplication(),leagueId);
        matchListViewModel = new ViewModelProvider(this,fac1).get(MatchListViewModel.class);
        matchListViewModel.getMatches().observe(this, matchesEntities -> {
            if(matchesEntities!=null){
                matches = matchesEntities;
                matches = filterMatches(matches,clubId);

                clubsHome = filterClubsByHome(allClubs,matches);
                clubsVisitor = filterClubsByVisitor(allClubs,matches);

                matchRecyclerAdapter.setMatchData(matches);
                matchRecyclerAdapter.setMatchModelList(getListData());
                matchRecyclerAdapter.setListClubs(clubsHome,clubsVisitor);
            }
            else {
                matchRecyclerAdapter.setMatchData(matches);
                matchRecyclerAdapter.setMatchModelList(getListData());
            }
        });

        /**
         * Setting the button to delete a match
         */
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(matchRecyclerAdapter.getNumberSelected()<1){
                    String msg = getString(R.string.notEnoughItems);
                    Toast statusToast = Toast.makeText(MatchsOfClub.this, msg, Toast.LENGTH_LONG);
                    statusToast.show();
                }
                else{
                    List<Match> matchesToDelete = matchRecyclerAdapter.getSelectedMatches();

                    clubsVisitorToUpdate = clubsVisitor;
                    clubsHomeToUpdate = clubsHome;

                    clubsHomeToUpdate = filterClubsByHome(clubsHomeToUpdate ,matchesToDelete);
                    clubsVisitorToUpdate = filterClubsByVisitor(clubsVisitorToUpdate ,matchesToDelete);

                    clubsHomeToUpdate = setClubsHomeValues(clubsHomeToUpdate, matchesToDelete);
                    clubsVisitorToUpdate = setClubsVisitorValues(clubsVisitorToUpdate, matchesToDelete);

                    matchListViewModel.deleteMatches(matchesToDelete, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "deleteMatches: success");
                            clubListViewModel.updateClubs(clubsVisitorToUpdate, new OnAsyncEventListener() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "updateClubs: success");
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                            clubListViewModel.updateClubs(clubsHomeToUpdate, new OnAsyncEventListener() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "updateClubs: success");
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "deleteClubs: failure",e);
                            setResponse(false);
                        }
                    });
                }
            }
        });

        /**
         * Setting the button to open the add match fragment
         */
        addFloatButton = findViewById(R.id.floatAddButton);
        addFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                MatchFragment fragment = new MatchFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.matchs_of_club, fragment).commit();
                addFloatButton.hide();
            }
        });

        /**
         * Open new activity to show the matches of the club selected
         */

        matchRecyclerAdapter = new MatchRecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on:" + matches.get(position).toString());
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(matchRecyclerAdapter);
    }

    /**
     * Method to set the points in the home clubs wich matches are deleted
     * @param clubs
     * @param matches
     * @return
     */
    public List<Club> setClubsVisitorValues(List<Club> clubs, List<Match> matches){
        for(int i =0; i<matches.size();i++){
            if(matches.get(i).getScoreHome()<matches.get(i).getScoreVisitor()){
                clubs.get(i).setWins(clubs.get(i).getWins()-1);
            }
            if(matches.get(i).getScoreHome()>matches.get(i).getScoreVisitor()){
                clubs.get(i).setLosses(clubs.get(i).getLosses()-1);
            }
            if(matches.get(i).getScoreVisitor()==matches.get(i).getScoreHome()){
                clubs.get(i).setDraws(clubs.get(i).getDraws()-1);
            }
            clubs.get(i).setPoints();
        }
        return clubs;
    }

    /**
     * Method to set the points in the home clubs wich matches are deleted
     * @param clubs
     * @param matches
     * @return
     */
    public List<Club> setClubsHomeValues(List<Club> clubs, List<Match> matches){
        for(int i =0; i<matches.size();i++){
            if(matches.get(i).getScoreHome()>matches.get(i).getScoreVisitor()){
                clubs.get(i).setWins(clubs.get(i).getWins()-1);
            }
            if(matches.get(i).getScoreHome()<matches.get(i).getScoreVisitor()){
                clubs.get(i).setLosses(clubs.get(i).getLosses()-1);
            }
            if(matches.get(i).getScoreVisitor()==matches.get(i).getScoreHome()){
                clubs.get(i).setDraws(clubs.get(i).getDraws()-1);
            }
            clubs.get(i).setPoints();
        }
        return clubs;
    }

    /**
     * Create a list of home clubs
     * @param allClubs
     * @param matches
     * @return
     */
    public List<Club> filterClubsByHome(List<Club> allClubs, List<Match> matches){
        List<Club> filteredClubs = new ArrayList<>();
        for(Match match : matches){
            for(Club club : allClubs){
                if(club.getClubId().equals(match.getIdClubHome())){
                    filteredClubs.add(club);
                }
            }
        }
        return filteredClubs;
    }

    /**
     * Create a list of visitor clubs
     * @param allClubs
     * @param matches
     * @return
     */
    public List<Club> filterClubsByVisitor(List<Club> allClubs, List<Match> matches){
        List<Club> filteredClubs = new ArrayList<>();
        for(Match match : matches){
            for(Club club : allClubs){
                if(club.getClubId().equals(match.getIdClubVisitor())){
                    filteredClubs.add(club);
                }
            }
        }
        return filteredClubs;
    }

    /**
     * Method to get only the matches of the club chosen
     * @param matches
     * @param clubId
     * @return
     */
    public List<Match> filterMatches(List<Match> matches, String clubId){
        List<Match> filteredMatches = new ArrayList<>();
        for(Match match : matches){
            if(match!=null){
                if(match.getIdClubHome().equals(clubId)||match.getIdClubVisitor().equals(clubId)){
                    filteredMatches.add(match);
                }
            }
        }
        return filteredMatches;
    }

    /**
     * Method to show the user if the action succeeded or not
     * @param response
     */
    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, getString(R.string.club_deleted), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    /**
     * Method to populate the model list
     * @return the model list
     */
    public List<MatchModel> getListData(){
        matchModelList = new ArrayList<>();
        for(Match match : matches){
            matchModelList.add(new MatchModel(match));
        }
        return matchModelList;
    }
}