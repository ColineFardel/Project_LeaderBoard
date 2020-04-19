package com.example.project_leaderboard.ui.club;

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
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

/**
 * This class is used to modify a club
 * @author Coline Fardel
 */
public class ModifyClub extends AppCompatActivity {
    private SharedPref sharedPref;
    private static final String TAG = "ModifyClub";
    private ClubViewModel viewModel;
    private Club club;
    Toast statusToast;
    DatabaseReference databaseReference;
    String clubId;

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

        /**
         * Getting the id of the club from last acitivty
         */

        String value = getIntent().getExtras().getString("ClubId");
        EditText editText = findViewById(R.id.modify_club_name);
        editText.setText(value);

        databaseReference = FirebaseDatabase.getInstance().getReference("Club");
        clubId = club.getClubId();
        String value = getIntent().getExtras().getString(clubId);
        EditText editText = findViewById(R.id.modify_club_name);
        editText.setText(value);

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
                viewModel.updateClub(club, new OnAsyncEventListener() {
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

        /**
         * Customized spinner
         */
        Spinner colorspinner = findViewById(R.id.league_spinner_modify);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        colorspinner.setAdapter(adapter);

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
