package gl2.kasri.younes.paintapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNumberActivity extends AppCompatActivity {

    static final int DRAW_NUMBER_REQUEST = 1;  // The request code

    TextView numberTextView;
    boolean startedAnActivity = false;

    static int currentNumber = 2;
    static int currentLevel = 1;


    public static void nextNumber(){
        currentNumber++;
       // if (currentNumber>=6) moveToNextLevel();
    }
    public static void moveToNextLevel(){
        currentLevel++;
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

        if (!startedAnActivity) {
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
            } else {
                Toast.makeText(this, "Essayez encore", Toast.LENGTH_LONG).show();
            }

            numberTextView.setText(""+currentNumber);
        }

        startedAnActivity = false;
    }

}
