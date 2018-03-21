package gl2.kasri.younes.paintapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawActivity extends AppCompatActivity {

    private CanvasView canvasView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        canvasView = findViewById(R.id.canvas);
        canvasView.setDrawActivity(this);
        canvasView.setCurrentNumber(ShowNumberActivity.currentNumber);
    }



    public void correctAnswer(){
        Toast.makeText(this, "Moving to the next level", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    public void wrongAnswer(){
        Toast.makeText(this, "You lost !", Toast.LENGTH_LONG).show();
        setResult(-1);
        finish();
    }

    public void clearCanvas(View v){
        canvasView.clearCanvas();
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
    */
}
