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

import java.util.List;

public class MatchListViewModel extends AndroidViewModel {
    private MatchRepository repository;
    private String leagueId;

    private final MediatorLiveData<List<Match>> observableMatch;

    public MatchListViewModel (@NonNull Application application, MatchRepository matchRepository, String leagueId){
        super(application);

        this.leagueId = leagueId;

        repository= matchRepository;

        observableMatch = new MediatorLiveData<>();
        observableMatch.setValue(null);

        LiveData<List<Match>> matches = repository.getAllMatches(leagueId);

        observableMatch.addSource(matches,observableMatch::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final MatchRepository matchRepository;
        private String leagueId;

        public Factory(@NonNull Application application, String leagueId) {
            this.application = application;
            matchRepository = MatchRepository.getInstance();
            this.leagueId = leagueId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MatchListViewModel(application, matchRepository, leagueId);
        }
    }

    /**
     * Expose the LiveData Match query so the UI can observe it.
     */
    public LiveData<List<Match>> getMatches(){
        return observableMatch;
    }

    public void deleteMatches(List<Match> matches, OnAsyncEventListener listener){
        for(Match match : matches){
            repository.delete(match, listener,leagueId);
        }
    }
}
