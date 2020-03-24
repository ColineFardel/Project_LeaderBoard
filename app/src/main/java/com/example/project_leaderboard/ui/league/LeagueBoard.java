package com.example.project_leaderboard.ui.league;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project_leaderboard.MainActivity;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.club.ClubFragment;

public class LeagueBoard extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    String[] clubs;
    ImageButton imageButton;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderborad_activity);

        sharedPref = new SharedPref(this);
        if(sharedPref.loadNightMode()==true){
            setTheme(R.style.NightTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_league, R.id.nav_club, R.id.nav_favorites,
                R.id.nav_settings, R.id.nav_match)
                .setDrawerLayout(drawer)
                .build();

         */
        /*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        */



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

        //put data
        String points[] = {"23","12","9","2","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
        String wins[] = {"23","12","9","2","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
        String draws[] = {"23","12","9","2","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
        String loses[] = {"23","12","9","2","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};

        MyAdapter listViewAdapter = new MyAdapter(this, clubs,draws,wins,loses,points);


        listView.setAdapter(listViewAdapter);

        TextView textView = findViewById(R.id.league_name);
        textView.setText(text);

    }
    //Create the adapter for the list view
    class MyAdapter extends ArrayAdapter{
        Context context;
        String name[];
        String draws[];
        String wins[];
        String loses[];
        String points[];

        MyAdapter (Context c, String name[], String draws[],String wins[],String loses[], String points[]){
            super(c,R.layout.row,R.id.name, name);
            this.context=c;
            this.name=name;
            this.points=points;
            this.wins=wins;
            this.draws=draws;
            this.loses=loses;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView myName = row.findViewById(R.id.name);
            TextView myPoints = row.findViewById(R.id.points);
            TextView myWins = row.findViewById(R.id.wins);
            TextView myDraws = row.findViewById(R.id.draws);
            TextView myLoses = row.findViewById(R.id.loses);

            myName.setText(name[position]);
            myPoints.setText(points[position]);
            myWins.setText(wins[position]);
            myDraws.setText(draws[position]);
            myLoses.setText(loses[position]);

            return row;
        }
    }
}