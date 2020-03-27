package com.example.project_leaderboard.ui.match;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.project_leaderboard.db.dao.MatchDao;
import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.AppDatabase;
import com.example.project_leaderboard.db.repository.MatchRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

import java.util.List;

public class AddMatchViewModel extends AndroidViewModel {

    private MatchRepository repository;
    private Application application;
    private MutableLiveData<String> mText;

    //private final MediatorLiveData<Match> observableMatch;
    private MatchDao matchDao;
    private AppDatabase db;
    private LiveData<List<Match>> mAllMatches;
    private String TAG = this.getClass().getSimpleName();


    public AddMatchViewModel (Application application){
        super(application);
        db= AppDatabase.getInstance(application);
        matchDao = db.matchDao();
        mAllMatches = (LiveData<List<Match>>) matchDao.getAll();
    }


    public LiveData<String> getText() {
        return mText;
    }

    public void insert (Match match){
        new InsertAsyncTask(matchDao).execute(match);
    }

    public LiveData <List<Match>> getAllMatches(){
        return mAllMatches;
    }

    public void update(Match match, OnAsyncEventListener onAsyncEventListener){
        new UpdateAsyncTask(matchDao).execute(match);
    }

    public void delete(Match match, OnAsyncEventListener onAsyncEventListener){
        new DeleteAsyncTask(matchDao).execute(match);
    }
    @Override
    protected void onCleared(){
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    class OperationAsyncTask extends AsyncTask<Match,Void,Void>{
        MatchDao mAsyncTaskDao;

        OperationAsyncTask(MatchDao dao){
            this.mAsyncTaskDao=dao;
        }
        @Override
        protected Void doInBackground (Match ... matches){
            return null;
        }
    }


    class InsertAsyncTask extends OperationAsyncTask{
        InsertAsyncTask(MatchDao mMathcDao){
            super(mMathcDao);
        }
        @Override
        protected Void doInBackground (Match ... matches){
            mAsyncTaskDao.insertAll(matches[0]);
            return null;
        }
    }
    class UpdateAsyncTask extends OperationAsyncTask{
        UpdateAsyncTask(MatchDao matchDao){
            super(matchDao);
        }
        @Override
        protected Void doInBackground (Match ... matches){
            mAsyncTaskDao.updateMatch(matches[0]);
            return null;
        }
    }

class DeleteAsyncTask extends OperationAsyncTask{
        public DeleteAsyncTask(MatchDao matchDao){
            super(matchDao);
        }
        @Override
    protected Void doInBackground (Match ... matches){
            mAsyncTaskDao.delete(matches[0]);
            return null;
        }
}
  /*  public AddMatchViewModel(@NonNull Application application, final int MatchId, MatchRepository matchRepository, OnAsyncEventListener callback, Context context) {
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

 */
}

