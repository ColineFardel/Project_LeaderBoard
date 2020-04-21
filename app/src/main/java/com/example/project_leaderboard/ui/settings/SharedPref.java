package com.example.project_leaderboard.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class is used to manage the preferences for the user
 * @author Coline Fardel
 */
public class SharedPref {
    SharedPreferences mySharedPref;
    public SharedPref(Context context){
        mySharedPref = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    public void setLanguage(String languageToLoad){
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString("LanguageToLoad",languageToLoad);
        editor.commit();
    }

    public void setNightMode(Boolean state){
        SharedPreferences.Editor editor =  mySharedPref.edit();
        editor.putBoolean("NightMode",state);
        editor.commit();
    }

    public Boolean loadNightMode(){
        Boolean state = mySharedPref.getBoolean("NightMode",false);
        return state;
    }

    public String getLanguage(){
        String languageToLoad = mySharedPref.getString("LanguageToLoad","en");
        return  languageToLoad;
    }
}
