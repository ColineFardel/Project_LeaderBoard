package com.example.project_leaderboard.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Data access object class for matches
 * @author Samuel Michellod
 */

public class Match{

    private String MatchId;
    private String IdClubHome;
    private String IdClubVisitor;
    private int ScoreHome;
    private int ScoreVisitor;
    private String IdLeague;

    public Match(String IdClubHome, String IdClubVisitor, int ScoreHome, int ScoreVisitor,String IdLeague, String MatchId){
        this.IdClubHome=IdClubHome;
        this.IdClubVisitor=IdClubVisitor;
        this.ScoreHome=ScoreHome;
        this.ScoreVisitor=ScoreVisitor;
        this.IdLeague=IdLeague;
        this.MatchId=MatchId;
    }

    @Exclude
    public String getMatchId(){
        return  MatchId;
    }

    public void setMatchId(String matchId){
        MatchId=matchId;
    }

    public String getIdLeague(){
        return IdLeague;
    }

    public void setIdLeague (String idLeague){
        IdLeague=idLeague;
    }


    public String getIdClubHome() {
        return IdClubHome;
    }

    public void setIdClubHome(String IdClubHome) {
        IdClubHome = IdClubHome;
    }

    public String getIdClubVisitor() {
        return IdClubVisitor;
    }

    public void setIdClubVisitor(String IdClubVisitor) {
        IdClubVisitor = IdClubVisitor;
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


    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    public boolean equals (Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if(!(obj instanceof Match)) return false;
        Match m = (Match) obj;
        return m.getMatchId().equals(this.getMatchId());
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Club Home", IdClubHome);
        result.put("Club Visitor", IdClubVisitor);
        result.put("Score Home",ScoreHome);
        result.put("Score Visitor", ScoreVisitor);
        result.put("League", IdLeague);
        return result;
    }


}
