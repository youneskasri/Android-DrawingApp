package gl2.kasri.younes.paintapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class DrawActivity extends AppCompatActivity {

    private MyPaintView myPaintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        myPaintView = findViewById(R.id.canvas);
        myPaintView.setDrawActivity(this);
        myPaintView.setCurrentNumber(ShowNumberActivity.currentNumber);
        myPaintView.setCurrentLevel(ShowNumberActivity.currentLevel);
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
        myPaintView.clearCanvasAndRefreshPoints();
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
