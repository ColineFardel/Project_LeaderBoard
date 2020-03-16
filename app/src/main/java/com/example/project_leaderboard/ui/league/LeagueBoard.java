package com.example.project_leaderboard.ui.league;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project_leaderboard.MainActivity;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.club.ClubFragment;
import com.example.project_leaderboard.ui.match.MatchsOfClub;
import com.google.android.material.navigation.NavigationView;

public class LeagueBoard extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    String[] clubs;
    ImageButton imageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderborad_activity);

        //Set the button to open a new fragment
        imageButton = findViewById(R.id.button_add);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                ClubFragment fragment = new ClubFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.leader_clubs,fragment).commit();
            }
        });

        //Get the info from the last fragment
        Intent intent = getIntent();
        int array = intent.getIntExtra(LeagueFragment.EXTRA_ID_ARRAY,0);
        String text = intent.getStringExtra(LeagueFragment.EXTRA_TEXT);
        clubs = getResources().getStringArray(array);
        ListView listView = findViewById(R.id.leaderboard_clubs);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                clubs);

        listView.setAdapter(listViewAdapter);

        TextView textView = findViewById(R.id.league_name);
        textView.setText(text);

        //set the listener for the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                i = new Intent(view.getContext(), MatchsOfClub.class);
                startActivity(i);
            }
        });
        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_league, R.id.nav_club, R.id.nav_favorites,
                R.id.nav_settings, R.id.nav_match)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        */

    }
}
