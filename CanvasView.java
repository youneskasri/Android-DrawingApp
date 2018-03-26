package gl2.kasri.younes.paintapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 26/03/2018.
 */

class CanvasView extends View {

    // A Partir de > 5px de distance on relie 2 points
    static final float TOLERANCE = 5;

    protected Canvas canvas;
    protected Path path;
    protected Paint paint;
    protected float density; // Screen density

    private float x;
    private float y;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        setPaint(Color.LTGRAY, 40f);
        density = context.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
    }

    protected void startTouch(float x, float y){
        path.moveTo(x,y);
        this.x = x;
        this.y = y;
    }

    protected void moveTouch(float x, float y){
        float dx = Math.abs(this.x - x);
        float dy = Math.abs(this.y - y);

        if (dx >= TOLERANCE || dy >= TOLERANCE){
            path.quadTo(this.x, this.y, (this.x + x)/2, (this.y +y)/2);
            this.x = x;
            this.y = y;
        }

    }

    protected void upTouch(){
        path.lineTo(x,y);
    }

    protected void setPaint(int color, float strokeWidth) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(strokeWidth);
    }

}
