package com.example.project_leaderboard.ui.match;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.MatchRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

/**
 * ViewModel class for matches
 * @author Samuel Michellod
 */
public class MatchViewModel extends AndroidViewModel {
    private MatchRepository repository;
    private final MediatorLiveData<Match> observableMatch;
    private String matchId;
    private String leagueId;

    public MatchViewModel (@NonNull Application application, MatchRepository matchRepository, String leagueId){
        super(application);

        repository=matchRepository;
        this.leagueId = leagueId;
        this.matchId = matchId;

        observableMatch = new MediatorLiveData<>();
        observableMatch.setValue(null);

    }
    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final String leagueId;
        private final MatchRepository repository;

        public Factory (@NonNull Application application, String leagueId){
            this.application=application;
            this.leagueId  =leagueId;
            repository= MatchRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MatchViewModel(application, repository,leagueId);
        }
    }

    public LiveData<Match> getMatch(String matchId,String leagueId){
        return repository.getMatch(matchId,leagueId);
    }

    public void createMatch (Match match, OnAsyncEventListener callback){
        MatchRepository.getInstance().insert(match,callback);
    }

    public void updateMatch(Match match, OnAsyncEventListener callback){
        repository.update(match, callback);
    }


}





