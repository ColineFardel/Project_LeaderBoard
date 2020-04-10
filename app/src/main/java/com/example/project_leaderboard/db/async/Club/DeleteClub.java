package com.example.project_leaderboard.db.async.Club;

import android.app.Application;
import android.os.AsyncTask;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
/**
 * This class is used to delete a club
 * @author Samuel Michellod
 */
public class DeleteClub extends AsyncTask<Match,Void,Void> {

    AppDatabase database;
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;


    public DeleteClub(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback=callback;
    }

    public void execute(Match match) {
    }

    @Override
    protected Void doInBackground(Match... matches) {
        try{
            database.matchDao().deleteAll();

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
