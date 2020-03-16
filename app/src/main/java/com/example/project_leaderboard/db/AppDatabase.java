package com.example.project_leaderboard.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {League.class, Club.class, Match.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    // For Singleton instantiation
    private static final Object LOCK = new Object();


    public synchronized static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "leaderboardDB")
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

    public abstract LeagueDao leagueDao();

    public abstract MatchDao matchDao();

    public abstract ClubDao clubDao();


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
            Club bayern = new Club("Bayern",30,9,0,3,2);
            Club dortmund = new Club("Dortmund",30,9,0,3,2);
            Club leipzig = new Club("Leipzig",30,9,0,3,2);
            Club leverkusen = new Club("Lerverkusen",30,9,0,3,2);
            Club shalke = new Club("Shalke",30,9,0,3,2);
            Club wolfsgurg = new Club("Wolsfbourg",30,9,0,3,2);
            Club fribourg = new Club("Fribourg",30,9,0,3,2);
            Club hoffenheim = new Club("Hoffenheim",30,9,0,3,2);
            Club cologne = new Club("Cologne",30,9,0,3,2);
            Club union_berlin = new Club("Union Berlin",30,9,0,3,2);
            Club eintracht = new Club("Eintracht",30,9,0,3,2);
            Club herta_bsc = new Club("Herta BSC",30,9,0,3,2);
            Club augsbourg = new Club("Augsbourg",30,9,0,3,2);
            Club mainz_05 = new Club("Mainz 05",30,9,0,3,2);
            Club dusserldorf = new Club("Düsseldorf",30,9,0,3,2);
            Club werder = new Club("Werder",30,9,0,3,2);
            Club paderborn = new Club("Padeborn",30,9,0,3,2);
            clubDao.insertAll(bayern,dortmund,leipzig,leverkusen,shalke,wolfsgurg,fribourg,hoffenheim,cologne,union_berlin,eintracht,herta_bsc,augsbourg,mainz_05,dusserldorf,werder,paderborn);
            return null;
        }
    }
}
