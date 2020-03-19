package com.example.project_leaderboard.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "match")
public class Match {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "IdClubHome")
    private int IdClubHome;

    @ColumnInfo(name = "IdClubVisitor")
    private int IdClubVisitor;

    @ColumnInfo(name = "ScoreHome")
    private int ScoreHome;

    @ColumnInfo(name = "ScoreVisitor")
    private int ScoreVisitor;

    public Match(int IdClubHome, int IdClubVisitor, int ScoreHome, int ScoreVisitor){
        this.IdClubHome=IdClubHome;
        this.IdClubVisitor=IdClubVisitor;
        this.ScoreHome=ScoreHome;
        this.ScoreVisitor=ScoreVisitor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClubHome() {
        return IdClubHome;
    }

    public void setIdClubHome(int idClubHome) {
        IdClubHome = idClubHome;
    }

    public int getIdClubVisitor() {
        return IdClubVisitor;
    }

    public void setIdClubVisitor(int idClubVisitor) {
        IdClubVisitor = idClubVisitor;
    }

    public int getScoreHome() {
        return ScoreHome;
    }

    public void setScoreHome(int scoreHome) {
        ScoreHome = scoreHome;
    }

    public int getScoreVisitor() {
        return ScoreVisitor;
    }

    public void setScoreVisitor(int scoreVisitor) {
        ScoreVisitor = scoreVisitor;
    }
}
