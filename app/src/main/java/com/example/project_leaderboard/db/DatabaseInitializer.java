package com.example.project_leaderboard.db;

import android.util.Log;
import android.os.AsyncTask;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.entity.Club;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";


    public static void populateDatabase (final AppDatabase db){
        Log.i(TAG, "Inserting demo data");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addMatch(final AppDatabase db, final String NameClubHome, final String NameClubVisitor, final int ScoreHome, final int ScoreVisitor, final int IdLeague){
        Match match = new Match(NameClubHome,NameClubVisitor,ScoreHome,ScoreVisitor,IdLeague);
        db.matchDao().insertAll(match);
    }

    private static void AddClub (final AppDatabase db, final String name, final int points, final int victories, final int draws, final int losses, final int leagueId){
        Club club = new Club(name, points,victories,draws,losses, leagueId);
        db.clubDao().insertAll(club);
    }
    private static void AddLeague (final AppDatabase db, final String name){
        League league = new League(name);
        db.leagueDao().insertLeague(league);
    }

    private static void populateWithTestData (AppDatabase db){
        db.clubDao().deleteAll();

        AddLeague(db,"Premier League");
        AddLeague(db,"Ligue 1");
        AddLeague(db,"Serie A");
        AddLeague(db,"Bundesliga");

        AddClub(db,"Arsenal", 15,3,0,0,1);
        AddClub(db,"Chelsea", 15,3,0,0,1);
        AddClub(db,"Manchester United", 15,3,0,0,1);
        AddClub(db,"Manchester City", 15,3,0,0,1);
        AddClub(db,"Liverpool", 15,3,0,0,1);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addMatch(db,"Arsenal","Chelsea",3,3,1);
        addMatch(db,"Manchester United","Manchester City",2,1,1);
    }

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{
        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db){
            database=db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }
    }
}
