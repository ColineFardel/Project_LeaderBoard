package com.example.project_leaderboard.db.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_leaderboard.db.entity.Match;

import java.util.List;

@Dao
public interface MatchDao {
    @Query("SELECT * FROM `match`")
    LiveData<List<Match> >getAll();

    @Query("SELECT * FROM `match`")
    List<Match> getMatchbyClub();

    @Query("SELECT * FROM `match` WHERE id IN (:MatchIds)")
    List<Match> loadAllByIds(int[] MatchIds);


    @Insert
    void insertAll(Match... match) throws SQLiteConstraintException;

    @Update
    void updateMatch(Match... match);

    @Delete
    void delete(Match match);

    @Query("DELETE FROM `match`")
    void deleteAll();

}
