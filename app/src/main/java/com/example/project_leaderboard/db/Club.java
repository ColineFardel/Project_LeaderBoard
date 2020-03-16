package com.example.project_leaderboard.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.jar.Attributes;

@Entity(tableName = "club", indices = {@Index(value = {"NameClub"}, unique = true)})
public class Club {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "NameClub")
    private String NameClub;

    @ColumnInfo (name="Points")
    private int points;

    @ColumnInfo (name="Victories")
    private int victories;

    @ColumnInfo(name="Losses")
    private int losses;

    @ColumnInfo(name="Draws")
    private int draws;


    public Club(String NameClub, int points, int victories,int losses, int draws) {
        this.NameClub = NameClub;
        this.points=points;
        this.victories=victories;
        this.losses=losses;
        this.draws=draws;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameClub() {
        return NameClub;
    }

    public void setNameClub(String NameClub) {
        this.NameClub = NameClub;
    }

    public int getPoints(){return points;}

    public void setPoints (int points) {this.points=points;};

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getLosses() {
        return losses;
    }
    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
}

