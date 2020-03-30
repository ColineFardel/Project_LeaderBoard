package com.example.project_leaderboard.ui.club;

import android.app.Application;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.BaseApp;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.repository.ClubRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

public class ClubViewModel extends AndroidViewModel {

    private ClubRepository repository;

    private Application application;

    private final MediatorLiveData<Club> observableClub;

    private MutableLiveData<String> mText;

    public ClubViewModel (@NonNull Application application, ClubRepository clubRepository){
        super(application);
        this.application=application;
        repository=clubRepository;
        observableClub = new MediatorLiveData<>();
        observableClub.setValue(null);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final ClubRepository repository;

        public Factory(@NonNull Application application){
            this.application=application;
            repository = ((BaseApp)application).getClubRepository();
        }

        public void createClub (Club club, OnAsyncEventListener callback){
            repository.insert(club,callback,application);
        }
        public void updateClub (Club club, OnAsyncEventListener callback){
            repository.update(club,callback,application);
        }
        public void deleteClub (Club club, OnAsyncEventListener callback){
            repository.delete(club,callback,application);
        }
    }


}