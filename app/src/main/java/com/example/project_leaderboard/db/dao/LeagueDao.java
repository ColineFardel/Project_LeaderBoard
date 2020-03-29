package com.example.project_leaderboard.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_leaderboard.db.entity.League;

import java.util.List;
@Dao
public interface LeagueDao {

    @Query("SELECT * FROM league")
    LiveData<List<League>> getAllLeagues();

    @Query("SELECT * FROM league WHERE LeagueId IN (:LeagueIds)")
    List<League> loadAllByIds(int[] LeagueIds);

    @Query("SELECT NameLeague FROM league")
    LiveData<List<String>> GetLeagueName();

    @Insert
    void insertLeague(League league) throws SQLiteConstraintException;

    @Update
    void updateLeague(League league);

    @Delete
    void deleteLeague(League league);

    @Query("DELETE FROM league")
    void deleteAll();
}
