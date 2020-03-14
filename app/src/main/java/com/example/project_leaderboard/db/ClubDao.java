package com.example.project_leaderboard.db;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClubDao {
    @Query("SELECT * FROM club")
    List<Club> getAll();

    @Query("SELECT * FROM club WHERE ClubId IN (:ClubIds)")
    List<Club> loadAllByIds(int[] ClubIds);

    @Query("SELECT * FROM club WHERE NameClub LIKE :ClubName LIMIT 1")
    Club findByName(String ClubName);

    @Insert
    void insertAll(Club... club) throws SQLiteConstraintException;

    @Update
    void updateFruits(Club... club);

    @Delete
    void delete(Club club);

    @Query("DELETE FROM club")
    void deleteAll();
}
