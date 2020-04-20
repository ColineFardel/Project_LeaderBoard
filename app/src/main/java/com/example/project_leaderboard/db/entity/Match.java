package com.example.project_leaderboard.db.entity;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

/**
 * Data access object class for matches
 * @author Samuel Michellod
 */

public class Match{

    private String matchId;
    private String idClubHome;
    private String idClubVisitor;
    private int scoreHome;
    private int scoreVisitor;
    private String idLeague;

    public Match(){
    }

    /**
     * Getters
     */
    @Exclude
    public String getMatchId(){
        return matchId;
    }
    @Exclude
    public String getIdLeague(){
        return idLeague;
    }
    public String getIdClubHome() {
        return idClubHome;
    }
    public String getIdClubVisitor() {
        return idClubVisitor;
    }
    public int getScoreHome() {
        return scoreHome;
    }
    public int getScoreVisitor() {
        return scoreVisitor;
    }
    /**
     * Setters
     */
    public void setMatchId(String matchId){
        this.matchId =matchId;
    }
    public void setIdLeague (String idLeague){
        this.idLeague =idLeague;
    }
    public void setIdClubHome(String IdClubHome) {
        idClubHome = IdClubHome;
    }
    public void setIdClubVisitor(String IdClubVisitor) {
        idClubVisitor = IdClubVisitor;
    }
    public void setScoreHome(int scoreHome) {
        this.scoreHome = scoreHome;
    }
    public void setScoreVisitor(int scoreVisitor) {
        this.scoreVisitor = scoreVisitor;
    }

    @Override
    public String toString(){
        return "Home " + scoreHome + "- "+ "Visitor" + scoreVisitor;
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
        result.put("Club Home", idClubHome);
        result.put("Club Visitor", idClubVisitor);
        result.put("scoreHome", scoreHome);
        result.put("scoreVisitor", scoreVisitor);
        return result;
    }
}
