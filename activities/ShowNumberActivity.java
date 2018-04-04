package gl2.kasri.younes.paintapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.R;
import gl2.kasri.younes.paintapplication.helpers.Level;

public class ShowNumberActivity extends AppCompatActivity {

    public final static int DRAW_NUMBER_REQUEST = 200;  // The request code

    private TextView numberTextView;

    protected boolean startedDrawingActivity = false;
    protected boolean gameOver = false;

    protected Level currentLevel;

    public void endTheGame(boolean wonTheGame){

        numberTextView.setTextSize(32);
        numberTextView.setText("Game Over");
        gameOver = true;

        currentLevel = new Level(); // reinitialiser à 0

        if (wonTheGame) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_number);
        numberTextView = findViewById(R.id.number);
        currentLevel = new Level();
        numberTextView.setText(""+currentLevel.getNumber());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!startedDrawingActivity && !gameOver) {
            startedDrawingActivity = true;
            Intent intent = new Intent(ShowNumberActivity.this, DrawActivity.class);
            intent.putExtra("number", currentLevel.getNumber());
            intent.putExtra("difficulty", currentLevel.getDifficultyLevel());
            startActivityForResult(intent, DRAW_NUMBER_REQUEST);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if ( startedDrawingActivity && requestCode == DRAW_NUMBER_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.i(Dev.TAG, "RESULT_OK onActivityResult: moving to nextNumber");
                currentLevel.nextNumber();
                if ( currentLevel.isOver() ) {
                    endTheGame(true);
                }
            } else if (resultCode == RESULT_CANCELED ){
                Log.i(Dev.TAG, "WRONG_ANSWER onActivityResult: Try again with the same number");
            }
            if (!gameOver) numberTextView.setText(""+currentLevel.getNumber());
        }

        startedDrawingActivity = false;
    }

}
