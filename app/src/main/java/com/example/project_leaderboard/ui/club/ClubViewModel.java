package com.example.project_leaderboard.ui.club;

import android.app.Application;

import androidx.annotation.NonNull;
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


    public ClubViewModel(@NonNull Application application, ClubRepository clubRepository,String id) {
        super(application);

        repository = clubRepository;

        observableClub = new MediatorLiveData<>();
        observableClub.setValue(null);

        LiveData<Club> club = repository.getClub(id);
        observableClub.addSource(club, observableClub::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final ClubRepository clubRepository;
        private final String id;

        public Factory(@NonNull Application application,String id) {
            this.application = application;
            clubRepository = ClubRepository.getInstance();
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ClubViewModel(application, clubRepository,id);
        }
    }

    public LiveData<Club> getClub(){
        return observableClub;
    }

    public void createClub (Club club, OnAsyncEventListener callback){
        repository.insert(club,callback);
    }
    public void updateClub (Club club, OnAsyncEventListener callback){
        repository.update(club,callback);
    }
    public void deleteClub (Club club, OnAsyncEventListener callback){
        repository.delete(club,callback);
    }




}