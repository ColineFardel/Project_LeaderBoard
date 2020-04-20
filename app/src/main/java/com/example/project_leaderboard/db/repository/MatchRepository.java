package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.firebase.MatchListLiveData;
import com.example.project_leaderboard.db.firebase.MatchLiveData;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
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

    /**
     * Get one match
     * @param id
     * @return one match
     */
    public LiveData<Match> getMatch(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Match")
                .child(id);
        return new MatchLiveData(reference);
    }

    /**
     * Get all matches
     * @return the list of matches
     */
    public LiveData<List<Match>> getAllMatches(String leagueId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Match")
                .child(leagueId);
        return new MatchListLiveData(reference);
    }

    public void insert(final Match match, final OnAsyncEventListener callback){
        String id = FirebaseDatabase.getInstance().getReference("Match")
                .child(match.getIdLeague())
                .push().getKey();

        FirebaseDatabase.getInstance().getReference("Match")
                .child(match.getIdLeague())
                .child(id)
                .setValue(match, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
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

    public void delete(final Match match, final OnAsyncEventListener callback, String leagueId){
        FirebaseDatabase.getInstance()
                .getReference("Match")
                .child(leagueId)
                .child(match.getMatchId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

        /*
        FirebaseDatabase.getInstance().getReference("matches");
        FirebaseDatabase.getInstance().getReference("matches").child(match.getMatchId())
                .removeValue ((databaseError, databaseReference) -> {
                    if(databaseError !=null){
                        callback.onFailure(databaseError.toException());
                    }else {
                        callback.onSuccess();
                    }
                });

         */
    }



}
