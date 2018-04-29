package gl2.kasri.younes.paintapplication.helpers;

import android.graphics.Point;

import java.util.ArrayList;

import gl2.kasri.younes.paintapplication.Dev;

public class Level {

    private int number;
    private int difficultyLevel;
    private Numbers numbers; // Helper Class contenant l'ensemble des points pr chaque nombre.
    private int remainingAttempts = Dev.NUMBER_OF_ATTEMPTS;

    private boolean isOver;

    public Level(){
        this(Dev.STARTING_NUMBER, Dev.STARTING_LEVEL);
    }

    public Level(int number, int difficultyLevel){
        super();
        this.number = number;
        this.difficultyLevel = difficultyLevel;
        switch(difficultyLevel){
            case 1: numbers = new NumbersEasy();
            //default: numbers = new Numbers();
        }
        numbers = new NumbersEasy();
        isOver = false;
    }

    public boolean isOver(){
        return isOver;
    }
    public int getNumber() { return number; }
    public int getDifficultyLevel() { return difficultyLevel; }

    public float getPointsRadius(float density){
        return 2f * density * (Dev.MAX_LEVEL-difficultyLevel+1)  ;
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

    private void nextLevel(){
            difficultyLevel++;
            number = Dev.STARTING_NUMBER;
            if ( difficultyLevel > Dev.MAX_LEVEL) {
                isOver = true;
            }
    }

    public void refreshRemainingAttempts() {
        remainingAttempts = Dev.NUMBER_OF_ATTEMPTS;
    }
}
