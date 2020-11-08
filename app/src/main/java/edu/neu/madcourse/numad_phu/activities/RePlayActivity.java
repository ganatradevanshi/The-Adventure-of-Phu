package edu.neu.madcourse.numad_phu.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.RelativeLayout;

import edu.neu.madcourse.numad_phu.R;

public class RePlayActivity extends AppCompatActivity {

    private int gameTheme = MenuActivity.backgroundThemeToggleValue;
    private int backgroundMusic = MenuActivity.backgroundMusicToggleValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_play);

        final RelativeLayout replayScreen = (RelativeLayout) findViewById(R.id.replayScreenLayout);

        if(gameTheme == 0){
            replayScreen.setBackgroundResource(R.drawable.sky);
        } else {
            replayScreen.setBackgroundResource(R.drawable.nightsky);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SplashScreenActivity.gameMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(backgroundMusic == 0) {
            SplashScreenActivity.gameMusic.start();
        }
    }

    @Override
    public void onBackPressed()
    {
        // disabling the back press
    }

    public void gameRestartClick (View view) { openMotionPhuActivity(); }

    public void openMotionPhuActivity() {
        Intent motionPhu = new Intent(this, MotionPhuActivity.class);
        startActivity(motionPhu);
    }

    public void gameHomeClick (View view) { splashScreenActivity(); }

    public void splashScreenActivity() {
        Intent splashScreen = new Intent(this, SplashScreenActivity.class);
        startActivity(splashScreen);
    }

    public void gameScoreClick (View view) { openScoreActivity(); }

    public void openScoreActivity() {
        Intent scoreActivity = new Intent(this, ScoreActivity.class);
        startActivity(scoreActivity);
    }

}
