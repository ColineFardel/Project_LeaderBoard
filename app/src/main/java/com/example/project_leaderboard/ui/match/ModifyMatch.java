package com.example.project_leaderboard.ui.match;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

/**
 * This class is used to modify a match
 * @author Coline Fardel
 */
public class ModifyMatch extends AppCompatActivity {

    SharedPref sharedPref;
    MatchViewModel viewModel;
    Match match;
    private Toast statusToast;
    DatabaseReference databaseReference;
    private String matchId;
    private EditText scoreHome,scoreVisitor;
    int score_home, score_visitor;


    private static final String TAG = "ModifyMatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Getting the id of the match
         */
        databaseReference = FirebaseDatabase.getInstance().getReference("Match");
        matchId = match.getMatchId();
        score_home = match.getScoreHome();
        score_visitor = match.getScoreVisitor();
        String value = getIntent().getExtras().getString(matchId);
        scoreHome = findViewById(R.id.score_home);
        scoreVisitor = findViewById(R.id.score_visitor);
        scoreHome.setText(score_home);
        scoreVisitor.setText(score_visitor);



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

        /**
         * Customized spinners
         */
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
                //save the club in the database
                viewModel.updateMatch(match, new OnAsyncEventListener() {
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
