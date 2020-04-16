package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.firebase.LeagueListLiveData;
import com.example.project_leaderboard.db.firebase.LeagueLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
/**
 * Repository class for leagues
 * @author Samuel Michellod
 */
public class LeagueRepository {

    private static LeagueRepository instance;

    public LeagueRepository(){

    }

    public static LeagueRepository getInstance() {
        if (instance == null) {
            synchronized (LeagueRepository.class) {
                if (instance == null) {
                    instance = new LeagueRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Method to get one league from the firebase database
     * @param id is the id of the league we want
     * @return the league
     */
    public LiveData<League> getLeague(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("League")
                .child(id);
        return new LeagueLiveData(reference);
    }

    /**
     * Method to get all the leagues from the firebase database
     * @return the list of the leagues
     */
    public LiveData<List<League>> getAllLeagues(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("League");
        return new LeagueListLiveData(reference);
    }
}
