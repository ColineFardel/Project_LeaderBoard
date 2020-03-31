package com.example.project_leaderboard.ui.settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.project_leaderboard.MainActivity;
import com.example.project_leaderboard.R;
import java.util.Locale;

/**
 * This class is used to display the settings page
 * @author Coline Fardel
 */
public class SettingsFragment extends Fragment {

    private Switch mySwitch;
    SharedPref sharedPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /**
         * Loading the language from the preferences
         */
        sharedPref = new SharedPref(getContext());
        String languageToLoad = sharedPref.getLanguage();
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        DisplayMetrics dm= getResources().getDisplayMetrics();
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, dm);

        /**
         * Loading the Night mode from the preferences
         */
        if(sharedPref.loadNightMode()==true){
            getContext().setTheme(R.style.NightTheme);
        }
        else{
            getContext().setTheme(R.style.AppTheme);
        }

        /**
         * Customized spinner
         */
        View root = inflater.inflate(R.layout.fragment_settings,container,false);
        Spinner coloredSpinner = root.findViewById(R.id.langue_spinner);
        String [] list = getResources().getStringArray(R.array.language);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        coloredSpinner.setAdapter(adapter);

        //Loading the spinner default position
        if(sharedPref.getLanguage().equals("fr")){
            coloredSpinner.setSelection(0);
        }
        else{
            coloredSpinner.setSelection(1);
        }

        /**
         * Setting the language in the preferences
         */
        coloredSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    String languageToLoad = "fr";
                    if(!sharedPref.getLanguage().equals(languageToLoad)){
                        sharedPref.setLanguage(languageToLoad);
                        restartApp();
                    }
                    sharedPref.setLanguage(languageToLoad);
                }
                if(position==1){
                    String languageToLoad = "en";
                    if(!sharedPref.getLanguage().equals(languageToLoad)){
                        sharedPref.setLanguage(languageToLoad);
                        restartApp();
                    }
                    sharedPref.setLanguage(languageToLoad);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Setting the night mode preferences
         */
        mySwitch = root.findViewById(R.id.night_switch);
        if(sharedPref.loadNightMode()==true){
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedPref.setNightMode(true);
                    restartApp();
                }
                else{
                    sharedPref.setNightMode(false);
                    restartApp();
                }
            }
        });

        return root;
    }

    /**
     * Methode to restart the activity
     */
    public void restartApp(){
        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}

