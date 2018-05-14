package gl2.kasri.younes.paintapplication.persistence;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import gl2.kasri.younes.paintapplication.helpers.Level;

@SuppressWarnings("WeakerAccess")
public class Game {

    private GameInfo gameInfo;
    private DeviceInfo deviceInfo;
    private GameStatistics gameStatistics;
    private GameDatabaseHandler gameDatabaseHandler;

    private String id_apprenant;
    private String id_accompagnant;
    private boolean flag;

    public Game(Activity showNumberActivity, Level currentLevel) {

        deviceInfo = new DeviceInfo( showNumberActivity );
        gameStatistics = new GameStatistics();
        gameInfo = new GameInfo(currentLevel);

        gameDatabaseHandler = new GameDatabaseHandler(showNumberActivity);
    }

    /* To Retrieve Data from DB (for Tests) */
    public Game(String id_accompagnant, String id_apprenant, boolean flag, GameInfo gameInfo, DeviceInfo deviceInfo, GameStatistics gameStatistics) {
        this.id_accompagnant = id_accompagnant;
        this.id_apprenant = id_apprenant;
        this.flag = flag;
        this.gameInfo = gameInfo;
        this.deviceInfo = deviceInfo;
        this.gameStatistics = gameStatistics;
    }
    public String toString(){
        return "this.id_accompagnant =" + id_accompagnant+
        "this.id_apprenant =" + id_apprenant+
        "this.flag =" + flag+
        " > this.gameInfo =" + gameInfo+
        " >> this.deviceInfo ="+ deviceInfo+
        " >>> this.gameStatistics ="+ gameStatistics;
    }


    private void clearPreviousResults(){
        gameStatistics.clearResults();
        gameInfo.clearResults();
    }

    /* sets HeureFin, moy et min Temps */
    public void finDuNiveau() {
        gameInfo.setHeureFin();
        gameStatistics.moyenneEtMinimumTemps();
        gameDatabaseHandler.addResults(this);
        afficherResultatsDuNiveau();
        clearPreviousResults();
    }


    public void operationEchoue(int nbrOperationsEchoue){
        gameStatistics.setNbWrongAnswers( gameStatistics.getNbWrongAnswers() + nbrOperationsEchoue);
    }

    /* sets Temps pour chaque chiffre réussi */
    public void operationReussie(long temps){
        int currentNumber = gameStatistics.getNbCorrectAnswers();
        gameStatistics.setNbCorrectAnswers(currentNumber+1);

        gameStatistics.getTempsPourChaqueNombre()[currentNumber]=temps;

        Log.i(gameTAG, "operationReussie pour "+ currentNumber+" en "+ gameStatistics.getTempsPourChaqueNombre()[currentNumber]/1000+" secondes");
    }


    String gameTAG = "GAME";
    public void afficherResultatsDuNiveau(){

        Log.i(gameTAG, "afficherResultatsDuNiveau: ");
        Log.i(gameTAG, "ID Niveau = "+ gameInfo.getId_niveau());
        Log.i(gameTAG, "@MAC device = "+ deviceInfo.getMacAddress());
        Log.i(gameTAG, "Lng = "+ deviceInfo.getLongitude());
        Log.i(gameTAG, "Lat = "+ deviceInfo.getLatitude());
        Log.i(gameTAG, "HeureDebut = "+ gameInfo.levelStartTime);
        Log.i(gameTAG, "HeureFin = " + gameInfo.levelFinishTime);
        Log.i(gameTAG, "Nombre Operations Reussi = " + gameStatistics.getNbCorrectAnswers());
        Log.i(gameTAG, "Nombre Operations Echoue = " + gameStatistics.getNbWrongAnswers());
        for(int i = 0; i< gameStatistics.getTempsPourChaqueNombre().length; i++){
            if (gameStatistics.getTempsPourChaqueNombre()[i]==null) break;
            Log.i(gameTAG, "Chiffre="+i+", en "+(gameStatistics.getTempsPourChaqueNombre()[i]/1000)+" secondes");
        }
        Log.i(gameTAG, "Min Temps="+ gameStatistics.getMinTimeInSeconds());
        Log.i(gameTAG, "Moy Temps="+ gameStatistics.getAvgTimeInSeconds());



        Log.i(gameTAG," ###__________FROM DATABASE____________###");
        ArrayList<Game> dbResults = gameDatabaseHandler.getAllRecord();
        for(Game g : dbResults){
            GameInfo info = g.gameInfo;
            DeviceInfo device = g.getDeviceInfo();
            GameStatistics stats = g.getGameStatistics();

            Log.i(gameTAG, "afficherResultatsDuNiveau: ");
            Log.i(gameTAG, "ID Niveau = "+ info.getId_niveau());
            Log.i(gameTAG, "@MAC device = "+ device.getMacAddress());
            Log.i(gameTAG, "Lng = "+ device.getLongitude());
            Log.i(gameTAG, "Lat = "+ device.getLatitude());
            Log.i(gameTAG, "HeureDebut = "+ info.levelStartTime);
            Log.i(gameTAG, "HeureFin = " + info.levelFinishTime);
            Log.i(gameTAG, "Nombre Operations Reussi = " + stats.getNbCorrectAnswers());
            Log.i(gameTAG, "Nombre Operations Echoue = " + stats.getNbWrongAnswers());
            Log.i(gameTAG, "Min Temps="+ gameStatistics.getMinTimeInSeconds());
            Log.i(gameTAG, "Moy Temps="+ gameStatistics.getAvgTimeInSeconds());
        }
    }



    public String getId_accompagnant() {
        return id_accompagnant;
    }

    public String getId_apprenant() {
        return id_apprenant;
    }


    public boolean isFlag() {
        return flag;
    }

    public GameStatistics getGameStatistics() {
        return gameStatistics;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

}


