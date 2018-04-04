package gl2.kasri.younes.paintapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.R;
import gl2.kasri.younes.paintapplication.helpers.Level;
import gl2.kasri.younes.paintapplication.views.MyDrawingView;

public class DrawActivity extends AppCompatActivity {

    private MyDrawingView myDrawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        myDrawingView = findViewById(R.id.canvas);

        int number = getIntent().getIntExtra("number", 0);
                int difficultyLevel = getIntent().getIntExtra("difficulty", 1);

        Level currentLevel = new Level(number, difficultyLevel);
        Log.i(Dev.TAG, "onCreate: currentNumber = "+currentLevel.getNumber()+" ");

        myDrawingView.setDrawActivity(this);
        myDrawingView.setCurrentLevel(currentLevel);
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
        myDrawingView.clearCanvasAndRefreshPoints();
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
