package edu.neu.madcourse.numad_phu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.neu.madcourse.numad_phu.R;

import static edu.neu.madcourse.numad_phu.utils.Constants.CURR_SCORE_KEY;
import static edu.neu.madcourse.numad_phu.utils.Constants.HIGH_SCORE_KEY;
import static edu.neu.madcourse.numad_phu.utils.Constants.PREF_NAME;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreNowValue;
    TextView scoreHighestValue;
    RelativeLayout scoreScreen;
    private int gameTheme = MenuActivity.backgroundThemeToggleValue;
    private int backgroundMusic = MenuActivity.backgroundMusicToggleValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreNowValue = (TextView) findViewById(R.id.textScoreNowValue);
        scoreHighestValue = (TextView) findViewById(R.id.textScoreHighestValue);
        scoreScreen = (RelativeLayout) findViewById(R.id.scoreScreenLayout);

        SharedPreferences appSharedPrefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        scoreNowValue.setText(String.valueOf(appSharedPrefs.getInt(CURR_SCORE_KEY, 0)));
        scoreHighestValue.setText(String.valueOf(appSharedPrefs.getInt(HIGH_SCORE_KEY, 0)));

        if(gameTheme == 0){
            scoreScreen.setBackgroundResource(R.drawable.sky);
        } else {
            scoreScreen.setBackgroundResource(R.drawable.nightsky);
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

    public void gameHomeClick(View view){
        openSplashActivity();
    }

    public void openSplashActivity(){
        Intent backToSplashActivity = new Intent(this, SplashScreenActivity.class);
        startActivity(backToSplashActivity);
    }

}
