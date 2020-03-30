package com.example.project_leaderboard.ui.match;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_leaderboard.BaseApp;
import com.example.project_leaderboard.db.dao.MatchDao;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.repository.MatchRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.List;

public class MatchViewModel extends AndroidViewModel {

 private MatchRepository repository;

 private Application application;

 private final MediatorLiveData<Match> observableMatch;

 public MatchViewModel (@NonNull Application application, MatchRepository matchRepository){
     super(application);
     this.application=application;
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
         repository= ((BaseApp)application).getMatchRepository();
     }
 }

 public void  getAllMatches (){
        repository.getAllMatches();

 }

public void createMatch (Match match, OnAsyncEventListener callback){
     repository.insert(match,callback,application);
}
public void updateMatch(Match match, OnAsyncEventListener callback){
     repository.update(match, callback,application);
}
public void deleteMatch(Match match, OnAsyncEventListener callback){
     repository.delete(match,callback,application);
}


}


