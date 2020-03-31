
package com.example.project_leaderboard.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
/**
 * ViewModel class for the settings
 * @author Coline Fardel
 */
public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Settings");
    }

    public LiveData<String> getText() {
        return mText;
    }
}