package com.example.project_leaderboard.ui.league;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.repository.LeagueRepository;
import java.util.List;

public class LeagueListViewModel extends AndroidViewModel {

    private LeagueRepository repository;
    private final MediatorLiveData<List<League>> observableLeagues;

    public LeagueListViewModel(@NonNull Application application, LeagueRepository repository) {
        super(application);

        this.repository = repository;
        observableLeagues = new MediatorLiveData<>();
        observableLeagues.setValue(null);

        LiveData<List<League>> leagues = repository.getAllLeagues();

        observableLeagues.addSource(leagues, observableLeagues::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final LeagueRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.repository = LeagueRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new LeagueListViewModel(application, repository);
        }
    }

    /**
     * Expose the LiveData League query so the UI can observe it.
     */
    public LiveData<List<League>> getAllLeagues() {
        return observableLeagues;
    }

}

