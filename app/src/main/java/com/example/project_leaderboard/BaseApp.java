package com.example.project_leaderboard;

import android.app.Application;

import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.repository.ClubRepository;
import com.example.project_leaderboard.db.repository.LeagueRepository;
import com.example.project_leaderboard.db.repository.MatchRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
    }
    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this);
    }
    public LeagueRepository getLeagueRepository(){
        return LeagueRepository.getInstance();
    }
    public MatchRepository getMatchRepository(){
        return MatchRepository.getInstance();
    }
    public ClubRepository getClubRepository(){
        return ClubRepository.getInstance();
    }
}
