package com.example.project_leaderboard.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.Match;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchListLiveData extends LiveData<List<Match>> {

    private final DatabaseReference reference;
    private static final String TAG = "LeaguesLiveData";
    private final MyValueEventListener listener = new MyValueEventListener();

    public MatchListLiveData (DatabaseReference ref){
        reference = ref;
    }

    protected void onActive(){
        Log.d(TAG,"onActive");
        reference.addValueEventListener(listener);
    }

    protected  void onInactive(){
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {

        public void onDataChange (@NonNull DataSnapshot dataSnapshot){
            setValue(toMatchList(dataSnapshot));
        }

        public void onCancelled (@NonNull DatabaseError databaseError){
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
    private List<Match> toMatchList(DataSnapshot snapshot){
        List<Match> matches = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()){
            Match match = childSnapshot.getValue(Match.class);
            match.setMatchId(childSnapshot.getKey());
            matches.add(match);
        }
        return matches;
    }
}
