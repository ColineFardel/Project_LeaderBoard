package com.example.project_leaderboard.db.entity;


import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity class for clubs
 * @author Samuel Michellod
 */
public class Club {
    private String clubId;
    private String nameClub;
    private String leagueId;
    private int points;
    private int wins;
    private int losses;
    private int draws;

    public Club (){
    }

    /**
     * Getters
     */
    @Exclude
    public String getClubId() {
        return clubId;
    }
    @Exclude
    public String getLeagueId(){
        return leagueId;
    }
    public String getNameClub() {
        return nameClub;
    }
    public int getLosses() {
        return losses;
    }
    public int getDraws() {
        return draws;
    }
    public int getPoints(){
        return points;
    }
    public int getWins() {
        return wins;
    }
    /**
     * Setters
     */
    public void setLeagueId(String leagueId){
        this.leagueId = leagueId;
    }
    public void setClubId(String ClubId) {
        this.clubId = ClubId;
    }
    public void setNameClub(String NameClub) {
        this.nameClub = NameClub;
    }
    public void setPoints () {
        points = getWins()*3+getDraws();
    };
    public void setWins(int wins) {
        this.wins = wins;
    }
    public void setLosses(int losses) {
        this.losses = losses;
    }
    public void setDraws(int draws) {
        this.draws = draws;
    }

    public boolean equals (Object obj){
        if(obj==null) return false;
        if(obj==this) return true;
        if(!(obj instanceof Club)) return false;
        Club c = (Club) obj;
        return c.getClubId().equals(this.getClubId());
    }

    public int compareTo(@NonNull Object o){
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("nameClub", nameClub);
        //result.put("clubId", clubId);
        result.put("points", points);
        result.put("wins", wins);
        result.put("losses", losses);
        result.put("draws", draws);

        return result;
 }
}

