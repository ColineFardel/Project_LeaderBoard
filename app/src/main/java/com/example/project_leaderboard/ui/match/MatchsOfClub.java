package com.example.project_leaderboard.ui.match;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project_leaderboard.R;
import com.example.project_leaderboard.ui.league.LeagueBoard;
import com.example.project_leaderboard.ui.settings.SharedPref;

public class MatchsOfClub extends AppCompatActivity {

    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = new SharedPref(this);
        if(sharedPref.loadNightMode()==true){
            setTheme(R.style.NightTheme);
        }
        else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_matchs_of_club);

        ListView listView = findViewById(R.id.all_matchs);

        //put data
        String score_visitor[] = {"3","2","3","0","1"};
        String score_home[] = {"0","3","1","1","0"};
        String club_name_visitor[] = {"Liverpool","Arsenal","Arsenal","Arsenal","Manchester United"};
        String club_name_home[] = {"Arsenal","Manchester United","Liverpool","Liverpool","Arsenal"};

        MyAdapter listViewAdapter = new MyAdapter(this, score_visitor,score_home,club_name_visitor,club_name_home);
        listView.setAdapter(listViewAdapter);
    }
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
