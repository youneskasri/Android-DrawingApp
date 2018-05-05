package gl2.kasri.younes.paintapplication.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;


public class GameDatabaseHandler extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;
        // Database Name
        private static final String DATABASE_NAME = "Game";
        // Table name
        private static final String TABLE_GAME_i = "Game_Infos";
        // Table Columns names
        private String a="id";
        private String b="id_application";
        private String c="id_apprenant";
        private String d="id_accompagnant";
        private String e="id_exercice";
        private String f="id_niveau";
        //private String g="date_actuelle";
        private String h="levelStartTime";
        private String i="levelFinishTime";
        private String j="Nombre_operation_reuss";
        private String k="Nombre_operation_echou";
        private String l="minimum_temps_operation_sec";
        private String m="moyen_temps_operation_sec";
        private String n="longitude";
        private String o="latitude";
        private String p="device";
        private String q="flag";

        public GameDatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_GAME_i + "("
                    + a + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + b + " VARCHAR(100),"
                    + c + " VARCHAR(100),"
                    + d + " VARCHAR(100),"
                    + e + " VARCHAR(100),"
                    + f + " VARCHAR(100),"
                      //      + g + " DATE,"
                    + h + " TIME,"
                    + i + " TIME,"
                    + j + " INTEGER,"
                    + k + " INTEGER,"
                    + l + " INTEGER,"
                    + m + " INTEGER,"
                    + n + " DOUBLE,"
                    + o + " DOUBLE,"
                    + p + " VARCHAR(100),"
                    + q + " BOOLEAN)";


            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_i);
            // Creating tables again
            onCreate(db);
        }


        public void addResults(Game Game) {

            GameInfo info = Game.getGameInfo();
            DeviceInfo device = Game.getDeviceInfo();
            GameStatistics stats = Game.getGameStatistics();


            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(b, info.getId_application());
            values.put(c, Game.getId_apprenant());
            values.put(d, Game.getId_accompagnant());
            values.put(e, info.getId_exercice());
            values.put(f, info.getId_niveau());
            //DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat sdfm = new SimpleDateFormat("HH:mm:ss");
            //String da = df.format(Game.getDate_actuelle());
            String hd = sdfm.format(info.getLevelStartTime());
            String hf = sdfm.format(info.getLevelFinishTime());
            //values.put(g, da);
            values.put(h, hd);
            values.put(i, hf);
            values.put(j, stats.getNbCorrectAnswers());
            values.put(k, stats.getNbWrongAnswers());
            values.put(l, stats.getMinTimeInSeconds());
            values.put(m, stats.getAvgTimeInSeconds());
            values.put(n, device.getLongitude());
            values.put(o, device.getLatitude());
            values.put(p, device.getMacAddress());
            values.put(q, Game.isFlag());

            // Inserting Row
            db.insert(TABLE_GAME_i, null, values);
            db.close(); // Closing database connection
        }
}

