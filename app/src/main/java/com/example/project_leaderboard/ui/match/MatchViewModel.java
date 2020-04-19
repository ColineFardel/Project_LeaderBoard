package com.example.project_leaderboard.ui.match;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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

 public MatchViewModel (@NonNull Application application, MatchRepository matchRepository){
     super(application);

     repository=matchRepository;

     observableMatch = new MediatorLiveData<>();
     observableMatch.setValue(null);
 }

 public static class Factory extends ViewModelProvider.NewInstanceFactory{
     @NonNull
     private final Application application;

     private final MatchRepository repository;

     public Factory (@NonNull Application application){
         this.application=application;
         repository= MatchRepository.getInstance();
     }

     @Override
     public <T extends ViewModel> T create(Class<T> modelClass) {
         //noinspection unchecked
         return (T) new MatchViewModel(application, repository);
     }
 }

public void createMatch (Match match, OnAsyncEventListener callback){
     MatchRepository.getInstance().insert(match,callback);
}
public void updateMatch(Match match, OnAsyncEventListener callback){
     repository.update(match, callback);
}
/*
public void deleteMatch(Match match, OnAsyncEventListener callback){
     repository.delete(match,callback);
}

 */


}


