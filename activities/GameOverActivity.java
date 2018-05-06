package gl2.kasri.younes.paintapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import gl2.kasri.younes.paintapplication.Dev;
import gl2.kasri.younes.paintapplication.R;

public class GameOverActivity extends ChooseLanguageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        myDrawerList = findViewById(R.id.navList);
        addDrawerItems();

        ImageView imageView = findViewById(R.id.imageView);

        boolean wonTheGame = getIntent().getBooleanExtra("wonTheGame", false);

        int idImage = R.drawable.trophy;

        if (wonTheGame)
            Log.i(Dev.TAG, "onCreate: WON THE GAME");

        imageView.setImageResource(idImage);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

}

