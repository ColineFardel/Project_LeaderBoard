package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.async.DeleteMatch;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.db.async.CreateMatch;
import com.example.project_leaderboard.db.async.UpdateMatch;

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

   /* public LiveData<List<Match>> getMatch (final int MatchId, Application application){
        new CreateMatch(MatchId,application).execute(getMatch());
    }

    */

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
