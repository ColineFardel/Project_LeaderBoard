package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.async.Club.CreateClub;
import com.example.project_leaderboard.db.async.Club.DeleteClub;
import com.example.project_leaderboard.db.async.Club.UpdateClub;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.List;

public class ClubRepository {


    private static ClubRepository instance;

    private ClubRepository(){

    }

    public static ClubRepository getInstance(){
        if(instance==null){
            synchronized (ClubRepository.class){
                if(instance==null){
                    instance = new ClubRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Club>> getByLeague (final int LeagueId, Context context){
        return AppDatabase.getInstance(context).clubDao().getByLeague(LeagueId);
    }



    public void insert (final Club club, OnAsyncEventListener callback, Application application){
        new CreateClub(application,callback).execute((Runnable) club);
    }

    public void update (final Club club, OnAsyncEventListener callback, Application application){
        new UpdateClub(application,callback).execute((Runnable) club);
    }
    public void delete (final Club club, OnAsyncEventListener callback, Application application){
        new DeleteClub(application,callback).execute((Runnable) club);
    }
}
