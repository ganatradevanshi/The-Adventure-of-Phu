package edu.neu.madcourse.numad_phu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import edu.neu.madcourse.numad_phu.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static MediaPlayer gameMusic;
    private int backgroundMusic = MenuActivity.backgroundMusicToggleValue;
    private int gameTheme = MenuActivity.backgroundThemeToggleValue;

    Button startButton;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final RelativeLayout splashScreen = (RelativeLayout) findViewById(R.id.splashScreenLayout);

        if(gameTheme == 0){
            splashScreen.setBackgroundResource(R.drawable.sky);
        } else {
            splashScreen.setBackgroundResource(R.drawable.nightsky);
        }

        if(backgroundMusic == 0){
            if(gameMusic == null) {
                gameMusic = MediaPlayer.create(this, R.raw.bgmnew);
            }
                gameMusic.setLooping(true);
                gameMusic.start();
        }
        else if(backgroundMusic == 1){
            gameMusic.pause();
        }
    }
    public void gameStartClick(View view) { openMainActivity(); }

    public void openMainActivity() {
        Intent mainActivity = new Intent(this, MotionPhuActivity.class);
        startActivity(mainActivity);
    }

    public void gameMenuClick (View view) { openMenuActivity(); }

    public void openMenuActivity() {
        Intent menuActivity = new Intent ( this, MenuActivity.class);
        startActivity(menuActivity);
    }

    public void gameScoreClick (View view) { openScoreActivity(); }

    public void openScoreActivity() {
        Intent scoreActivity = new Intent(this, ScoreActivity.class);
        startActivity(scoreActivity);
    }

}
