package com.example.project_leaderboard.ui.match;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.settings.SharedPref;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MatchsOfClub extends AppCompatActivity {

    private SharedPref sharedPref;
    private ImageButton imageButton;
    private List<String> userSelection = new ArrayList<>();
    private String club_name[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        setContentView(R.layout.activity_matchs_of_club);

        //Get the arguments from last activity
        String value = getIntent().getExtras().getString("ClubName");

        //Set the title textview
        TextView title = findViewById(R.id.club_name_edittext);
        title.setText(value);

        //Set the button to open the add match fragment
        imageButton = findViewById(R.id.button_add);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                MatchFragment fragment = new MatchFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.matchs_of_club,fragment).commit();
            }
        });

        ListView listView = findViewById(R.id.all_matchs);
        //put data
        String score_visitor[] = {"3","2","3","0","1"};
        String score_home[] = {"0","3","1","1","0"};
        String club_name_visitor[] = {"Liverpool","Arsenal","Arsenal","Arsenal","Manchester United"};
        String club_name_home[] = {"Arsenal","Manchester United","Liverpool","Liverpool","Arsenal"};

        club_name=club_name_home;

        //Putting the multi choice mode listener for the list view
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);

        MyAdapter listViewAdapter = new MyAdapter(this, score_visitor,score_home,club_name_visitor,club_name_home);
        listView.setAdapter(listViewAdapter);

    }

    //Setting the multi choice listener in order to delete multiple clubs or to modify one
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if(userSelection.contains(club_name[position])){
                userSelection.remove(club_name[position]);
            }
            else{
                userSelection.add(club_name[position]);
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
                        String warning = getString(R.string.toast);
                        Toast toast=Toast. makeText(MatchsOfClub.this,warning,Toast. LENGTH_SHORT);
                        toast. setMargin(50,50);
                        toast.setGravity(Gravity.CENTER, 0,0);
                        toast. show();
                    }
                    else{
                        String value = userSelection.get(0);
                        /*
                        Bundle b = new Bundle();
                        b.putString("ClubName",value);

                         */

                        Intent i;
                        i = new Intent(getBaseContext(), ModifyMatch.class);
                        //i.putExtras(b);
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


    class MyAdapter extends ArrayAdapter {
        Context context;
        String score_visitor[];
        String score_home[];
        String club_name_visitor[];
        String club_name_home[];

        MyAdapter(Context c, String score_visitor[], String score_home[], String club_name_visitor[], String club_name_home[]) {
            super(c, R.layout.row_match, R.id.club_home, club_name_home);
            this.context = c;
            this.score_home=score_home;
            this.score_visitor=score_visitor;
            this.club_name_home=club_name_home;
            this.club_name_visitor=club_name_visitor;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_match, parent, false);

            TextView myclub_home = row.findViewById(R.id.club_home);
            TextView myclub_visitor = row.findViewById(R.id.club_visitor);
            TextView myscore_visitor = row.findViewById(R.id.score_visitor_list);
            TextView myscore_home = row.findViewById(R.id.score_home_list);

            myclub_home.setText(club_name_home[position]);
            myclub_visitor.setText(club_name_visitor[position]);
            myscore_visitor.setText(score_visitor[position]);
            myscore_home.setText(score_home[position]);

            return row;
        }
    }
}
