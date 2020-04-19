package com.example.project_leaderboard.adapter;

import com.example.project_leaderboard.db.entity.Club;

/**
 * This class is used to select multiple clubs
 * @author Coline Fardel
 */
public class ClubModel {
    private Club club;
    private boolean isSelected;

    public ClubModel(Club club){
        this.club = club;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public Club getClub(){
        return club;
    }
}
