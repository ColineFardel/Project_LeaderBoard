package com.example.project_leaderboard.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.League;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeagueListLiveData extends LiveData<List<League>> {

    private final DatabaseReference reference;
    private static final String TAG = "LeaguesLiveData";
    private final MyValueEventListener listener = new MyValueEventListener();

    public LeagueListLiveData (DatabaseReference ref){
        reference = ref;
    }

    protected void OnActive(){
        Log.d(TAG,"onActive");
        reference.addValueEventListener(listener);
    }

    protected  void onInactive(){
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {

        public void onDataChange (@NonNull DataSnapshot dataSnapshot){
            setValue(toLeagueList(dataSnapshot));
        }

        public void onCancelled (@NonNull DatabaseError databaseError){
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
    private List<League> toLeagueList(DataSnapshot snapshot){
        List<League> leagues = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()){
            League league = childSnapshot.getValue(League.class);
            league.setLeagueId(childSnapshot.getKey());
            leagues.add(league);
        }
        return leagues;
    }
}
