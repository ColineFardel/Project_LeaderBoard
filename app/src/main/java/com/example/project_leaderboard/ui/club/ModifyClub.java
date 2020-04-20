package com.example.project_leaderboard.ui.club;

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
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.league.LeagueListViewModel;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.Locale;

/**
 * This class is used to modify a club
 * @author Coline Fardel
 */
public class ModifyClub extends AppCompatActivity {
    private static final String TAG = "ModifyClub";
    private SharedPref sharedPref;

    private String clubId;
    private String leagueId;
    private EditText clubName;
    private TextView leagueName;

    private ClubViewModel clubViewModel;
    private Club club;
    private List<League> listLeagues;
    private LeagueListViewModel leagueviewModel;

    private Toast statusToast;
    private DatabaseReference databaseReference;

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

        setContentView(R.layout.activity_modify_club);

        clubName = findViewById(R.id.modify_club_name);
        leagueName = findViewById(R.id.leagueName);

        /**
         * Get the club from firebase with the id from last activity
         */
        Intent i = getIntent();
        clubId = i.getStringExtra("clubId");
        leagueId = i.getStringExtra("leagueId");

        ClubViewModel.Factory factory = new ClubViewModel.Factory(this.getApplication(),leagueId);
        clubViewModel = new ViewModelProvider(this,factory).get(ClubViewModel.class);
        clubViewModel.getClub( leagueId,clubId).observe(this, clubEntity -> {
            if(clubEntity!=null)
                club = clubEntity;
            clubName.setText(club.getNameClub());
            club.setLeagueId(leagueId);
        });

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
         * Setting the action for cancel button
         */
        Button cancel_button = findViewById(R.id.cancel_modify_club);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * Setting the action for save button
         */
        Button save_button = findViewById(R.id.save_modify_club);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the club in the database
                club.setNameClub(clubName.getText().toString().trim());
                clubViewModel.updateClub(club, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "updateClub: success");
                        setResponse(true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "updateClub: failure",e);
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
            statusToast = Toast.makeText(getApplicationContext(), getString(R.string.club_updated), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(getApplicationContext(), getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }
}
