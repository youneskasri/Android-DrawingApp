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
        showToast("Good Job ! Moving to the next level");
        setResult(RESULT_OK); // equals -1
        finish();
    }

    public void wrongAnswer(){
        showToast("You lost ! Try again");
        setResult(RESULT_CANCELED);
        finish();
    }

    public void clearCanvas(View v){
        canvasView.clearCanvas();
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
