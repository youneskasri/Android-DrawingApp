package gl2.kasri.younes.paintapplication.helpers;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import gl2.kasri.younes.paintapplication.Dev;

public class Circle {
    public float x,y, radius;
    public Paint paint;

    private Circle(float x, float y, float radius, Paint paint){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.paint = paint;
    }

    public Circle(float x, float y, float radius){
        this(x, y, radius, new Paint());
        setPaint();
    }

    public Circle(float x, float y){
        this(x, y, 1f*Dev.MAX_TOLERANCE_EASY, new Paint());
        setPaint();
    }


    private void setPaint() {
        paint.setColor(Color.LTGRAY);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5f);
    }

    public boolean doesSurround(float x, float y) {

        double distanceDuCentre = Math.sqrt( Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));

        if ( distanceDuCentre <= radius ) {
            Log.i(Dev.TAG, " ||AC|| = " + distanceDuCentre);
            Log.i(Dev.TAG, " R = " + radius);
            return true;
        }
        return false;
    }

}
