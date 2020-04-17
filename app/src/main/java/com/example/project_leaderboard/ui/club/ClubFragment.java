package com.example.project_leaderboard.ui.club;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.project_leaderboard.MainActivity;
import com.example.project_leaderboard.R;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.repository.LeagueRepository;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;
import com.example.project_leaderboard.ui.league.LeagueViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to add a new club
 * @author Coline Fardel
 */
public class ClubFragment extends Fragment {

    private ClubViewModel clubViewModel;
    private Club club;
    private EditText name_edittext;
    private Button addclub;
    private Spinner spinner;
    DatabaseReference databaseReference;
    private static final String TAG = "ClubFragment";




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_club,container,false);

         /*
        clubViewModel =
                ViewModelProviders.of(this).get(ClubViewModel.class);
        View root = inflater.inflate(R.layout.fragment_club, container, false);
        final TextView textView = root.findViewById(R.id.text_club);
        clubViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        name_edittext = view.findViewById(R.id.club_name_edittext);
        addclub = view.findViewById(R.id.button_add_addclub);

         // Get the LeagueName from firebase in the spinner
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("League").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> leagues = new ArrayList<String>();
                for(DataSnapshot leagueSnapshot: dataSnapshot.getChildren()){
                    String leagueName = leagueSnapshot.child("LeagueName").getValue(String.class);
                    leagues.add(leagueName);
                }
                Spinner spinner = (Spinner) view.findViewById(R.id.league_spinner_modify_match);
                ArrayAdapter<String> leagueAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,leagues);
                leagueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(leagueAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        /**
         * Customized spinner
         */
        Spinner leagueSpinner = view.findViewById(R.id.league_spinner_modify_match);
        String [] list = getResources().getStringArray(R.array.league);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_dropdown_layout,list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        leagueSpinner.setAdapter(adapter);

        /**
         * Looking for arguments from previous activity
         */
        Bundle b = getArguments();
        String league;
        if(b!=null){
            league =b.getString("League");
            switch (league){
                case "Premier league":
                    leagueSpinner.setSelection(0);
                    break;
                case "Bundesliga":
                    leagueSpinner.setSelection(1);
                    break;
                case "Ligue 1":
                    leagueSpinner.setSelection(2);
                    break;
                case "Serie A":
                    leagueSpinner.setSelection(3);
                    break;
            }
        }

        /**
         * Setting the action for cancel button
         */
        Button cancel_button = view.findViewById(R.id.button_cancel_addclub);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        /**
         * Setting the action for add button
         */
        /*
        name_edittext = getActivity().findViewById(R.id.club_name_edittext);
        LeagueRepository repository = new LeagueRepository();
        LeagueViewModel leagueViewModel = new LeagueViewModel(getActivity().getApplication(),repository);
         */
        Button add_button = view.findViewById(R.id.button_add_addclub);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Club");
        club= new Club();
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            club.setNameClub(name_edittext.getText().toString().trim());
            club.setWins(0);
            club.setDraws(0);
            club.setLosses(0);
            club.setPoints(0);
            club.setLeagueId(spinner.getSelectedItem().toString().trim());
            databaseReference.push().setValue(club);
            Toast.makeText(getContext(),"Club inserted successfully",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    /**
     * Method the create the club in the database
     * @param
     * @param
     */
   /* private void createClub(Club club, OnAsyncEventListener callback ) {
      clubViewModel.createClub(club,callback);
      club.setDraws(0);
      club.setLosses(0);
      club.setWins(0);
      club.setPoints(0);
    }

    */





}