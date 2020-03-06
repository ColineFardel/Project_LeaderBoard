package com.example.project_leaderboard.ui.league;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeagueViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LeagueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Leagues");
    }

    public LiveData<String> getText() {
        return mText;
    }
}