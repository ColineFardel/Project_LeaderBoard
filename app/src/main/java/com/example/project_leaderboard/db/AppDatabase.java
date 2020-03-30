package com.example.project_leaderboard.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project_leaderboard.db.dao.ClubDao;
import com.example.project_leaderboard.db.dao.LeagueDao;
import com.example.project_leaderboard.db.dao.MatchDao;
import com.example.project_leaderboard.db.entity.Club;
import com.example.project_leaderboard.db.entity.League;
import com.example.project_leaderboard.db.entity.Match;

import java.util.concurrent.Executors;


@Database(entities = {League.class, Club.class, Match.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static final String DATABASE_NAME = "leaderboardDB";
    private static AppDatabase instance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract LeagueDao leagueDao();

    public abstract MatchDao matchDao();

    public abstract ClubDao clubDao();


    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                   instance = buildDatabase(context.getApplicationContext());
                   instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;

    }

    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            initializeDemoData(database);
                            DatabaseInitializer.populateDatabase(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    public static void initializeDemoData(final AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                Log.i(TAG, "Wipe database.");
              //  database.matchDao().deleteAll();
               // database.leagueDao().deleteAll();
             //   database.clubDao().deleteAll();

                DatabaseInitializer.populateDatabase(database);
            });
        });
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }


    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }


    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }




}
