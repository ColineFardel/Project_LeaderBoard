package com.example.project_leaderboard.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.firebase.ClubListLiveData;
import com.example.project_leaderboard.db.firebase.ClubLiveData;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
/**
 * Repository class for clubs
 * @author Samuel Michellod
 */
public class ClubRepository {

    private static ClubRepository instance;

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

    public LiveData<Club> getClub(String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Club")
                .child(id);
        return new ClubLiveData(reference);
    }
    public LiveData<List<Club>> getClubsByLeague(String leagueId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Club")
                .child(leagueId);
        return new ClubListLiveData(reference);
    }

  /*  public LiveData<List<Club>> getAllClubs (Application application){
        return AppDatabase.getInstance(application).clubDao().getAll();
    }

    public LiveData<List<Club>> getByLeague (final int LeagueId, Context context){
        return AppDatabase.getInstance(context).clubDao().getByLeague(LeagueId);
    }
*/
    public void insert(final Club club, final OnAsyncEventListener callback){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("clubs");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance().getReference("league").child(club.getLeagueId()).child("clubs").child(key)
                .setValue(club, ((databaseError, databaseReference) -> {
                    if(databaseError !=null){
                        callback.onFailure(databaseError.toException());
                    }else {
                        callback.onSuccess();
                    }
                }));
    }

    public void update(final Club club, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("clubs")
                .child(club.getClubId())
                .updateChildren(club.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Club club, final OnAsyncEventListener callback){
       FirebaseDatabase.getInstance().getReference("clubs");
        FirebaseDatabase.getInstance().getReference("league").child(club.getLeagueId()).child("clubs")
                .removeValue ((databaseError, databaseReference) -> {
                    if(databaseError !=null){
                        callback.onFailure(databaseError.toException());
                    }else {
                        callback.onSuccess();
                    }
                });
    }



}
