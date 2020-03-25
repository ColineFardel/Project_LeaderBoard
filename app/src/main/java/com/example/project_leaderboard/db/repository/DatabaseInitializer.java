package com.example.project_leaderboard.db.repository;

import android.util.Log;
import android.os.AsyncTask;

import com.example.project_leaderboard.db.entity.Match;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";


    public static void populateDatabase (final AppDatabase db){
        Log.i(TAG, "Inserting demo data");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addMatch(final AppDatabase db, final int IdClubHome, final int IdClubVisitor, final int ScoreHome, final int ScoreVisitor){
        Match match = new Match(IdClubHome,IdClubVisitor,ScoreHome,ScoreVisitor);
        db.matchDao().insertAll(match);
    }

    private static void populateWithTestData (AppDatabase db){
        db.matchDao().deleteAll();


        addMatch(db,1,1,3,2);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
