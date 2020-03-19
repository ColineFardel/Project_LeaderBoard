package com.example.project_leaderboard.db.async;

import android.content.Context;

import com.example.project_leaderboard.db.entity.Match;
import com.example.project_leaderboard.db.repository.AppDatabase;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

public class UpdateMatch {


    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateMatch(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getAppDatabase(context);
        this.callback=callback;
    }

    public void execute(Match match) {
    }
}
