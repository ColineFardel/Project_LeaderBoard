package com.example.project_leaderboard.ui.league;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project_leaderboard.db.dao.LeagueDao;
import com.example.project_leaderboard.db.dao.MatchDao;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.AppDatabase;
import com.example.project_leaderboard.db.repository.MatchRepository;

import java.util.List;

public class LeagueViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    private MatchRepository repository;
    private Application application;


    //private final MediatorLiveData<Match> observableMatch;
    private LeagueDao leagueDao;
    private AppDatabase db;
    private String LeagueName;
    private LiveData<List<League>> mAllLeagues;
    private String TAG = this.getClass().getSimpleName();

    public LeagueViewModel(Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        leagueDao = db.leagueDao();
        mAllLeagues = (LiveData<List<League>>) leagueDao.GetLeagueName(LeagueName);
        mText = new MutableLiveData<>();
        mText.setValue("Leagues");
    }

public LiveData<List<League>> getLeagueName(){
        return mAllLeagues;
}
/*
    public LiveData<List<League>> getmAllLeagues() {
        return mAllLeagues;
    }
*/


    /*
    public LeagueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Leagues");
    }
*/
    public LiveData<String> getText() {
        return mText;
    }
}