package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.async.Match.DeleteMatch;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.db.async.Match.CreateMatch;
import com.example.project_leaderboard.db.async.Match.UpdateMatch;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
/**
 * Repository class for matches
 * @author Samuel Michellod
 */
public class MatchRepository {

    private static MatchRepository instance;
    private Context context;
    public MatchRepository(){

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

    public LiveData<List<Match>> getAllMatches (){
        return (LiveData<List<Match>>) AppDatabase.getInstance(context).matchDao().getAll();
    }


    public void insert(final Match match, final OnAsyncEventListener callback){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("matches");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance().getReference("leagues").child(match.getIdLeague()).child("matches").child(key)
                .setValue(match, ((databaseError, databaseReference) -> {
                    if(databaseError !=null){
                        callback.onFailure(databaseError.toException());
                    }else {
                        callback.onSuccess();
                    }
                }));
    }

    public void update(final Match match, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("matches")
                .child(match.getMatchId())
                .updateChildren(match.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Match match, final OnAsyncEventListener callback){
        FirebaseDatabase.getInstance().getReference("matches");
        FirebaseDatabase.getInstance().getReference("matches").child(match.getMatchId())
                .removeValue ((databaseError, databaseReference) -> {
                    if(databaseError !=null){
                        callback.onFailure(databaseError.toException());
                    }else {
                        callback.onSuccess();
                    }
                });
    }


    public void update (final Match match, OnAsyncEventListener callback, Application application){
        new UpdateMatch (application,callback).execute(match);
    }


}
