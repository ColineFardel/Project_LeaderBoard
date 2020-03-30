package com.example.project_leaderboard.ui.league;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import com.example.project_leaderboard.ui.club.ModifyClub;
import com.example.project_leaderboard.ui.match.MatchsOfClub;
import com.example.project_leaderboard.ui.settings.SharedPref;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.club.ClubFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LeagueBoard extends AppCompatActivity{

    private String[] clubs;
    private ImageButton imageButton;
    private SharedPref sharedPref;
    private List<String> userSelection = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPref = new SharedPref(this);

        //Loading the language from the preferences
        String languageToLoad = sharedPref.getLanguage();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);

        DisplayMetrics dm= getResources().getDisplayMetrics();
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, dm);

        //Loading the Night mode from the preferences
        if(sharedPref.loadNightMode()==true){
            setTheme(R.style.NightTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.leaderborad_activity);

        TextView textView = findViewById(R.id.league_name);

        //Set the button to open the add club fragment
        imageButton = findViewById(R.id.button_add);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = findViewById(R.id.league_name);
                String value = (String)title.getText();
                Bundle b= new Bundle();
                b.putString("League",value);
                FragmentManager fm = getSupportFragmentManager();
                ClubFragment fragment = new ClubFragment();
                fragment.setArguments(b);
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
        String wins[] = {"7","12","9","2","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
        String draws[] = {"2","12","9","2","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
        String loses[] = {"3","12","9","2","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};

        MyAdapter listViewAdapter = new MyAdapter(this, clubs,draws,wins,loses,points);
        listView.setAdapter(listViewAdapter);

        //Putting the multi choice mode listener for the list view
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);

        //Open new activity to show the matches of the club selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //probably going to use a switch or a Bundle
                String value = clubs[position];
                Bundle b = new Bundle();
                b.putString("ClubName",value);
                Intent i = new Intent(getBaseContext(), MatchsOfClub.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        textView.setText(text);

    }

    //Setting the multi choice listener in order to delete multiple clubs or to modify one
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if(userSelection.contains(clubs[position])){
                userSelection.remove(clubs[position]);
            }
            else{
                userSelection.add(clubs[position]);
            }
            mode.setTitle(userSelection.size() + " items selected");
        }

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

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_bar:
                    //call remove items from adapter
                    break;
                case R.id.modify_bar:
                    if(userSelection.size()>1){
                        Toast toast=Toast. makeText(LeagueBoard.this,"You must select only one item to modify it",Toast. LENGTH_SHORT);
                        toast. setMargin(50,50);
                        toast. show();
                    }
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

        public void removeItems(List<String> items){
            for(String item : items){
                //put the method to remove item from database
            }
            notifyDataSetChanged();
        }
    }
}