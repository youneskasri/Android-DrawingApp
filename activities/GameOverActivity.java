package gl2.kasri.younes.paintapplication.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import gl2.kasri.younes.paintapplication.R;

public class GameOverActivity extends ChooseLanguageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        myDrawerList = findViewById(R.id.navList);
        addDrawerItems();

        ImageView imageView = findViewById(R.id.imageView);
        TextView messageTextView = findViewById(R.id.messageTextView);

        boolean wonTheGame = getIntent().getBooleanExtra("wonTheGame", false);

        String message;
        int idImage;

        if (wonTheGame){
            message = "Congratulations ! You won";
            idImage = R.drawable.trophy;
        } else {
            /* is never reached in this version b/c
            * we don't want to hurt the child */
            message = "Too bad :( .. Keep trying";
            idImage = R.drawable.cross;
        }

        messageTextView.setText(message);
        imageView.setImageResource(idImage);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

}

