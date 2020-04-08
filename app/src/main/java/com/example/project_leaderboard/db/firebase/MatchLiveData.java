package com.example.project_leaderboard.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.Match;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MatchLiveData extends LiveData<Match> {

    private static final String TAG = "MatchLiveData";

    private final DatabaseReference reference;
    private final MatchLiveData.MyValueEventListener listener = new MatchLiveData.MyValueEventListener();

    public MatchLiveData (DatabaseReference ref){
        this.reference= ref;
    }

    protected void onActive(){
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    protected void onInactive(){
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {

        public void onDataChange (@NonNull DataSnapshot dataSnapshot){
            Match match = dataSnapshot.getValue(Match.class);
          //  match.setIdLeague(dataSnapshot.getKey());
        }

        public void onCancelled (@NonNull DatabaseError databaseError){
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
