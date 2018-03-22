package gl2.kasri.younes.paintapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gl2.kasri.younes.Dev;
import gl2.kasri.younes.Numbers;

/**
 * Created by admin on 15/02/2018.
 */

public class CanvasView extends View {

    static final float TOLERANCE = 5;  // A Partir de > 5px de distance on relie 2 points
    static final float DX = 30, DY = 30; // Tolérance dx et dy pour marquer un point

    private Canvas canvas;
    private Path path;
    private Paint paint;

    private float density, x, y;
    private int currentNumber;

    private DrawActivity drawActivity;
    private Numbers numbers; // Helper Class contenant l'ensemble des points pr chaque nombre.

    protected List<Point>  points = null;
    protected List<Circle> circles = null;

    protected int remainingAttempts = 3;

    /** Constructor */
    public CanvasView(Context context, AttributeSet attributeSet){

        super(context,attributeSet);
        path = new Path();
        setPaint(Color.LTGRAY, 40f);
        density = context.getResources().getDisplayMetrics().density;
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

