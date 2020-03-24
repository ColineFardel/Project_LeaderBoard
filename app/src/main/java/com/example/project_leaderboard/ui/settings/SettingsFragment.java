package com.example.project_leaderboard.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

public class SettingsFragment extends Fragment {

    private SettingsViewModel toolsViewModel;
    private Switch mySwitch;
    SharedPref sharedPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView textView = root.findViewById(R.id.text_settings);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        sharedPref = new SharedPref(getContext());
        if(sharedPref.loadNightMode()==true){
            getContext().setTheme(R.style.NightTheme);
        }
        else{
            getContext().setTheme(R.style.AppTheme);
        }
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
    public void restartApp(){
        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}

