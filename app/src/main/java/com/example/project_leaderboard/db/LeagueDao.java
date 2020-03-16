package com.example.project_leaderboard.db;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface LeagueDao {

    @Query("SELECT * FROM league")
    List<League> getAll();

    @Query("SELECT * FROM league WHERE id IN (:LeagueIds)")
    List<League> loadAllByIds(int[] LeagueIds);

    @Query("SELECT * FROM league WHERE NameLeague LIKE :LeagueName LIMIT 1")
    League findByName(String LeagueName);

    @Insert
    void insertAll(League... league) throws SQLiteConstraintException;

    @Update
    void updateFruits(League... league);

    @Delete
    void delete(League league);

    @Query("DELETE FROM league")
    void deleteAll();
}
