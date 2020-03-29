package com.example.project_leaderboard.ui.club;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.settings.SharedPref;

public class ModifyClub extends AppCompatActivity {
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //load the night mode or the normal mode
        sharedPref = new SharedPref(this);
        if(sharedPref.loadNightMode()==true){
            setTheme(R.style.NightTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_modify_club);

        //Get the arguments from last activity
        String value = getIntent().getExtras().getString("ClubName");

        //Set the edit text with the name of the club
        EditText editText = findViewById(R.id.modify_club_name);
        editText.setText(value);

        //Setting the action for cancel button
        Button cancel_button = findViewById(R.id.cancel_modify_club);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Setting the action for save button
        Button save_button = findViewById(R.id.save_modify_club);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the club in the database
            }
        });

        //customized spinner
        Spinner colorspinner = findViewById(R.id.league_spinner_modify);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        colorspinner.setAdapter(adapter);






    }
}
