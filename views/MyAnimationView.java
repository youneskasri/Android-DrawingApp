package gl2.kasri.younes.paintapplication.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gl2.kasri.younes.paintapplication.activities.ShowAnimationActivity;
import gl2.kasri.younes.paintapplication.helpers.Level;

/**
 * Created by admin on 10/04/2018.
 */

public class MyAnimationView extends CanvasView {

    private Level currentLevel;
    private Paint pointsPaint, drawingPaint;
    private Activity activity;

    private float x,y;
    private List<Point> points = null;

    public MyAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pointsPaint = makePaint(Color.DKGRAY, 10f);
        drawingPaint = makePaint(Color.DKGRAY, 40f);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (points == null) {
            points = currentLevel.getNumberWithPoints();
        }

        drawNumber();
        canvas.drawPath(path, drawingPaint);
    }

    public void playAnimation(){

        avancerAnimation();

        new Timer().scheduleAtFixedRate(new TimerTask(){
            public void run() {
                if (points.size() > 1) {
                    avancerAnimation();
                } else {
                    finAnimation(); return;
                }
            }
        },0,300);
    }

    private void avancerAnimation() {
        if (points == null)
            points = currentLevel.getNumberWithPoints();
        Point A = points.get(0);
        Point B = points.get(1);
        relierPoints(A, B);
        points.remove(A);
    }

    private void relierPoints(Point a, Point b) {
        startTouch(a.x * density, a.y * density);
        moveTouch(b.x * density, b.y * density);
        upTouch();
        moveTouch( (a.x + b.x)/2 * density, (a.y + b.y)/2 * density);
        upTouch();
        invalidate();
    }

    private void finAnimation(){
        ((ShowAnimationActivity)this.activity).finAnimation();
    }

    protected void startTouch(float x, float y){
        path.moveTo(x,y);
        this.x = x;
        this.y = y;
    }

    protected void moveTouch(float x, float y){
            path.quadTo(this.x, this.y, (this.x + x)/2, (this.y +y)/2);
            this.x = x;
            this.y = y;
    }

    protected void upTouch(){
        path.lineTo(x,y);
    }

    public void setCurrentLevel(Level level) {
        currentLevel = level;
        points = level.getNumberWithPoints();
    }

    private void drawNumber(){
        for (Point p : points) {
            float radius = currentLevel.getPointsRadius(density);
            canvas.drawCircle(p.x * density, p.y * density, radius, pointsPaint);
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}


