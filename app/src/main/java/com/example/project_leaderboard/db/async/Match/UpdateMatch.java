package com.example.project_leaderboard.db.async.Match;

import android.app.Application;
import android.os.AsyncTask;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.AppDatabase;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
/**
 * This class is used to update a match
 * @author Samuel Michellod
 */
public class UpdateMatch extends AsyncTask<Match,Void,Void> {

    private Application application;
    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateMatch(Application application, OnAsyncEventListener callback) {
        this.application=application;
        this.callback=callback;
    }

    @Override
    protected Void doInBackground(Match... matches) {
        try{
            database.matchDao().updateMatch();

        }catch (Exception e){
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute (Void aVoid){
        if(exception==null){
            callback.onSuccess();
        }
        else{
            callback.onFailure(exception);
        }
    }
}
