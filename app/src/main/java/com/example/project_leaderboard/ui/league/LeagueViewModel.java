package com.example.project_leaderboard.ui.league;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.BaseApp;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.repository.LeagueRepository;

import java.util.List;

public class LeagueViewModel extends AndroidViewModel {

private LeagueRepository repository;

private Application application;


private final MediatorLiveData<List<League>> observableLeague;

public LeagueViewModel (@NonNull Application application, LeagueRepository leagueRepository){
    super(application);
    this.application=application;
    repository=leagueRepository;
    observableLeague = new MediatorLiveData<>();
    observableLeague.setValue(null);

    LiveData<List<League>> leagues = repository.getAllLeagues(application);
    observableLeague.addSource(leagues, value -> observableLeague.setValue(value));
}



    public static class Factory extends ViewModelProvider.NewInstanceFactory{
    @NonNull
        private final Application application;
        private Context context;

    private final LeagueRepository leagueRepository;

    public Factory(@NonNull Application application){
        this.application=application;
        //leagueRepository = ((BaseApp)application).getLeagueRepository();
        leagueRepository = new LeagueRepository();
    }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LeagueViewModel(application, leagueRepository);
        }
    }
    public LiveData<List<League>> getLeague(){
        return observableLeague;
    }

    public LiveData<List<String>> getLeagueName(Context context) {
        return  repository.getLeagueName(context);
    }
    }


