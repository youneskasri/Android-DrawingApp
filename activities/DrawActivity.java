package gl2.kasri.younes.paintapplication.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import gl2.kasri.younes.paintapplication.R;
import gl2.kasri.younes.paintapplication.helpers.Level;
import gl2.kasri.younes.paintapplication.views.MyDrawingView;

public class DrawActivity extends AppCompatActivity {

    protected MyDrawingView myDrawingView;
    private Level currentLevel;

    /* sound effects CorrectAnswer */
    protected MediaPlayer[] bravoSounds;
    protected MediaPlayer[] tryAgainSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        myDrawingView = findViewById(R.id.canvas);

        int number = getIntent().getIntExtra("number", 0);
        int difficultyLevel = getIntent().getIntExtra("difficulty", 1);

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
        int n = (int)Math.random() * bravoSounds.length; if (n==bravoSounds.length) n--;
        bravoSounds[n].start();

        showToast("Good Job ! Moving to the next level");

        Runnable endTheActivity =  new Runnable() {
            public void run() {
                dialog.dismiss();
                setResult(RESULT_OK);
                finish();
            }
        };

        new Handler().postDelayed(endTheActivity, 1000);
    }

    public void wrongAnswer(){
       /* showToast("You lost ! Try again");
        setResult(RESULT_CANCELED);
        finish(); */

       playTryAgainSound();
       Intent intent = new Intent(this, ShowAnimationActivity.class);
       intent.putExtra("number", currentLevel.getNumber());
       intent.putExtra("difficulty", currentLevel.getDifficultyLevel());
       startActivity(intent);
    }

    public void playTryAgainSound(){
        tryAgainSounds[0].start();
    }

    public void clearCanvas(View v){
        myDrawingView.clearCanvasAndRefreshPoints();
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public AlertDialog showCustomDialog(int layout) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DrawActivity.this);
        View mView = getLayoutInflater().inflate(layout, null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        return dialog;
    }

    public void setCurrentLevel(int number, int difficultyLevel) {
        currentLevel = new Level(number, difficultyLevel);
        myDrawingView.setCurrentLevel(currentLevel);
    }

}
