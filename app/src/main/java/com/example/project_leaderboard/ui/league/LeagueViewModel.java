package com.example.project_leaderboard.ui.league;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.repository.LeagueRepository;

import java.util.List;

/**
 * This class is the view model for one league
 * @author Samuel Michellod
 */
public class LeagueViewModel extends AndroidViewModel {

private LeagueRepository repository;
private final MediatorLiveData<League> observableLeague;

public LeagueViewModel (@NonNull Application application, LeagueRepository leagueRepository,String id){
    super(application);
    repository = leagueRepository;
    observableLeague = new MediatorLiveData<>();
    observableLeague.setValue(null);

    if(id!=null){
        LiveData<League> league = repository.getLeague(id);
        observableLeague.addSource(league, observableLeague::setValue);
    }
}

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

    @NonNull
    private final Application application;
    private final String id;
    private final LeagueRepository leagueRepository;

    public Factory(@NonNull Application application,String id){
        this.application = application;
        this.id = id;
        leagueRepository = new LeagueRepository().getInstance();
    }

        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LeagueViewModel(application, leagueRepository,id);
        }
    }
    public LiveData<League> getLeague(){
        return observableLeague;
    }
    /*
    public LiveData<List<String>> getLeagueName(Context context) {
        return  repository.getLeagueName(context);
    }
    public LiveData<League> getLeagueByName(Context context, String leagueName){
        return repository.getLeagueByName(context,leagueName);
    }

     */
}


