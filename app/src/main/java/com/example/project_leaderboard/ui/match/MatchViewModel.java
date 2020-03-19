package com.example.project_leaderboard.ui.match;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.MatchRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.List;

public class MatchViewModel extends ViewModel{

    MatchRepository repository;
    Context context;

    private MutableLiveData<String> mText;

    public MatchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Match");
    }

    public LiveData<String> getText() {
        return mText;
    }



   /* public MatchViewModel(@NonNull Application application, final Integer id, MatchRepository matchRepository){

         repository = matchRepository;
        MediatorLiveData<Object> observanbleMatch = new MediatorLiveData<>();

    }
   public LiveData<List<Match>> match = repository.getAllMatch(context);

    public void createMatch (Match match, OnAsyncEventListener callback){
        repository.insert(match,callback,context);
    }
    public void updateMatch (Match match, OnAsyncEventListener callback){
        repository.update(match,callback,context);
    }

    public void deleteMatch (Match match,OnAsyncEventListener callback){
            repository.delete(match,callback,context);
    }

    */
}

