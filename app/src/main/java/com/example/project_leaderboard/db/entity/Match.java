package com.example.project_leaderboard.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;
/**
 * Data access object class for matches
 * @author Samuel Michellod
 */
@Entity
        (foreignKeys = {
        @ForeignKey(
                entity = Club.class,
                parentColumns = "ClubId",
                childColumns = "NameClubHome"
        ),
        @ForeignKey(
                entity = Club.class,
                parentColumns = "ClubId",
                childColumns = "NameClubVisitor"
        )
}, indices = {@Index(value = {"NameClubHome"}), @Index(value = {"NameClubVisitor"})})
public class Match implements Comparable{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "IdLeague")
    private int IdLeague;


    @ColumnInfo(name = "NameClubHome")
    private String NameClubHome;

    @ColumnInfo(name = "NameClubVisitor")
    private String NameClubVisitor;

    @ColumnInfo(name = "ScoreHome")
    private int ScoreHome;

    @ColumnInfo(name = "ScoreVisitor")
    private int ScoreVisitor;

    public Match(String NameClubHome, String NameClubVisitor, int ScoreHome, int ScoreVisitor,int IdLeague){
        this.NameClubHome=NameClubHome;
        this.NameClubVisitor=NameClubVisitor;
        this.ScoreHome=ScoreHome;
        this.ScoreVisitor=ScoreVisitor;
        this.IdLeague=IdLeague;
    }

    public int getId() {
        return id;
    }

    public int getIdLeague(){
        return IdLeague;
    }

    public void setIdLeague (int idLeague){
        IdLeague=idLeague;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameClubHome() {
        return NameClubHome;
    }

    public void setNameClubHome(String NameClubHome) {
        NameClubHome = NameClubHome;
    }

    public String getNameClubVisitor() {
        return NameClubVisitor;
    }

    public void setNameClubVisitor(String NameClubVisitor) {
        NameClubVisitor = NameClubVisitor;
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
