package com.example.project_leaderboard.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.Club;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ClubLiveData extends LiveData<Club> {

    private final DatabaseReference reference;
    private final ClubLiveData.MyValueEventListener listener = new ClubLiveData.MyValueEventListener();
    private static final String TAG ="ClubLiveData";

    public ClubLiveData(DatabaseReference ref){
        reference = ref;
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
            if (dataSnapshot.exists()) {
                Club entity = dataSnapshot.getValue(Club.class);
                entity.setClubId(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        public void onCancelled (@NonNull DatabaseError databaseError){
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
