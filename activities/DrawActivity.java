package gl2.kasri.younes.paintapplication.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;

import gl2.kasri.younes.paintapplication.R;
import gl2.kasri.younes.paintapplication.helpers.Level;
import gl2.kasri.younes.paintapplication.views.MyDrawingView;

public class DrawActivity extends ChooseLanguageActivity {

    protected MyDrawingView myDrawingView;
    private Level currentLevel;

    /* sound effects CorrectAnswer */
    protected MediaPlayer[] bravoSounds;
    protected MediaPlayer[] tryAgainSounds;

    private int nbrOperationsEchoue = 0;

    private static int NUMBER=0;
    private static int DIFF_LEVEL=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        myDrawingView = findViewById(R.id.canvas);

        myDrawerList = findViewById(R.id.navList);
        addDrawerItems();

        /**
         * Quand je change language => reStart activity
         * => I get No Intent => Default Values 0 and 1
         * I Fixed it with STATIC variables
         */
        int number = getIntent().getIntExtra("number", -1);
        int difficultyLevel = getIntent().getIntExtra("difficulty", -1);
        if (number==-1 || difficultyLevel==-1){
            number = NUMBER;
            difficultyLevel = DIFF_LEVEL;
        } else {
            NUMBER = number;
            DIFF_LEVEL = difficultyLevel;
        }


        setCurrentLevel(number, difficultyLevel);
        myDrawingView.setDrawActivity(this);

        bravoSounds = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.bravo1),
                MediaPlayer.create(this, R.raw.bravo2),
                MediaPlayer.create(this, R.raw.bravo3)
        };

        tryAgainSounds = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.tryagain1)
        };
    }

    public void correctAnswer(){

        final AlertDialog dialog = showCustomDialog(R.layout.dialog_correct);

        // Play a random sound
        int n = (int)(Math.random() * bravoSounds.length); if (n==bravoSounds.length) n--;
        bravoSounds[n].start();

        showToast(getString(R.string.moving_next_lvl));

        Runnable endTheActivity =  new Runnable() {
            public void run() {
                safeDismiss(dialog);
                Intent intent = new Intent();
                intent.putExtra("nbrOperationsEchoue", nbrOperationsEchoue);
                setResult(RESULT_OK, intent);
                finish();
            }
        };

        new Handler().postDelayed(endTheActivity, 1000);
    }

    public void wrongAnswer(){
        
       nbrOperationsEchoue++;

       playTryAgainSound();
       Intent intent = new Intent(this, ShowAnimationActivity.class);
       intent.putExtra("number", currentLevel.getNumber());
       intent.putExtra("difficulty", currentLevel.getDifficultyLevel());
       startActivity(intent);
    }

    public void playTryAgainSound(){
        tryAgainSounds[0].start();
    }

    public void clearCanvas(View v) {
        myDrawingView.clearCanvas();
    }

    public void setCurrentLevel(int number, int difficultyLevel) {
        currentLevel = new Level(number, difficultyLevel);
        myDrawingView.setCurrentLevel(currentLevel);
    }

}
