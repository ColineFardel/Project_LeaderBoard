package com.example.project_leaderboard.ui.club;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.project_leaderboard.R;

import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.util.OnAsyncEventListener;

public class ClubDetails extends AppCompatActivity {

    private static final String TAG = "ClubDetails";

    private static final int CREATE_CLUB = 0;
    private static final int EDIT_CLUB = 1;
    private static final int DELETE_CLUB = 2;

    private boolean isEditable;

    private EditText ClubName;
    private EditText Victories;
    private EditText Draws;
    private EditText Losses;
    private EditText Points;

    private Spinner LeagueSpinner;
    private EditText LeagueId;

    private Toast statusToast;
    private Application application;

    private ClubListViewModel viewModel;

    private Club club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_club);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initiateView();

      /*  String clientEmail = getIntent().getStringExtra("clientEmail");



        ClubViewModel.Factory factory = new ClubViewModel.Factory(getApplication(), clientEmail);
        viewModel = ViewModelProviders.of(this, factory).get(ClubViewModel.class);
        viewModel.getClubs().observe(this, Club -> {
            if (Club != null) {
                club = Club;
                updateContent();
            }
        });
        */

    }
    private void initiateView() {
        isEditable = false;
        ClubName = findViewById(R.id.club_name);
        LeagueSpinner = findViewById(R.id.league_spinner);

        ClubName.setFocusable(false);
        ClubName.setEnabled(false);
       LeagueSpinner.setFocusable(false);
       LeagueSpinner.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (club != null)  {
            menu.add(0, EDIT_CLUB, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_CLUB, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(0, CREATE_CLUB, Menu.NONE, getString(R.string.action_create_club))
                    .setIcon(R.drawable.ic_add_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_CLUB) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_edit_white_24dp);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_CLUB) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.delete(club, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteClub: success");
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteClub: failure", e);
                    }
                }, application);

                //  alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
                // alertDialog.show();
            });
        }
        if (item.getItemId() == CREATE_CLUB) {
            createClub(
                    ClubName.getText().toString(),
                    Victories.getId(),
                    Points.getId(),
                    Losses.getId(),
                    Draws.getId(),
                    LeagueId.getId()
            );
        }
        return super.onOptionsItemSelected(item);
    }

    private void createClub(String NameClub, int points, int victories, int losses, int draws, int LeagueId) {
        club = new Club(NameClub,points,victories,losses,draws,LeagueId);
        club.setNameClub(NameClub);
        club.setPoints(0);
        club.setVictories(0);
        club.setLosses(0);
        club.setDraws(0);

        club.setLeagueId(LeagueId);
        viewModel.insert(club, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createClub: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createClub: failure", e);
            }
        }, application);
    }



    private void switchEditableMode() {
        if (!isEditable) {
            ClubName.setFocusable(true);
            ClubName.setEnabled(true);
            ClubName.setFocusableInTouchMode(true);

            LeagueSpinner.setFocusable(true);
            LeagueSpinner.setEnabled(true);
            LeagueSpinner.setFocusableInTouchMode(true);

            ClubName.requestFocus();
            LeagueSpinner.requestFocus();
        } else {
            saveChanges(
                 ClubName.getText().toString(),
                 LeagueId.getId()
            );
            ClubName.setFocusable(false);
            ClubName.setEnabled(false);

           LeagueSpinner.setFocusable(false);
           LeagueSpinner.setEnabled(false);
        }
        isEditable = !isEditable;
    }





    private void saveChanges(String NameClub, int LeagueId) {
        club.setNameClub(NameClub);
        club.setPoints(0);
        club.setVictories(0);
        club.setLosses(0);
        club.setDraws(0);
        club.setLeagueId(LeagueId);

        viewModel.update(club, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "updateClub: success");
                        setResponse(true);
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "updateClient: failure", e);
                        setResponse(false);
                    }
                }, application);

}
    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, getString(R.string.club_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }
    }



