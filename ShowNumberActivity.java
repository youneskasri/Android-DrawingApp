package gl2.kasri.younes.paintapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import gl2.kasri.younes.Dev;

public class ShowNumberActivity extends AppCompatActivity {

    static final int DRAW_NUMBER_REQUEST = 1;  // The request code

    private TextView numberTextView;

    protected boolean startedDrawingActivity = false;
    protected boolean gameOver = false;

    protected static int currentNumber = 2;
    protected static int currentLevel = 1;

    public void nextNumber(){
        currentNumber++;
        if ( currentNumber > Dev.MAX_NUMBER ){
            moveToNextLevel();
        }
    }

    public void moveToNextLevel(){
        currentLevel++;
        if ( currentLevel > Dev.MAX_LEVEL){
            endTheGame(true);
        }
    }

    public void endTheGame(boolean wonTheGame){

        numberTextView.setTextSize(32);
        numberTextView.setText("Game Over");
        gameOver = true;

        Intent intent = new Intent(ShowNumberActivity.this, GameOverActivity.class);
        intent.putExtra("wonTheGame", wonTheGame);
        startActivity(intent);

        // TODO Finish, retourner A l'activité de début
        // TODO New Intent activiteDebut --> GameOverActivity
        // TODO GameOverActivity.onTouch => return to activiteDebut
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_number);
        numberTextView = findViewById(R.id.number);
        numberTextView.setText(""+currentNumber);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!startedDrawingActivity && !gameOver) {
            startedDrawingActivity = true;
            Intent intent = new Intent(ShowNumberActivity.this, DrawActivity.class);
            startActivityForResult(intent, DRAW_NUMBER_REQUEST);
        }

        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if ( startedDrawingActivity && requestCode == DRAW_NUMBER_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.i(Dev.TAG, "RESULT_OK onActivityResult: moving to nextNumber");
                nextNumber();
            } else if (resultCode == RESULT_CANCELED ){
                Log.i(Dev.TAG, "WRONG_ANSWER onActivityResult: Try again with the same number");
            }
            if (!gameOver) numberTextView.setText(""+currentNumber);
        }

        startedDrawingActivity = false;
    }

}
