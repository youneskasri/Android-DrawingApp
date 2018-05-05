package gl2.kasri.younes.paintapplication.persistence;

import java.util.Date;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.helpers.Level;

public class GameInfo {

    String id_application = Dev.ID_EXERCICE;
    String id_exercice = Dev.ID_EXERCICE;

    private Level currentLevel;
    Date levelStartTime; /* lors de l'instanciation */
    Date levelFinishTime; /* lors de finDuJeu() */

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
        return id_exercice + "_" + currentLevel.getDifficultyLevel();
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