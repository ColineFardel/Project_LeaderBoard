package com.example.project_leaderboard;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.project_leaderboard.ui.settings.SharedPref;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPref sharedPref;
    //  private AddMatchViewModel matchViewModel;
    //   private MatchListAdapter matchListAdapter;
    Intent intent;

    MaterialSearchView searchView;


    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
MaterialSearchView searchView;
        //Loading the language from the preferences
        String languageToLoad = sharedPref.getLanguage();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        DisplayMetrics dm= getResources().getDisplayMetrics();
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, dm);

        //Loading Night mode from preferences
        if(sharedPref.loadNightMode()==true){
            setTheme(R.style.NightTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main);


     //   searchView();

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

    private void searchView(){
        searchView= findViewById(R.id.app_bar_search);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.layout.suggest_item);
       // MenuItem item = menu.findItem(R.id.app_bar_search);
      // searchView.setMenuItem(item);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.app_bar_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
@Override
public void onBackPressed(){
        if(searchView.isSearchOpen()){
            searchView.closeSearch();
        }
        else{
            super.onBackPressed();
        }
}
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

   /* public void toastMsg (String msg){
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.show();
    }
    public void displayToastmsg(View v){
        toastMsg("Match successfully added");
    }

    */
}
