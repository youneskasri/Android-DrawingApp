package gl2.kasri.younes.paintapplication.helpers;

import android.graphics.Point;

import java.util.ArrayList;

abstract class Numbers {

    ArrayList<Point> getNumberWithPoints(int number){

        ArrayList<Point> points = new ArrayList<>();
        switch(number){
            case 0 : drawNumberZeroWithPoints(points); break;
            case 1 : drawNumberOneWithPoints(points); break;
            case 2 : drawNumberTwoWithPoints(points); break;
            case 3 : drawNumberThreeWithPoints(points); break;
            case 4 : drawNumberFourWithPoints(points); break;
            case 5 : drawNumberFiveWithPoints(points); break;
            case 6 : drawNumberSixWithPoints(points); break;
            case 7 : drawNumberSevenWithPoints(points); break;
            case 8 : drawNumberEightWithPoints(points); break;
            case 9 : drawNumberNineWithPoints(points); break;
        }

        return  points;
    }

    protected abstract void drawNumberZeroWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberOneWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberTwoWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberThreeWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberFourWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberFiveWithPoints(ArrayList<Point> points);

    protected  abstract void drawNumberSixWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberSevenWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberEightWithPoints(ArrayList<Point> points);

    protected abstract void drawNumberNineWithPoints(ArrayList<Point> points);
}
