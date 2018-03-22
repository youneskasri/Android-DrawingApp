package gl2.kasri.younes.paintapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import gl2.kasri.younes.Dev;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        ImageView imageView = findViewById(R.id.imageView);
        TextView messageTextView = findViewById(R.id.messageTextView);

        boolean wonTheGame = getIntent().getBooleanExtra("wonTheGame", false);
        Log.i(Dev.TAG, "onCreate: GameOverActivity : wonTheGame = "+wonTheGame);

        String message;
        int idImage;
        if (wonTheGame){
            message = "Congratulations ! You won";
            idImage = R.drawable.trophy;
        } else {
            message = "Too bad :( .. Keep trying";
            idImage = R.drawable.cross;
        }


        messageTextView.setText(message);
        imageView.setImageResource(idImage);
    }

    // TODO onClick Listener => return to MainActivity ( finish() )
}

