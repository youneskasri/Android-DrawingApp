package gl2.kasri.younes.paintapplication.persistence;

import java.util.Date;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.helpers.Level;

@SuppressWarnings("WeakerAccess")
public class GameInfo {

    String id_application = Dev.ID_EXERCICE;
    String id_exercice = Dev.ID_EXERCICE;

    private Level currentLevel;
    Date levelStartTime; /* lors de l'instanciation */
    Date levelFinishTime; /* lors de finDuJeu() */


    /* To Retrieve Data from DB and Tests */
    public GameInfo(String id_application, String id_exercice, Date levelStartTime, Date levelFinishTime, String id_level){
        this.id_application = id_application;
        this.id_exercice = id_exercice;
        this.levelStartTime = levelStartTime;
        this.levelFinishTime = levelFinishTime;
        String diff = id_level.substring(id_level.length()-1);
        int difficultyLevel = Integer.parseInt(diff);
        currentLevel = new Level(0, difficultyLevel);
    }


    public void clearResults() {
        levelStartTime = new Date();
        levelFinishTime = null;
    }

    public GameInfo(Level currentLevel) {

        this.currentLevel = currentLevel;
        levelStartTime = new Date();
    }

    public void setHeureFin() {
        this.levelFinishTime = new Date();
    }

    public String getId_application() {
        return id_application;
    }

    public String getId_niveau() {
        return id_exercice + "_" +
                (currentLevel.getDifficultyLevel()-1);
        /* -1 Car dans showNumberActivity on fait nextNumber=>nextDiffLevel avant de tester FinDuNiveau */
    }

    public String getId_exercice() {
        return id_exercice;
    }

    public Date getLevelStartTime() {
        return levelStartTime;
    }

    public Date getLevelFinishTime() {
        return levelFinishTime;
    }

}