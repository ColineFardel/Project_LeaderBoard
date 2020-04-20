package com.example.project_leaderboard.db.repository;

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

    /**
     * Get one club by its id
     * @param
     * @return on club
     */
    public LiveData<Club> getClub(String leagueId, String clubId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Club")
                .child(leagueId)
                .child(clubId);
        return new ClubLiveData(reference);
    }

    /**
     * Get all the clubs of one specific league
     * @param leagueId
     * @return the list of clubs
     */
    public LiveData<List<Club>> getClubsByLeague(String leagueId){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Club")
                .child(leagueId);
        return new ClubListLiveData(reference);
    }

    public void insert(final Club club, final OnAsyncEventListener callback){
        String id = FirebaseDatabase.getInstance().getReference("Club")
                .child(club.getLeagueId())
                .push().getKey();

        FirebaseDatabase.getInstance().getReference("Club")
                .child(club.getLeagueId())
                .child(id)
                .setValue(club, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Club club, final OnAsyncEventListener callback,String leagueId) {
        FirebaseDatabase.getInstance()
                .getReference("Club")
                .child(leagueId)
                .child(club.getClubId())
                .updateChildren(club.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Club club, final OnAsyncEventListener callback, String leagueId){
       FirebaseDatabase.getInstance()
               .getReference("Club")
               .child(leagueId)
               .child(club.getClubId())
               .removeValue((databaseError, databaseReference) -> {
                   if (databaseError != null) {
                       callback.onFailure(databaseError.toException());
                   } else {
                       callback.onSuccess();
                   }
               });
    }
}