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
    private String ClubId;
    private String NameClub;
    private String LeagueId;
    private int points;
    private int wins;
    private int losses;
    private int draws;


    public Club (String ClubId, String NameClub,String LeagueId, int points, int wins, int losses, int draws){
        this.ClubId=ClubId;
        this.NameClub=NameClub;
        this.LeagueId=LeagueId;
        this.points=points;
        this.wins=wins;
        this.losses=losses;
        this.draws=draws;
    }


    public String getLeagueId(){
        return LeagueId;
    }

    public void setLeagueId(String leagueId){
        this.LeagueId=LeagueId;
    }

    @Exclude
    public String getClubId() {
        return ClubId;
    }

    public void setClubId(String ClubId) {
        this.ClubId = ClubId;
    }

    public String getNameClub() {
        return NameClub;
    }

    public void setNameClub(String NameClub) {
        this.NameClub = NameClub;
    }

    public int getPoints(){return points;}

    public void setPoints (Integer points) {this.points=points;};

    public int getVictories() {
        return wins;
    }

    public void setVictories(int victories) {
        this.wins = wins;
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
        result.put("Name",NameClub);
        result.put("Id", ClubId);
        result.put("Points", points);
        result.put("Wins", wins);
        result.put("Losses", losses);
        result.put("Draws", draws);

        return result;
 }

}

