package com.example.project_leaderboard.ui.match;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project_leaderboard.db.dao.MatchDao;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.AppDatabase;
import com.example.project_leaderboard.db.repository.MatchRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.List;

public class MatchViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MatchRepository repository;
    private Application application;


    //private final MediatorLiveData<Match> observableMatch;
    private MatchDao matchDao;
    private AppDatabase db;
    private LiveData<List<Match>> mAllMatches;
    private String TAG = this.getClass().getSimpleName();


    public MatchViewModel(Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        matchDao = db.matchDao();
        mAllMatches = (LiveData<List<Match>>) matchDao.getAll();
        mText = new MutableLiveData<>();
        mText.setValue("Add Match");
    }
/*
     public MatchViewModel(Application application) {
         mText = new MutableLiveData<>();
         mText.setValue("Leagues");
     }
*/
    public LiveData<String> getText() {
        return mText;
    }

    public void insert(Match match) {
        new MatchViewModel.InsertAsyncTask(matchDao).execute(match);
    }

    public LiveData<List<Match>> getAllMatches() {
        return mAllMatches;
    }

    public void update(Match match, OnAsyncEventListener onAsyncEventListener) {
        new MatchViewModel.UpdateAsyncTask(matchDao).execute(match);
    }

    public void delete(Match match, OnAsyncEventListener onAsyncEventListener) {
        new MatchViewModel.DeleteAsyncTask(matchDao).execute(match);
    }
/*
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }
*/
    public class OperationAsyncTask extends AsyncTask<Match, Void, Void> {
        MatchDao mAsyncTaskDao;

        OperationAsyncTask(MatchDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Match... matches) {
            return null;
        }
    }


    private class InsertAsyncTask extends MatchViewModel.OperationAsyncTask {
        InsertAsyncTask(MatchDao mMathcDao) {
            super(mMathcDao);
        }

        @Override
        protected Void doInBackground(Match... matches) {
            mAsyncTaskDao.insertAll(matches[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends MatchViewModel.OperationAsyncTask {
        UpdateAsyncTask(MatchDao matchDao) {
            super(matchDao);
        }

        @Override
        protected Void doInBackground(Match... matches) {
            mAsyncTaskDao.updateMatch(matches[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends MatchViewModel.OperationAsyncTask {
        public DeleteAsyncTask(MatchDao matchDao) {
            super(matchDao);
        }

        @Override
        protected Void doInBackground(Match... matches) {
            mAsyncTaskDao.delete(matches[0]);
            return null;
        }
    }
}

