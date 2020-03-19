package com.example.project_leaderboard.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.async.DeleteMatch;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.db.async.CreateMatch;
import com.example.project_leaderboard.db.async.UpdateMatch;

import java.util.List;

public class MatchRepository {

    public LiveData<List<Match>> getAllMatch (Context context){
        return (LiveData<List<Match>>) AppDatabase.getAppDatabase(context).matchDao().getAll();
    }

   public void insert (final Match match, OnAsyncEventListener callback, Context context){
        new CreateMatch(context,callback).execute(match);
    }

    public void update (final Match match, OnAsyncEventListener callback, Context context){
        new UpdateMatch (context,callback).execute(match);
    }
    public void delete (final Match match, OnAsyncEventListener callback, Context context){
        new DeleteMatch(context,callback).execute(match);
    }

}
