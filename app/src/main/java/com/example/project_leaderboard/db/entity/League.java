package com.example.project_leaderboard.db.entity;

import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

/**
 * Data access object class for leagues
 * @author Samuel Michellod
 */

public class League{

    private String LeagueId;
    private String LeagueName;

    public League() {
    }

    /**
     * Getters
     */
    @Exclude
    public String getLeagueId() {
        return LeagueId;
    }
    public String getLeagueName() {
        return LeagueName;
    }
    /**
     * Setters
     */
    public void setLeagueId(String LeagueId) {
        this.LeagueId = LeagueId;
    }
    public void setLeagueName(String LeagueName) {
        this.LeagueName = LeagueName;
    }

    public boolean equals (Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if(!(obj instanceof League)) return false;
        League l = (League) obj;
        return l.getLeagueId().equals(this.getLeagueId());
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap();
        result.put("LeagueName", LeagueName);
        return result;
    }
}