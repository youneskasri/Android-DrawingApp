package gl2.kasri.younes.paintapplication.persistence;

import android.util.Log;

import gl2.kasri.younes.paintapplication.activities.ShowNumberActivity;
import gl2.kasri.younes.paintapplication.helpers.Level;

import static android.content.ContentValues.TAG;


public class Game {

    private GameInfo gameInfo;
    private DeviceInfo deviceInfo;
    private GameStatistics gameStatistics;
    private GameDatabaseHandler gameDatabaseHandler;

    private String id_apprenant;
    private String id_accompagnant;
    private boolean flag;

    public Game(ShowNumberActivity showNumberActivity, Level currentLevel) {

        deviceInfo = new DeviceInfo( showNumberActivity );
        gameStatistics = new GameStatistics();
        gameInfo = new GameInfo(currentLevel);

        gameDatabaseHandler = new GameDatabaseHandler(showNumberActivity);
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
        clearPreviousResults();
        afficherResultatsDuNiveau();
    }

    public void operationEchoue(int nbrOperationsEchoue){
        gameStatistics.setNbWrongAnswers( gameStatistics.getNbWrongAnswers() + nbrOperationsEchoue);
    }

    /* sets Temps pour chaque chiffre réussi */
    public void operationReussie(long temps){
        int currentNumber = gameStatistics.getNbCorrectAnswers();
        gameStatistics.setNbCorrectAnswers(currentNumber+1);

        gameStatistics.getTempsPourChaqueNombre()[currentNumber]=temps;

        Log.i(TAG, "operationReussie pour "+ currentNumber+" en "+ temps/1000+" secondes");
    }


    String gameTAG = "GAME";
    public void afficherResultatsDuNiveau(){
        Log.i(gameTAG, "afficherResultatsDuNiveau: ");
        Log.i(gameTAG, "ID Niveau = "+ gameInfo.getId_niveau());
        Log.i(gameTAG, "@MAC device = "+ deviceInfo.getMacAddress());
        Log.i(gameTAG, "HeureDebut = "+ gameInfo.levelStartTime);
        Log.i(gameTAG, "HeureFin = " + gameInfo.levelFinishTime);
        Log.i(gameTAG, "Nombre Operations Reussi = " + gameStatistics.getNbCorrectAnswers());
        Log.i(gameTAG, "Nombre Operations Echoue = " + gameStatistics.getNbWrongAnswers());
        for(int i = 0; i< gameStatistics.getTempsPourChaqueNombre().length; i++){
            Log.i(gameTAG, "Chiffre="+i+", en "+(gameStatistics.getTempsPourChaqueNombre()[i]/1000)+" secondes");
        }
        Log.i(gameTAG, "Min Temps="+ gameStatistics.getMinTimeInSeconds());
        Log.i(gameTAG, "Moy Temps="+ gameStatistics.getAvgTimeInSeconds());
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


