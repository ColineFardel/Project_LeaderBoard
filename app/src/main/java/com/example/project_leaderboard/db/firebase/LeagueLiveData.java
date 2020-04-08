package com.example.project_leaderboard.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.League;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LeagueLiveData extends LiveData<League> {

    private final DatabaseReference reference;
    private static final String TAG = "LeagueLiveData";


    private final LeagueLiveData.MyValueEventListener listener = new LeagueLiveData.MyValueEventListener();

    public LeagueLiveData (DatabaseReference ref){
        reference= ref;
    }

    protected void OnActive(){
        reference.addValueEventListener(listener);
    }

    protected  void onInactive(){
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener{

        public void onDataChange (@NonNull DataSnapshot dataSnapshot){
            League league = dataSnapshot.getValue(League.class);
         //   league.setLeagueId(dataSnapshot.getKey());
            setValue(league);
        }

        public void onCancelled (@NonNull DatabaseError databaseError){
            Log.e(TAG, "Can't listner to query " + reference, databaseError.toException());
        }
    }

}
