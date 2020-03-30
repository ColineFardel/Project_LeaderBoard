package com.example.project_leaderboard.db.entity;


import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = @ForeignKey(entity = League.class, parentColumns = "LeagueId", childColumns = "LeagueId"), indices = @Index(value = {"LeagueId"}))
public class Club {

    @PrimaryKey(autoGenerate = true)
    private int ClubId;

    @ColumnInfo(name = "NameClub")
    private String NameClub;

    @ColumnInfo(name="LeagueId")
    private int LeagueId;

    @ColumnInfo (name="Points")
    private int points;

    @ColumnInfo (name="Victories")
    private int victories;

    @ColumnInfo(name="Losses")
    private int losses;

    @ColumnInfo(name="Draws")
    private int draws;


    public Club(String NameClub, @Nullable int points,@Nullable int victories, @Nullable int losses, @Nullable int draws, int LeagueId) {
        this.NameClub = NameClub;
        this.LeagueId=LeagueId;
    }



    public int getLeagueId(){
        return LeagueId;
    }

    public void setLeagueId(int leagueId){
        this.LeagueId=LeagueId;
    }

    public int getClubId() {
        return ClubId;
    }

    public void setClubId(int id) {
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

