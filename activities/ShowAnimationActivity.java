package gl2.kasri.younes.paintapplication.activities;

import android.os.Bundle;

import gl2.kasri.younes.paintapplication.R;
import gl2.kasri.younes.paintapplication.helpers.Level;
import gl2.kasri.younes.paintapplication.views.MyAnimationView;

public class ShowAnimationActivity extends DrawActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_animation);
        MyAnimationView myAnimationView = findViewById(R.id.canvas2);

        int number = getIntent().getIntExtra("number", 0);
        int difficultyLevel = getIntent().getIntExtra("difficulty", 1);

        Level currentLevel = new Level(number, difficultyLevel);
        myAnimationView.setCurrentLevel(currentLevel);
        myAnimationView.setActivity(this);
        myAnimationView.playAnimation();
    }

    public void finAnimation(){
        this.finish();
    }
}
