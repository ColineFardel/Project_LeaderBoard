package com.example.project_leaderboard.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {League.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    public abstract LeagueDao leagueDao();

    public synchronized static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "league-database")
                            /*
                            allow queries on the main thread.
                            Don't do this in a real app!
                            See PersistenceBasicSample
                            https://github.com/googlesamples/android-architecture-components/tree/master/BasicSample
                            for an example.

                            Would throw java.lang.IllegalStateException:
                            Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                            */
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
