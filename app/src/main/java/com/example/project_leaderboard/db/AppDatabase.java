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


@Database(entities = {League.class, Club.class, Match.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static final String DATABASE_NAME = "leaderboardDB";
    private static AppDatabase instance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    // For Singleton instantiation
   // private static final Object LOCK = new Object();

    public abstract LeagueDao leagueDao();

    public abstract MatchDao matchDao();

    public abstract ClubDao clubDao();


    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                   instance = buildDatabase(context);
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
                database.matchDao().deleteAll();
                database.leagueDao().deleteAll();
                database.clubDao().deleteAll();

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




/*
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
            Club paris = new Club("Paris-SG", 15, 5, 0, 0, 3);
            Club marseille = new Club("Marseille", 15, 5, 0, 0, 3);
            Club rennes = new Club("Rennes", 15, 5, 0, 0, 3);
            Club lille = new Club("Lille", 15, 5, 0, 0, 3);
            Club reims = new Club("Reims", 15, 5, 0, 0, 3);
            Club nice = new Club("Nice", 15, 5, 0, 0, 3);
            Club lyon = new Club("Lyon", 15, 5, 0, 0, 3);
            Club montpellier = new Club("Montpellier", 15, 5, 0, 0, 3);
            Club monaco = new Club("Monaco", 15, 5, 0, 0, 3);
            Club angers = new Club("Angers", 15, 5, 0, 0, 3);
            Club strasbourg = new Club("Strasbourg", 15, 5, 0, 0, 3);
            Club bordeaux = new Club("Bordeaux", 15, 5, 0, 0, 3);
            Club nantes = new Club("Nantes", 15, 5, 0, 0, 3);
            Club brest = new Club("Brest", 15, 5, 0, 0, 3);
            Club metz = new Club("Metz", 15, 5, 0, 0, 3);
            Club dijon = new Club("Dijon", 15, 5, 0, 0, 3);
            Club saintetienne = new Club("Saint-Etienne", 15, 5, 0, 0, 3);
            Club nimes = new Club("Nîmes", 15, 5, 0, 0, 3);
            Club amiens = new Club("Amiens", 15, 5, 0, 0, 3);
            Club toulouse = new Club("Toulouse", 15, 5, 0, 0, 3);
            clubDao.insertAll(paris,marseille,rennes,lille,reims,nice,montpellier,monaco,lyon,angers,strasbourg,bordeaux,nantes,brest,metz,dijon,saintetienne,nimes,amiens,toulouse);
            Club lazio = new Club("Lazio", 15, 5, 0, 0, 4);
            Club inter = new Club("Inter", 15, 5, 0, 0, 4);
            Club atalanta = new Club("Atalanta", 15, 5, 0, 0, 4);
            Club roma = new Club("Roma", 15, 5, 0, 0, 4);
            Club naples = new Club("Naples", 15, 5, 0, 0, 4);
            Club milan = new Club("Milan AC", 15, 5, 0, 0, 4);
            Club hellas = new Club("Hellas Vérone", 15, 5, 0, 0, 4);
            Club parme = new Club("Parme", 15, 5, 0, 0, 4);
            Club bologne = new Club("Bologne", 15, 5, 0, 0, 4);
            Club sassulo = new Club("Sassulo", 15, 5, 0, 0, 4);
            Club cagliari = new Club("Cagliari", 15, 5, 0, 0, 4);
            Club fiorentina = new Club("Fiorentina", 15, 5, 0, 0, 4);
            Club udinese = new Club("Udinese", 15, 5, 0, 0, 4);
            Club torino = new Club("Torino", 15, 5, 0, 0, 4);
            Club sampdoria = new Club("Sampdoria", 15, 5, 0, 0, 4);
            Club genoa = new Club("Genoa", 15, 5, 0, 0, 4);
            Club lecce = new Club("Lecce", 15, 5, 0, 0, 4);
            Club spal = new Club("SPAL 2013", 15, 5, 0, 0, 4);
            Club brescia = new Club("Brescia", 15, 5, 0, 0, 4);
            clubDao.insertAll(lazio,inter,atalanta,roma,naples,milan,hellas,parme,bologne,sassulo, cagliari,fiorentina,udinese,torino,sampdoria,genoa,lecce,spal,brescia);
            Match match1 = new Match(2,4,6,3);
            Match match2 = new Match(5,6,2,1);
            Match match3 = new Match(15,25,0,0);
            matchDao.insertAll(match1,match2,match3);
            return null;
        }
    }

   */
}
