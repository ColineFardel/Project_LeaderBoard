package com.example.project_leaderboard.db.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;

import java.util.List;

public class ClubsByLeague {

    @Embedded
    public League league;

    @Relation(parentColumn = "LeagueId", entityColumn = "LeagueId", entity = Club.class)
    public List<Club> clubs;

}
