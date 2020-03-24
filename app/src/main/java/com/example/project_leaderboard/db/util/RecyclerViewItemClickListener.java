package com.example.project_leaderboard.db.util;

import android.view.View;

public interface RecyclerViewItemClickListener {

    void onIemClick(View v, int position);
    void onItemLongClick(View v, int position);
}
