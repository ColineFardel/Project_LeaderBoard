package com.example.project_leaderboard.ui.match;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;


import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.MatchRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

public class AddMatchViewModel extends AndroidViewModel {

    private MatchRepository repository;
    private Application application;
    private MutableLiveData<String> mText;

    private final MediatorLiveData<Match> observableMatch;

    public AddMatchViewModel(@NonNull Application application, final int MatchId, MatchRepository matchRepository, OnAsyncEventListener callback, Context context) {
        super(application);
        this.application = application;
        repository = matchRepository;

        observableMatch = new MediatorLiveData<>();
        observableMatch.setValue(null);

      //  LiveData<Match> match = repository.insert(callback,context);

      //  observableMatch.addSource(match, observableMatch::setValue);
    }


    class Factory extends ViewModelProvider.NewInstanceFactory {
            @NonNull

            private final Application application;

            private final int MatchId;

            //   private final MatchRepository repository;

            public Factory(@NonNull Application application, int MatchId) {
                this.application = application;
                this.MatchId = MatchId;
            }
        }


    public MediatorLiveData<Match> getMatch() {
        return observableMatch;
    }

    public void createMatch(Match match, OnAsyncEventListener callback) {
        repository.insert(match, callback, application);
    }

    public void deleteMatch(Match match, OnAsyncEventListener callback) {
        repository.delete(match,callback,application);
    }
    public void updateMatch(Match match, OnAsyncEventListener callback) {
        repository.update(match, callback, application);
    }
}

