package com.example.project_leaderboard.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Club.class,
                parentColumns = "ClubId",
                childColumns = "IdClubHome"
        ),
        @ForeignKey(
                entity = Club.class,
                parentColumns = "ClubId",
                childColumns = "IdClubVisitor"
        )
})
public class Match implements Comparable{

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

    @Override
    public String toString(){
        return "Home " + ScoreHome + "- "+ "Visitonr" + ScoreVisitor;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }
}
