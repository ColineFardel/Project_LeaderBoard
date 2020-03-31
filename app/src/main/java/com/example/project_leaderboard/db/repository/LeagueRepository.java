package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.entity.League;

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
    /*
public LiveData<List<League>> getAllLeagues (Context context){
        return AppDatabase.getInstance(context).leagueDao().getAllLeagues();
}
*/
    public LiveData<List<League>> getAllLeagues (Application application){
        return AppDatabase.getInstance(application).leagueDao().getAllLeagues();
    }

    public LiveData<List<String>> getLeagueName(Context context){
        return AppDatabase.getInstance(context).leagueDao().GetLeagueName();
    }

    public LiveData<League> getLeagueByName(Context context, String leagueName){
        return AppDatabase.getInstance(context).leagueDao().getLeagueByName(leagueName);
    }
}
