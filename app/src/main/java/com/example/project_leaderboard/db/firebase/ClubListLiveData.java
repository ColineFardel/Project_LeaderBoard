package com.example.project_leaderboard.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.Club;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClubListLiveData extends LiveData<List<Club>> {

    private final DatabaseReference reference;
    private static final String TAG = "LeaguesLiveData";
    private final MyValueEventListener listener = new MyValueEventListener();

    public ClubListLiveData (DatabaseReference ref){
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
            setValue(toClubList(dataSnapshot));
        }

        public void onCancelled (@NonNull DatabaseError databaseError){
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
    private List<Club> toClubList(DataSnapshot snapshot){
        List<Club> clubs = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()){
            Club club = childSnapshot.getValue(Club.class);
            club.setClubId(childSnapshot.getKey());
            clubs.add(club);
        }
        return clubs;
    }
}
