package com.example.project_leaderboard.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_leaderboard.R;

public class MatchFragment extends Fragment {

    private MatchViewModel matchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        matchViewModel =
                ViewModelProviders.of(this).get(MatchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_match, container, false);
        final TextView textView = root.findViewById(R.id.text_match);
        matchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}