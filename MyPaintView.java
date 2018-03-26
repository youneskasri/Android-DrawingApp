package gl2.kasri.younes.paintapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import gl2.kasri.younes.Dev;
import gl2.kasri.younes.Numbers;

/**
 * Created by admin on 15/02/2018.
 */

public class MyPaintView extends CanvasView {

    static final float DX = 30, DY = 30; // Tolérance dx et dy pour marquer un point

    private DrawActivity drawActivity; // The calling activity

    private int currentNumber;
    private Numbers numbers; // Helper Class contenant l'ensemble des points pr chaque nombre.

    private List<Point>  points = null;
    private List<Circle> circles = null;

    private int remainingAttempts = 3;

    /** Constructor */
    public MyPaintView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        numbers = new Numbers();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (points == null) {
            initPointsAndCircles();
        }

        drawCircles();
        drawNumber();
        canvas.drawPath(path, paint);
    }

    private void initPointsAndCircles() {
        points = numbers.getNumberWithPoints(currentNumber);
        circles = new ArrayList<Circle>();
        for (Point p : points){
            circles.add( new Circle(p.x, p.y) );
        }
    }

    boolean wasOutOfBounds;
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if ( isOutOfBounds(x, y) ){
                    wasOutOfBounds = true;
                    return true;
                }
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if ( isOutOfBounds(x, y)  ){
                    clearCanvasAndRefreshPoints();
                    wasOutOfBounds = true;
                    return true;
                }
                if (wasOutOfBounds){
                    return true;
                }
                if (marquerLePoint(x,y))
                    Log.i(Dev.TAG, "onTouchEvent: J'ai marqué le point " + x +"-"+y);
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

    private boolean marquerLePoint(float x, float y){

        boolean unPointEstMarque = false;
        for (int i = 0; i<points.size(); i++){
            float dx = Math.abs(x - points.get(i).x * density);
            float dy = Math.abs(y - points.get(i).y * density);

            if (dx < DX  && dy < DY){
                points.remove(i);
                unPointEstMarque = true;
            }

        }

        return unPointEstMarque;
    }

    private void checkRemainingAttempts(){
        remainingAttempts--;
        drawActivity.showToast("Attention ! Il vous reste " + remainingAttempts +" tentative(s)");

        if (remainingAttempts == 0){
            drawActivity.wrongAnswer();
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

    public void clearCanvasAndRefreshPoints(){
        path.reset();
        points = numbers.getNumberWithPoints(currentNumber);
        invalidate();
    }

    /** The calling activity */
    public void setDrawActivity(DrawActivity drawActivity){
        this.drawActivity = drawActivity;
    }

    public void setCurrentNumber(int currentNumber){
        this.currentNumber = currentNumber;
    }

    private void drawNumber(){
        paint.setColor(Color.DKGRAY);
        for (Point p : points) {
            canvas.drawCircle(p.x * density, p.y * density, 0.2f * density, paint);
        }
        paint.setColor(Color.RED);
    }

    private void drawCircles(){
        for (Circle c : circles){
            canvas.drawCircle(c.x * density, c.y * density, c.radius * density, c.paint);
        }
    }


      /*   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
       super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }
*/

}

