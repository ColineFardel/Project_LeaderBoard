package com.example.project_leaderboard.db.entity;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "league", indices = {@Index(value = {"NameLeague"}, unique = true)})
public class League{

    @PrimaryKey(autoGenerate = true)
    private int LeagueId;

    @ColumnInfo(name = "NameLeague")
    private String LeagueName;

    public League(String LeagueName) {
        this.LeagueName = LeagueName;
    }

    public int getLeagueId() {
        return LeagueId;
    }

    public void setLeagueId(int id) {
        this.LeagueId = LeagueId;
    }

    public String getLeagueName() {
        return LeagueName;
    }

    public void setLeagueName(String LeagueName) {
        this.LeagueName = LeagueName;
    }
}
