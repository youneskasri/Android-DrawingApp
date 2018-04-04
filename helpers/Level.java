package gl2.kasri.younes.paintapplication.helpers;

import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;

import gl2.kasri.younes.paintapplication.Dev;

/**
 * Created by admin on 03/04/2018.
 */

public class Level implements Serializable{

    private int number;
    protected int difficultyLevel;
    private Numbers numbers; // Helper Class contenant l'ensemble des points pr chaque nombre.
    private int remainingAttempts = 3;

    private boolean isOver;

    public Level(){
        this(Dev.STARTING_LEVEL, Dev.STARTING_NUMBER);
    }

    public Level(int number, int difficultyLevel){
        super();
        this.number = number;
        this.difficultyLevel = difficultyLevel;
        numbers = new Numbers();
        isOver = false;
    }

    public boolean isOver(){
        return isOver;
    }
    public int getNumber() { return number; }
    public int getDifficultyLevel() { return difficultyLevel; }

    public float getPointsRadius(float density){
        return density * 0.5f;
    }

    public ArrayList<Point> getNumberWithPoints(){
        return numbers.getNumberWithPoints(number);
    }

    public int checkRemainingAttempts(){
        remainingAttempts--;
        return remainingAttempts;
    }

    public void nextNumber(){
        number++;
        if ( number > Dev.MAX_NUMBER ){
            nextLevel();
        }
    }

    public void nextLevel(){
            difficultyLevel++;
            number = Dev.STARTING_NUMBER;
            if ( difficultyLevel > Dev.MAX_LEVEL) {
                isOver = true;
            }
    }

}
