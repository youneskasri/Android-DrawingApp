package gl2.kasri.younes.paintapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Date;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.R;
import gl2.kasri.younes.paintapplication.helpers.Level;
import gl2.kasri.younes.paintapplication.persistence.Game;

public class ShowNumberActivity extends AppCompatActivity {

    public final static int DRAW_NUMBER_REQUEST = 200;  // The request code

    Game game;
    Long t1, t2;

    private TextView numberTextView;
    private TextView levelTextView;
    private TextView numberNameTextView;

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

    private void updateShowNumberLayout() {
        numberTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,currentLevel.getNumbersFontSize());

        int number = currentLevel.getNumber();
        numberTextView.setText(""+number);
        numberNameTextView.setText(getStringIdFromNumber(number));
        levelTextView.setText(getLevelName(currentLevel));
    }

    private int getLevelName(Level currentLevel) {
        int levelName = R.string.level_easy;
        if (currentLevel.getDifficultyLevel() == 2)
            levelName = R.string.hard_level;

        return levelName;
    }

    private int getStringIdFromNumber(int number) {
        int id = R.string.number_zero;
        if (number == 1)
            id = R.string.number_one;
        else if (number == 2)
            id = R.string.number_two;
        else if (number == 3)
            id = R.string.number_three;
        else if (number == 4)
            id = R.string.number_four;
        else if (number == 5)
            id = R.string.number_five;
        else if (number == 6)
            id = R.string.number_six;
        else if (number == 7)
            id = R.string.number_seven;
        else if (number == 8)
            id = R.string.number_eight;
        else if (number == 9)
            id = R.string.number_nine;

        return id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_number);

        levelTextView = findViewById(R.id.levelTextView);
        numberTextView = findViewById(R.id.number);
        numberNameTextView = findViewById(R.id.numberNameTextView);

        currentLevel = new Level();
        game = new Game(this, currentLevel);
        updateShowNumberLayout();

        t1 = game.getGameInfo().getLevelStartTime().getTime();
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

                /* Operation Réussie */
                t2 = new Date().getTime();
                game.operationReussie(t2 - t1);
                t1 = t2;

                /* nbr Operation Echoue ? */
                int nbrOperationsEchoue = getIntent()
                        .getIntExtra("nbrOperationsEchoue", 0);
                game.operationEchoue(nbrOperationsEchoue);
                Log.i("STATS", "onActivityResult: game.getGameStatistics().getNbWrongAnswers()=="+game.getGameStatistics().getNbWrongAnswers());
                /* if new Level */
                if (currentLevel.getNumber()==0){
                    game.finDuNiveau();
                    //TODO game = new Game(this, currentLevel);
                   t1 = game.getGameInfo().getLevelStartTime().getTime();
                }

                if ( currentLevel.isOver() ) {
                    endTheGame(true);
                }
            } else if (resultCode == RESULT_CANCELED ){
                Log.i(Dev.TAG, "WRONG_ANSWER onActivityResult: Try again with the same number");
            }
            if (!gameOver) {
                updateShowNumberLayout();
            }
        }

        startedDrawingActivity = false;
    }

}
