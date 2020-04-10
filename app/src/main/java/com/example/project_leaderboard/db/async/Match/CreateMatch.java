package com.example.project_leaderboard.db.async.Match;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

public class CreateMatch extends AsyncTask<Match,Void,Void> {
    /**
     * This class is used to create a match
     * @author Samuel Michellod
     */
    private AppDatabase database;
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;
    private Context context;


    public CreateMatch(Application application, OnAsyncEventListener callback) {
        this.application=application;
        this.callback=callback;
    }

  /*  public void execute(Match match) {
    }
*/
    @Override
    protected Void doInBackground(Match... matches) {
        try{
            database.matchDao().insertAll();

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
