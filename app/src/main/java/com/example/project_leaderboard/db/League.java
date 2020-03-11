package com.example.project_leaderboard.db;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "league", indices = {@Index(value = {"NameLeague"}, unique = true)})
public class League{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "NameLeague")
    private String LeagueName;

    public League(String LeagueName) {
        this.LeagueName = LeagueName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeagueName() {
        return LeagueName;
    }

    public void setLeagueName(String LeagueName) {
        this.LeagueName = LeagueName;
    }
}
