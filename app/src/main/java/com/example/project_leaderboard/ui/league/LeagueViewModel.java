package com.example.project_leaderboard.ui.league;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.BaseApp;
import com.example.project_leaderboard.db.dao.LeagueDao;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.repository.LeagueRepository;
import com.example.project_leaderboard.db.repository.MatchRepository;

import java.util.List;

public class LeagueViewModel extends AndroidViewModel {

private LeagueRepository repository;

private Application application;

private final MediatorLiveData<League> observableLeague;

public LeagueViewModel (@NonNull Application application, LeagueRepository leagueRepository){
    super(application);
    this.application=application;
    repository=leagueRepository;
    observableLeague = new MediatorLiveData<>();
    observableLeague.setValue(null);
}

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
    @NonNull
        private final Application application;

    private final LeagueRepository leagueRepository;

    public Factory(@NonNull Application application){
        this.application=application;
        leagueRepository = ((BaseApp)application).getLeagueRepository();
    }
    }
    }


