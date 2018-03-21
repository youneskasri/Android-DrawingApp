package gl2.kasri.younes.paintapplication;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by admin on 21/03/2018.
 */

class Circle {
    public float x,y, radius;
    Paint paint;

    public Circle(float x, float y, float radius, Paint paint){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.paint = paint;
    }

    public Circle(float x, float y){
        this(x, y, 20, new Paint());
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

        Log.i("Encercler", "Point A : (x,y)=("+x+", "+y+")");
        Log.i("Encercler", "Centre : (x,y)=("+this.x+", "+this.y+")");
        double distanceDuCentre = Math.sqrt( Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
        Log.i("Encercler", " ||AC|| = " + distanceDuCentre);
        Log.i("Encercler", " R = " + radius);

        if ( distanceDuCentre <= radius ) {
            Log.i("Encercler", "surround: OUI");
            return true;
        }
        return false;
    }

}
