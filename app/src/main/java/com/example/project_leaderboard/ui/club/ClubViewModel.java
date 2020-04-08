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

    private Application application;

    private final MediatorLiveData<List<Club>> observableClub;


    public ClubViewModel(@NonNull Application application, ClubRepository clubRepository) {
        super(application);
        this.application = application;
        repository = clubRepository;
        observableClub = new MediatorLiveData<>();
        observableClub.setValue(null);
        LiveData<List<Club>> clubs = repository.getAllClubs(application);
        observableClub.addSource(clubs, value -> observableClub.setValue(value));
    }




    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final ClubRepository clubRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            clubRepository = new ClubRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ClubViewModel(application, clubRepository);
        }
    }

    public LiveData<List<Club>> getClub(){
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