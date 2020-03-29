package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.async.Match.DeleteMatch;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.db.async.Match.CreateMatch;
import com.example.project_leaderboard.db.async.Match.UpdateMatch;

import java.util.List;

public class MatchRepository {

    private static MatchRepository instance;

    private MatchRepository(){

    }

    public static MatchRepository getInstance(){
        if(instance==null){
            synchronized (MatchRepository.class){
                if(instance==null){
                    instance = new MatchRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Match>> getAllMatches (final int MatchId, Context context){
        return (LiveData<List<Match>>) AppDatabase.getInstance(context).matchDao().getAll();
    }



   public void insert (final Match match, OnAsyncEventListener callback, Application application){
        new CreateMatch(application,callback).execute(match);
    }

    public void update (final Match match, OnAsyncEventListener callback, Application application){
        new UpdateMatch (application,callback).execute(match);
    }
    public void delete (final Match match, OnAsyncEventListener callback, Application application){
        new DeleteMatch(application,callback).execute(match);
    }

}
