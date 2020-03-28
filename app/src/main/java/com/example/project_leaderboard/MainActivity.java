package com.example.project_leaderboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.ui.match.AddMatchViewModel;
import com.example.project_leaderboard.ui.match.MatchListAdapter;
import com.example.project_leaderboard.ui.match.MatchViewModel;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPref sharedPref;
    //  private AddMatchViewModel matchViewModel;
    //   private MatchListAdapter matchListAdapter;
    Intent intent;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = new SharedPref(this);
        if(sharedPref.loadNightMode()==true){
            setTheme(R.style.NightTheme);
            //int grey = Color.parseColor("#FF303030");
            //navigationView.setBackgroundColor(grey);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main);

       /* RecyclerView recyclerView = findViewById(R.id.recyclerview);
        matchListAdapter = new MatchListAdapter(this);
        recyclerView.setAdapter(matchListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchViewModel = ViewModelProviders.of(this).get(AddMatchViewModel.class);
        matchViewModel.getAllMatches().observe(this, new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
            matchListAdapter.setMatches(matches);
            }
        });

*/
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void toastMsg (String msg){
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.show();
    }
    public void displayToastmsg(View v){
        toastMsg("Match successfully added");
    }


}
