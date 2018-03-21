package gl2.kasri.younes.paintapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gl2.kasri.younes.*;
/**
 * Created by admin on 15/02/2018.
 */

public class CanvasView extends View {

    static final float TOLERANCE = 5;

    /** Tolérance dx et dy pour marquer un point */
    static final float DX = 30, DY = 30;

    private Canvas canvas;
    private Path path, correctPath;
    private Paint paint;

    private float density, x, y;
    private int currentNumber;

    private DrawActivity drawActivity;
    private Context activityContext;
    private Numbers numbers;

    protected List<Point>  points = null;
    protected List<Circle> circles = null;

    Rect borders;
    Paint bordersPaint;

    /** Constructor */
    public CanvasView(Context context, AttributeSet attributeSet){

        super(context,attributeSet);

        this.activityContext = context;

        path = new Path();

        setPaint(Color.LTGRAY, 40f);

        density = context.getResources().getDisplayMetrics().density;

         borders = new Rect( Math.round(100 * density),
                Math.round(130 * density),
                Math.round(280 * density),
                Math.round(380 * density)); 

        bordersPaint = new Paint(paint);
        bordersPaint.setColor(Color.LTGRAY);
        bordersPaint.setStrokeWidth(5f);

        numbers = new Numbers();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        if (points == null) {
            points = numbers.getNumberWithPoints(currentNumber);
            circles = new ArrayList<Circle>();
            for (Point p : points){
                circles.add( new Circle(p.x, p.y) );
            }
        }

        drawCircles();
        drawNumber();
        canvas.drawPath(path, paint);
    }

    /** Pr Chaque point --> canvas.drawCircle */
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

    private void drawBorder(){
        canvas.drawRect(borders, bordersPaint);
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
                    clearCanvas();
                    wasOutOfBounds = true;
                    return true;
                }
                if (wasOutOfBounds){
                    return true;
                }
                if (marquerLePoint(x,y))
                    Log.i("TAG", "onTouchEvent: J'ai marqué le point " + x +"-"+y);
                //drawCorrectPath();

                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // verifierSiTermineOuGagne();
                if (wasOutOfBounds){
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


    ArrayList<Point> pointsMarques = new ArrayList<>();

    /** retourne TRUE si un point est marqué */
    private boolean marquerLePoint(float x, float y){

        boolean unPointEstMarque = false;
        for (int i = 0; i<points.size(); i++){
            float dx = Math.abs(x - points.get(i).x * density);
            float dy = Math.abs(y - points.get(i).y * density);

            if (dx < DX  && dy < DY){
                pointsMarques.add(points.get(i));
                points.remove(i);
                unPointEstMarque = true;
            }

        }

        return unPointEstMarque;
    }

    private void drawCorrectPath(){

        //Point A = pointsMarques.get(0);
        //path.moveTo(A.x, A.y);

        for (int i=1; i<pointsMarques.size(); i++){
            Point A = pointsMarques.get(i - 1);
            Point B = pointsMarques.get(i);
            path.moveTo(A.x * density, A.y * density);
            path.lineTo(B.x * density, B.y * density);
           // path.quadTo(B.x * density, B.y * density, (A.x + B.x)/2 * density, (A.y +B.y)/2 * density);
        }

    }


    /** si reste 0 points drawActivity.correctAnswer() */
    private void verifierSiTermineOuGagne(){

        if (points.size() == 0) {   // Jusqu'ici il a marqué tous les points
            boolean resultat = false;
            Log.i("TAG", "verifierSiTermineOuGagne: il a marqué tous les points");
            ArrayList<Point> correctPoints = numbers.getNumberWithPoints(currentNumber);
            ArrayList<Point> invertedCorrectPoints = new ArrayList<>();
            for (int i = correctPoints.size()-1; i >= 0; i--) {
                invertedCorrectPoints.add(correctPoints.get(i));
            }

            if (pointsMarques.size() == correctPoints.size()) {
                // Le même nombre de points (y)
                Log.i("TAG", "verifierSiTermineOuGagne: Le même nombre de points (y)");
                if (comparePointsOrder(pointsMarques, correctPoints)
                        || comparePointsOrder(pointsMarques, invertedCorrectPoints)) {
                    // Les points sont dans l'ordre ( DIRECT ou INVERSE )
                    Log.i("TAG", "Les points sont dans l'ordre (y)");
                    resultat = true;
                }
            }

            if (resultat == true)
             drawActivity.correctAnswer();
            else
             drawActivity.wrongAnswer();

        } // END_IF
    }

    private boolean comparePointsOrder(ArrayList<Point> firstList, ArrayList<Point> secondList) {
        
        if (firstList.size() != secondList.size()) return false;

        for (int i = 0, n = firstList.size(); i<n; i++ ){
            Point A = firstList.get(i), 
                    B = secondList.get(i);
            if (A.x != B.x || A.y != B.y){
                return false;
            }
        }
        return  true;
    }

    public void clearCanvas(){
        path.reset();
        points = numbers.getNumberWithPoints(currentNumber);
        invalidate();
    }

    public void setDrawActivity(DrawActivity drawActivity){
        this.drawActivity = drawActivity;
    }

    public void setCurrentNumber(int currentNumber){
        this.currentNumber = currentNumber;
    }

    private void setPaint(int color, float strokeWidth) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(strokeWidth);
    }


      /*   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
       super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }
*/


    private void startTouch(float x, float y){
        path.moveTo(x,y);
        this.x = x;
        this.y = y;
    }

    private void moveTouch(float x, float y){
        float dx = Math.abs(this.x - x);
        float dy = Math.abs(this.y - y);

        if (dx >= TOLERANCE || dy >= TOLERANCE){
            path.quadTo(this.x, this.y, (this.x + x)/2, (this.y +y)/2);
            this.x = x;
            this.y = y;
        }

    }

    private void upTouch(){
        path.lineTo(x,y);
    }

    public boolean isOutOfBounds(float x, float y){
        for (Circle circle : circles) {
            // si distanceEntre(A, Centre)>Radius => OutOfBounds
            if ( circle.surround(x/density,y/density) ) {
                return false;
            }
        }
        return true;
    }


}

