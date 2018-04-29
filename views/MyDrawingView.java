package gl2.kasri.younes.paintapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import gl2.kasri.younes.paintapplication.activities.DrawActivity;
import gl2.kasri.younes.paintapplication.helpers.Circle;
import gl2.kasri.younes.paintapplication.helpers.Level;

import static gl2.kasri.younes.paintapplication.Dev.TAG;


public class MyDrawingView extends CanvasView {

   // static final float DX = 30, DY = 30; // Tolérance dx et dy pour marquer un point

    private DrawActivity drawActivity; // The calling activity

    private Level currentLevel;

    private Paint drawingPaint;
    private Paint pointsPaint;

    private List<Point>  points = null;
    private List<Circle> circles = null; // Intervalles d'acceptation ( you can make it visible if you wish )

    private Path previousPath = null;
    private List<Point> previousPoints = null;
    private Point lastRemovedPoint;

    /** Constructor */
    public MyDrawingView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        previousPath = new Path(path);

        pointsPaint = makePaint(Color.DKGRAY, 10f);
        drawingPaint = makePaint(Color.parseColor("#DB0A5B"), 40f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (points == null) {
            initPointsAndCircles();
        }

       // drawCircles();
        drawNumber();
        canvas.drawPath(path, drawingPaint);
    }

    private void initPointsAndCircles() {
        points = currentLevel.getNumberWithPoints();
        previousPoints = new ArrayList<>(points);
        circles = new ArrayList<>();
        for (Point p : points){
            circles.add( new Circle(p.x, p.y) );
        }
    }

    @Override
    public boolean performClick() { /* To remove a warning ( Accessibility ) */
        return super.performClick();
    }

    boolean wasOutOfBounds;
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                performClick();
                if ( isOutOfBounds(x, y) ){
                    wasOutOfBounds = true;
                    return true;
                }
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if ( isOutOfBounds(x, y)  ){
                    clearCanvasAndRetrievePoints();
                    wasOutOfBounds = true;
                    return true;
                }
                if (wasOutOfBounds){
                    return true;
                }
                if (markThePoint(x,y)){
                    Log.i(TAG, "onTouchEvent: J'ai marqué le point " + x +"-"+y);
                    previousPath = new Path(path);
                    previousPoints = new ArrayList<>(points);
                }

                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (wasOutOfBounds){
                    checkRemainingAttempts();
                    path.moveTo(x,y);
                    wasOutOfBounds = false;
                    return true;
                }
                upTouch();
                invalidate();
                if (points.size() == 0){
                    drawActivity.correctAnswer();
                }

                break;
        }
        return true;
    }

    private boolean markThePoint(float x, float y){

        boolean unPointEstMarque = false;
        for (int i = 0; i<points.size(); i++){
            if (points.get(i)==null) continue;
            float dx = Math.abs(x - points.get(i).x * density);
            float dy = Math.abs(y - points.get(i).y * density);

            float maxTolerance = currentLevel.getMaxTolerance();
            if (dx < maxTolerance  && dy < maxTolerance){
                lastRemovedPoint = points.remove(i);
                unPointEstMarque = true;
            }

        }

        return unPointEstMarque;
    }

    private void checkRemainingAttempts(){
        int remainingAttempts = currentLevel.checkRemainingAttempts();
        drawActivity.showToast("Attention ! Il vous reste " + remainingAttempts +" tentative(s)");
        if ( remainingAttempts == 0 ){
            drawActivity.wrongAnswer();
            currentLevel.refreshRemainingAttempts();
        } else {
            drawActivity.playTryAgainSound();
        }
    }

    public boolean isOutOfBounds(float x, float y){
        for (Circle circle : circles) {
            if ( circle.doesSurround(x/density,y/density) ) {
                return false; // Le point est à l'interieur du cercle (y)
            }
        }
        return true;
    }

    public void clearCanvasAndRetrievePoints(){
        path.reset();

        path = new Path(previousPath);
        points = new ArrayList<>(previousPoints);
        points.add(lastRemovedPoint);

        invalidate();
    }

    public void clearCanvas(){
        path.reset();
        previousPath = new Path(path);

        points = currentLevel.getNumberWithPoints();
        invalidate();
    }


    /** The calling activity */
    public void setDrawActivity(DrawActivity drawActivity){
        this.drawActivity = drawActivity;
    }

    public void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    private void drawNumber(){
        for (Point p : points) {
            float radius = currentLevel.getPointsRadius(density);
            if (p!=null) canvas.drawCircle(p.x * density, p.y * density, radius, pointsPaint);
        }
    }

    private void drawCircles() {
        for (Circle c : circles) {
            float radius = c.radius * density / currentLevel.getDifficultyLevel(); // TODO
            canvas.drawCircle(c.x * density, c.y * density, radius, c.paint);
        }
    }


}

