package com.example.project_leaderboard.ui.match;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MatchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MatchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Leagues");
    }

    public LiveData<String> getText() {
        return mText;
    }
}


