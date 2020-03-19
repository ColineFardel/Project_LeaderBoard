package com.example.project_leaderboard.db.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.AppDatabase;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

public class CreateMatch extends AsyncTask<Match,Void,Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;


    public CreateMatch(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getAppDatabase(context);
        this.callback=callback;
    }

    public void execute(Match match) {
    }

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
            callback.onFeilure(exception);
        }
    }
}
