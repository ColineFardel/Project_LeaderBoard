package com.example.project_leaderboard.db.async.Club;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
/**
 * This class is used to create a club
 * @author Samuel Michellod
 */
public class CreateClub extends AsyncTask<Match,Void,Void> {


    private AppDatabase database;
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;
    private Context context;


    public CreateClub(Application application, OnAsyncEventListener callback) {
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
