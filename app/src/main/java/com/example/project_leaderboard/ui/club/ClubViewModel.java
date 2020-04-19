package com.example.project_leaderboard.ui.club;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.repository.ClubRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.List;
/**
 * ViewModel class for clubs
 * @author Samuel Michellod
 */
public class ClubViewModel extends AndroidViewModel {

    private ClubRepository repository;
    private final MediatorLiveData<Club> observableClub;
    private String id;
    private String clubId;


    public ClubViewModel(@NonNull Application application, ClubRepository clubRepository, String leagueId) {
        super(application);

        this.id=leagueId;

        repository = clubRepository;


        observableClub = new MediatorLiveData<>();
        observableClub.setValue(null);
        /*
        LiveData<Club> club = repository.getClub(leagueId,clubId);
        observableClub.addSource(club, observableClub::setValue);

         */
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final ClubRepository clubRepository;
        private final String leagueId;

        public Factory(@NonNull Application application,String leagueId) {
            this.application = application;
            clubRepository = ClubRepository.getInstance();
            this.leagueId = leagueId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ClubViewModel(application, clubRepository,leagueId);
        }
    }

    public LiveData<Club> getClub(String leagueId ,String clubId){
        this.clubId=clubId;
        return repository.getClub(leagueId,clubId);
    }

    public void createClub (Club club, OnAsyncEventListener callback){
        repository.insert(club,callback);
    }
    public void updateClub (Club club, OnAsyncEventListener callback){
        repository.update(club,callback,id);
    }
    public void deleteClub (Club club, OnAsyncEventListener callback){
        repository.delete(club,callback, id);
    }




}