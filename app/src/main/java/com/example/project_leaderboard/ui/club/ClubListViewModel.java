package com.example.project_leaderboard.ui.club;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.db.async.CreateClub;
import com.example.project_leaderboard.db.async.DeleteClub;
import com.example.project_leaderboard.db.async.UpdateClub;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.repository.ClubRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.List;

public class ClubListViewModel extends AndroidViewModel {

    private ClubRepository repository;

    private Context applicationContext;

    private int LeagueId;

    private final MediatorLiveData<List<Club>> observableClub;

    public ClubListViewModel (@NonNull Application application, ClubRepository clubRepository){
        super(application);
        repository= clubRepository;
        applicationContext=application.getApplicationContext();

        observableClub = new MediatorLiveData<>();

        observableClub.setValue(null);

        LiveData<List<Club>> clubs = repository.getByLeague(LeagueId,applicationContext);

        observableClub.addSource(clubs,observableClub::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ClubRepository clubRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            clubRepository = ClubRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ClubListViewModel(application, clubRepository);
        }
    }

    /**
     * Expose the LiveData ClubEntity query so the UI can observe it.
     */
    public LiveData<List<Club>> getClubs() {
        return observableClub;
    }

    public void insert (final Club club, OnAsyncEventListener callback, Application application){
        new CreateClub(application,callback).execute((Runnable) club);
    }

    public void update (final Club club, OnAsyncEventListener callback, Application application){
        new UpdateClub(application,callback).execute((Runnable) club);
    }
    public void delete (final Club club, OnAsyncEventListener callback, Application application){
        new DeleteClub(application,callback).execute((Runnable) club);
    }

}
