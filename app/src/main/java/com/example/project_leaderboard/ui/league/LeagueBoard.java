package com.example.project_leaderboard.ui.league;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project_leaderboard.adapter.ClubAdapter;
import com.example.project_leaderboard.adapter.ClubRecyclerAdapter;
import com.example.project_leaderboard.adapter.RecyclerAdapter;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.util.RecyclerViewItemClickListener;
import com.example.project_leaderboard.ui.club.ClubListViewModel;
import com.example.project_leaderboard.ui.settings.SharedPref;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.club.ClubFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * This class is used to show all the clubs in a specific league and their points/wins/draws/loses
 * When you click on one club it will open the class MatchsOfClub to display all the matches of this club
 * When you click on the plus button it will open the class ClubFragment to create a new club
 * If you long click on one club you can modify it with the class ModifyClub
 * If you long click on multiple clubs you can delete them
 * @author Coline Fardel
 */
public class LeagueBoard extends AppCompatActivity{
    private List<Club> clubs;
    private ClubListViewModel viewModel;
    private RecyclerAdapter<Club> recyclerAdapter;
    private static final String TAG = "LeagueBoard";
    private LeagueViewModel leagueViewModel;

    private SharedPref sharedPref;
    private ImageButton imageButton;


    private ClubRecyclerAdapter<Club> clubRecyclerAdapter;
    private ClubAdapter clubAdapter;


    /*
    private String[] clubs;


    private List<String> userSelection = new ArrayList<>();
    //private RecyclerAdapter<Club> recyclerAdapter;
    private ClubViewModel viewModel;
    //private List<Club> clubs;
    private static final String TAG = "Leaderboard Fragment";
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, dm);

        /**
         * Loading the Night mode from the preferences
         */
        if (sharedPref.loadNightMode() == true) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.leaderborad_activity);
        TextView leagueName = findViewById(R.id.league_name);

        /**
         * Getting the id of the league to set the title and get the clubs from database
         */
        Intent i = getIntent();
        String leagueId = i.getStringExtra("leagueId");

        LeagueViewModel.Factory fac = new LeagueViewModel.Factory(this.getApplication(),leagueId);
        leagueViewModel = new ViewModelProvider(this,fac).get(LeagueViewModel.class);
        leagueViewModel.getLeague().observe(this,league -> {
            if(league!=null){
                leagueName.setText(league.getLeagueName());
            }
        });

        /**
         * Firebase
         */
        RecyclerView recyclerView = findViewById(R.id.clubsrecyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        clubs = new ArrayList<>();

        /**
         * Open new activity to show the matches of the club selected
         */
        /*
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = clubs[position];
                Bundle b = new Bundle();
                b.putString("ClubName",value);
                Intent i = new Intent(getBaseContext(), MatchsOfClub.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

         */

        clubRecyclerAdapter = new ClubRecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on:" + clubs.get(position).toString());
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });

        ClubListViewModel.Factory factory = new ClubListViewModel.Factory(getApplication(), leagueId);
        viewModel = new ViewModelProvider(this, factory).get(ClubListViewModel.class);
        viewModel.getClubsByLeague(leagueId).observe(this, clubEntities -> {
            if (clubEntities != null) {
                clubs = clubEntities;

                Collections.sort(clubs, new Comparator<Club>() {
                    @Override
                    public int compare(Club o1, Club o2) {
                        int club1Points = o1.getPoints();
                        int club2Points = o2.getPoints();

                        if(club1Points==club2Points)
                            return 0;
                        if(club1Points<club2Points)
                            return 1;
                        else
                            return -1;
                    }
                });
                clubRecyclerAdapter.setClubData(clubs);

            }
        });

        /**
         * Setting the button to open the add club fragment
         */
        imageButton = findViewById(R.id.button_add);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = findViewById(R.id.league_name);
                String value = (String) title.getText();
                Bundle b = new Bundle();
                b.putString("League", value);
                FragmentManager fm = getSupportFragmentManager();
                ClubFragment fragment = new ClubFragment();
                fragment.setArguments(b);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.leader_clubs, fragment).commit();
            }
        });

        /**
         * Setting the multi choice listener in order to delete multiple clubs or to modify one
         */

    /*
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            //If the user click two times on the same club then it will remove it from userSelection
            if(userSelection.contains(clubs[position])){
                userSelection.remove(clubs[position]);
            }
            //If the user click one time on a club it will add it to userSelection
            else{
                userSelection.add(clubs[position]);
            }
            //Use the size of the array to show how many items the user selected
            mode.setTitle(userSelection.size() + getString(R.string.item_selected));
        }

     */

        /**
         * Putting the multi choice mode listener for the list view
         */
        /*
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);

         */

        /**
         * Create the menu for the selection
         * @param mode
         * @param menu
         * @return true
         */
    /*
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.delete_modify_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

     */




        /**
         * Say what to do depending on what button the user click on
         * @param mode
         * @param item
         * @return false
         */
        /*
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                //If the user click on the delete button it will delete the clubs from the database
                case R.id.delete_bar:
                    //delete the club(s) from the database
                    break;
                //If the user click on the modify button
                case R.id.modify_bar:
                    //If the user has selected more than one club it will show a toast
                    if(userSelection.size()>1){
                        String warning = getString(R.string.toast);
                        Toast toast=Toast. makeText(LeagueBoard.this,warning,Toast. LENGTH_SHORT);
                        toast. setMargin(50,50);
                        toast.setGravity(Gravity.CENTER, 0,0);
                        toast. show();
                    }
                    //If the user selected one item it will open the class ModifyClub and send to it the name of the club selected
                    else{
                        String value = userSelection.get(0);
                        Bundle b = new Bundle();
                        b.putString("ClubName",value);
                        Intent i;
                        i = new Intent(getBaseContext(), ModifyClub.class);
                        i.putExtras(b);
                        startActivity(i);
                    }
                default:
                    return false;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    };

         */



        recyclerView.setAdapter(clubRecyclerAdapter);
    }
}