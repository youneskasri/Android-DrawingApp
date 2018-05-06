package gl2.kasri.younes.paintapplication.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static gl2.kasri.younes.paintapplication.Dev.TAG;


@SuppressWarnings("WeakerAccess")
public class GameDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "Game";
    // Table name
    private static final String TABLE_GAME_i = "Game_Infos";
    // Table Columns names
    private String a = "id";
    private String b = "id_application";
    private String c = "id_apprenant";
    private String d = "id_accompagnant";
    private String e = "id_exercice";
    private String f = "id_niveau";
    //private String g="date_actuelle";
    private String h = "heure_debut";
    private String i = "heure_fin";
    private String j = "Nombre_operation_reuss";
    private String k = "Nombre_operation_echou";
    private String l = "minimum_temps_operation_sec";
    private String m = "moyen_temps_operation_sec";
    private String n = "longitude";
    private String o = "latitude";
    private String p = "device";
    private String q = "flag";

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
                + h + " DATETIME,"
                + i + " DATETIME,"
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
        SimpleDateFormat sdfm = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.UK);
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

    public ArrayList<Game> getAllRecord() {
        ArrayList<Game> results = new ArrayList<>();
        Cursor rs = getReadableDatabase().rawQuery("SELECT * FROM Game_Infos", null);
        rs.moveToFirst();

        while (!rs.isAfterLast()) {
            String id = rs.getString(rs.getColumnIndex(a));
            Log.i(TAG, "getAllRecord: Retrieving Record N°="+id);
            String id_application =rs.getString(rs.getColumnIndex(b));
            String id_apprenant = rs.getString(rs.getColumnIndex(c));
            String id_accompagnant = rs.getString(rs.getColumnIndex(d));
            String id_exercice = rs.getString(rs.getColumnIndex(e));
            String id_niveau = rs.getString(rs.getColumnIndex(f));
            //rs.getString(rs.getColumnIndex(g));
            String heure_debut = rs.getString(rs.getColumnIndex(h));
            String heure_fin = rs.getString(rs.getColumnIndex(i));
            String nombre_operation_reuss = rs.getString(rs.getColumnIndex(j));
            String nombre_operation_echou = rs.getString(rs.getColumnIndex(k));
            String minimum_temps_operation_sec = rs.getString(rs.getColumnIndex(l));
            String moyen_temps_operation_sec = rs.getString(rs.getColumnIndex(m));
            String longitude = rs.getString(rs.getColumnIndex(n));
            String latitude = rs.getString(rs.getColumnIndex(o));
            String mac_device = rs.getString(rs.getColumnIndex(p));
            String flag = rs.getString(rs.getColumnIndex(q));

            SimpleDateFormat sdfm = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.UK);

            DeviceInfo device = new DeviceInfo(mac_device, longitude, latitude);

            GameInfo info;
            try {
                Date heureDebut  = sdfm.parse(heure_debut);
                Date heureFin = sdfm.parse(heure_fin);
                info = new GameInfo(id_application,id_exercice,heureDebut, heureFin, id_niveau);
            } catch (Exception e1) {
                info = new GameInfo(id_application,id_exercice,new Date(), new Date(), id_niveau);
            }

            GameStatistics stats = new GameStatistics(nombre_operation_reuss,
                    nombre_operation_echou,
                    minimum_temps_operation_sec,
                    moyen_temps_operation_sec);

            results.add(new Game(id_accompagnant, id_apprenant, Boolean.parseBoolean(flag), info, device, stats));
            rs.moveToNext();
        }

        rs.close();
        return results;
    }


}