package com.example.project_leaderboard.db.util;

import android.view.View;

public interface RecyclerViewItemClickListener {

    void onItemLongClick(View v, int position);

    void onItemClick(View view, int position);
}
