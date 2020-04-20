package com.example.project_leaderboard;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project_leaderboard.adapter.SearchAdapter;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
/**
 * This is the main class of the project
 * @author Coline Fardel
 */
public class MainActivity extends AppCompatActivity {

    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Club> list;
    SearchView searchView;



    private AppBarConfiguration mAppBarConfiguration;
    private SharedPref sharedPref;
    //  private AddMatchViewModel matchViewModel;
    //   private MatchListAdapter matchListAdapter;
    Intent intent;

    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Club");
        recyclerView = findViewById(R.id.searchrecyclerview);
        searchView = findViewById(R.id.app_bar_search);





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

        setContentView(R.layout.activity_main);

        /**
        * Calling the recycler view for the database access
        */
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

        /**
         * Create a drawer layout for the navigation
         */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_league, R.id.nav_club,
                R.id.nav_settings, R.id.nav_match)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * Inflate the menu
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

  /*  protected void onStart(){
        super.onStart();
        if(databaseReference!=null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        list=new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            list.add(ds.getValue(Club.class));
                        }
                        SearchAdapter searchAdapter = new SearchAdapter(list);
                        recyclerView.setAdapter(searchAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            if(searchView!=null){
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        search(newText);
                        return true;
                    }
                });
            }
        }
    }

   */
    private void search(String s){
        ArrayList<Club> myList = new ArrayList<>();

        for(Club object : list){
            if(object.getNameClub().toLowerCase().contains(s.toLowerCase())){
                myList.add(object);
            }
        }
        SearchAdapter searchAdapter = new SearchAdapter(myList);
        recyclerView.setAdapter(searchAdapter);
    }



}
