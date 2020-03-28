package com.example.project_leaderboard.ui.club;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
        String value = getIntent().getExtras().getString("ClubName");

        TextView textView = findViewById(R.id.modify_clubName);
        TextView textView2 = findViewById(R.id.modify_clubLeague);
        EditText editText = findViewById(R.id.modify_club_name);
        editText.setText(value);

        //customized spinner
        Spinner colorspinner = findViewById(R.id.league_spinner_modify);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        colorspinner.setAdapter(adapter);






    }
}
