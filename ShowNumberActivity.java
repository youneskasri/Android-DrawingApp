package gl2.kasri.younes.paintapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNumberActivity extends AppCompatActivity {

    static final int DRAW_NUMBER_REQUEST = 1;  // The request code
    static final int MAX_NUMBER = 5, MAX_LEVEL = 1;

    private TextView numberTextView;

    protected boolean startedAnActivity = false;
    protected boolean gameOver = false;

    protected static int currentNumber = 2;
    protected static int currentLevel = 1;

    public void nextNumber(){
        currentNumber++;
        if ( currentNumber > MAX_NUMBER ){
            moveToNextLevel();
        }
    }

    public void moveToNextLevel(){
        currentLevel++;
        if ( currentLevel > MAX_LEVEL){
            endTheGame(true);
        }
    }

    public void endTheGame(boolean wonTheGame){
        if (wonTheGame){
            numberTextView.setText("Congratulations ! You won");
            numberTextView.setTextColor(Color.GREEN);
        } else {
            numberTextView.setText("Too bad :( .. Keep trying");
            numberTextView.setTextColor(Color.RED);
        }
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

        if (!startedAnActivity && !gameOver) {
            startedAnActivity = true;
            Intent intent = new Intent(ShowNumberActivity.this, DrawActivity.class);
            startActivityForResult(intent, DRAW_NUMBER_REQUEST);
        }

        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if ( startedAnActivity && requestCode == DRAW_NUMBER_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.i("RESULT_OK", "onActivityResult: moving to nextNumber");
                nextNumber();
            } else if ( resultCode == RESULT_CANCELED ){
                Log.i("WRONG_ANSWER", "onActivityResult: Try again with this number");
            }
            numberTextView.setText(""+currentNumber);
        }

        startedAnActivity = false;
    }

}
