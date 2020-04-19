package com.example.project_leaderboard.ui.league;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_leaderboard.adapter.ClubRecyclerAdapter;
import com.example.project_leaderboard.adapter.ClubModel;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;
import com.example.project_leaderboard.ui.club.ClubListViewModel;
import com.example.project_leaderboard.ui.club.ModifyClub;
import com.example.project_leaderboard.ui.match.MatchsOfClub;
import com.example.project_leaderboard.ui.settings.SharedPref;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.club.ClubFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * This class is used to show all the clubs in a specific league and their points/wins/draws/loses
 * When you click on one club it will open the class MatchsOfClub to display all the matches of this club
 * When you click on the plus button it will open the class ClubFragment to create a new club
 * If you long click on one club you can modify it with the class ModifyClub
 * If you long click on multiple clubs you can delete them
 * @author Coline Fardel
 */
public class LeagueBoard extends AppCompatActivity{
    private static final String TAG = "LeagueBoard";

    private List<Club> clubs;
    private String leagueId;

    private ClubListViewModel viewModel;
    private LeagueViewModel leagueViewModel;

    private SharedPref sharedPref;
    private ImageButton modifyButton;
    private ImageButton deleteButton;
    private FloatingActionButton addFloatButton;
    private List<ClubModel> clubModelList;

    private ClubRecyclerAdapter<Club> clubRecyclerAdapter;


    /*
    private String[] clubs;


    private List<String> userSelection = new ArrayList<>();
    //private RecyclerAdapter<Club> recyclerAdapter;
    private ClubViewModel viewModel;
    //private List<Club> clubs;
    private static final String TAG = "Leaderboard Fragment";
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        setContentView(R.layout.leaderborad_activity);
        TextView leagueName = findViewById(R.id.league_name);

        /**
         * Getting the id of the league to set the title and get the clubs from database
         */
        Intent i = getIntent();
        leagueId = i.getStringExtra("leagueId");

        LeagueViewModel.Factory fac = new LeagueViewModel.Factory(this.getApplication(),leagueId);
        leagueViewModel = new ViewModelProvider(this,fac).get(LeagueViewModel.class);
        leagueViewModel.getLeague().observe(this,league -> {
            if(league!=null){
                leagueName.setText(league.getLeagueName());
            }
        });

        /**
         * Firebase
         */
        RecyclerView recyclerView = findViewById(R.id.clubsrecyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        clubs = new ArrayList<>();

        /**
         * Open new activity to show the matches of the club selected
         */
        clubRecyclerAdapter = new ClubRecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on:" + clubs.get(position).toString());
            }

            @Override
            public void onItemClick(View view, int position) {
                String clubId = clubs.get(position).getClubId();
                Bundle b = new Bundle();
                b.putString("Club",clubId);
                b.putString("League",leagueId);
                Intent i = new Intent(getBaseContext(), MatchsOfClub.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        /**
         * Put the list of clubs in the adapter to display it
         */
        ClubListViewModel.Factory factory = new ClubListViewModel.Factory(getApplication(), leagueId);
        viewModel = new ViewModelProvider(this, factory).get(ClubListViewModel.class);
        viewModel.getClubsByLeague(leagueId).observe(this, clubEntities -> {
            if (clubEntities != null) {
                clubs = clubEntities;

                Collections.sort(clubs, new Comparator<Club>() {
                    @Override
                    public int compare(Club o1, Club o2) {
                        int club1Points = o1.getPoints();
                        int club2Points = o2.getPoints();

                        if(club1Points==club2Points)
                            return 0;
                        if(club1Points<club2Points)
                            return 1;
                        else
                            return -1;
                    }
                });
                clubRecyclerAdapter.setClubModelList(getListData());
                clubRecyclerAdapter.setClubData(clubs);
            }
        });

        /**
         * Setting the button to delete a club
         */
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clubRecyclerAdapter.getNumberSelected()<1){
                    String msg = getString(R.string.notEnoughItems);
                    Toast statusToast = Toast.makeText(LeagueBoard.this, msg, Toast.LENGTH_LONG);
                    statusToast.show();
                }
                else{
                    //viewModel.deleteClubs();
                    //delete
                }

            }
        });

        /**
         * Setting the button to modify a club
         */
        modifyButton = findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clubRecyclerAdapter.getNumberSelected()>1){
                    String msg = getString(R.string.tooMuchItems);
                    Toast statusToast = Toast.makeText(LeagueBoard.this, msg, Toast.LENGTH_LONG);
                    statusToast.show();
                }
                else{
                    if(clubRecyclerAdapter.getNumberSelected()<1){
                        String msg = getString(R.string.notEnoughItems);
                        Toast statusToast = Toast.makeText(LeagueBoard.this, msg, Toast.LENGTH_LONG);
                        statusToast.show();
                    }
                    else {
                        Club clubselected = clubRecyclerAdapter.getSelectedClub();
                        Bundle b = new Bundle();
                        b.putString("clubId",clubselected.getClubId());
                        Intent i;
                        i = new Intent(getBaseContext(), ModifyClub.class);
                        i.putExtras(b);
                        startActivity(i);
                    }
                }
            }
        });

        /**
         * Setting the button to open the add club fragment
         */
        addFloatButton = findViewById(R.id.floatAddButton);
        addFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                ClubFragment fragment = new ClubFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.leader_clubs, fragment).commit();
                addFloatButton.hide();
            }
        });
        /*
        modifyButton = findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = findViewById(R.id.league_name);
                String value = (String) title.getText();
                Bundle b = new Bundle();
                b.putString("League", value);
                FragmentManager fm = getSupportFragmentManager();
                ClubFragment fragment = new ClubFragment();
                fragment.setArguments(b);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.leader_clubs, fragment).commit();
            }
        });

         */

        recyclerView.setAdapter(clubRecyclerAdapter);
    }

    /**
     * Method to populate the model list
     * @return the model list
     */
    public List<ClubModel> getListData(){
        clubModelList = new ArrayList<>();
        for(Club club : clubs){
            clubModelList.add(new ClubModel(club));
        }
        return clubModelList;
    }
}