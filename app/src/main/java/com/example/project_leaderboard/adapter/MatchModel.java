package com.example.project_leaderboard.adapter;

import com.example.project_leaderboard.db.entity.Match;

/**
 * This class is used to select multiple matches
 * @author Coline Fardel
 */
public class MatchModel {

    private Match match;
    private boolean isSelected;

    public MatchModel(Match match){
        this.match = match;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public Match getMatch(){
        return match;
    }
}
