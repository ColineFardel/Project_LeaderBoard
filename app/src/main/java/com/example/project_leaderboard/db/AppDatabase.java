package com.example.project_leaderboard.db;

import android.content.Context;
import android.os.AsyncTask;
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

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final LeagueDao leagueDao;
        private final MatchDao matchDao;
        private final ClubDao clubDao;

        public PopulateDbAsync(AppDatabase instance) {
            leagueDao = instance.leagueDao();
            matchDao = instance.matchDao();
            clubDao = instance.clubDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            League premier_league = new League("Premier League");
            League bundesliga = new League("Bundesliga");
            League ligue_1 = new League("Ligue 1");
            League serie_a = new League("Serie A");

            leagueDao.insertLeague(premier_league);
            leagueDao.insertLeague(bundesliga);
            leagueDao.insertLeague(ligue_1);
            leagueDao.insertLeague(serie_a);

            Club arsenal = new Club("Arsenal", 15, 5, 0, 0, 1);
            Club liverpool = new Club("Liverpool", 20, 5, 5, 0, 1);
            Club chelsea = new Club("Chelsea", 20, 5, 5, 0, 1);
            Club tottenham = new Club("Tottenham", 20, 5, 5, 0, 1);
            Club manchester_United = new Club("Manchester United", 20, 5, 5, 0, 1);
            Club manchester_City = new Club("Manchester City", 20, 5, 5, 0, 1);
            Club leicester = new Club("Leicester", 20, 5, 5, 0, 1);
            Club wolves = new Club("Wolves", 20, 5, 5, 0, 1);
            Club sheffield_united = new Club("Sheffield United", 20, 5, 5, 0, 1);
            Club burnley = new Club("Burnley", 20, 5, 5, 0, 1);
            Club crystal_palace = new Club("Crystal Palace", 20, 5, 5, 0, 1);
            Club everton = new Club("Everton", 20, 5, 5, 0, 1);
            Club newcastle = new Club("Newcastle", 20, 5, 5, 0, 1);
            Club southampton = new Club("Southampton", 20, 5, 5, 0, 1);
            Club brighton = new Club("Brighton", 20, 5, 5, 0, 1);
            Club west_ham = new Club("West Ham", 20, 5, 5, 0, 1);
            Club watford = new Club("Watford", 20, 5, 5, 0, 1);
            Club bournemouth = new Club("Bournemouth", 20, 5, 5, 0, 1);
            Club aston_villa = new Club("Aston Villa", 20, 5, 5, 0, 1);
            Club norwich = new Club("Norwich", 20, 5, 5, 0, 1);
            clubDao.insertAll(arsenal,liverpool,chelsea,tottenham,manchester_City,manchester_United,leicester,watford,wolves,sheffield_united
                    ,burnley,crystal_palace,everton,newcastle,sheffield_united,southampton,brighton,west_ham,bournemouth,aston_villa,norwich);
            return null;
        }
    }


}
