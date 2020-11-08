package edu.neu.madcourse.numad_phu.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import edu.neu.madcourse.numad_phu.R;

public class MenuActivity extends AppCompatActivity {

    public static int backgroundMusicToggleValue = 0;
    public static int backgroundThemeToggleValue = 0;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("themeToggleVal", backgroundThemeToggleValue);
        outState.putInt("gameMusicVal", backgroundMusicToggleValue);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(backgroundMusicToggleValue == 0) {
            SplashScreenActivity.gameMusic.start();
        }
        backgroundMusicToggle();
        themeToggle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SplashScreenActivity.gameMusic.pause();
    }

    @Override
    public void onBackPressed()
    {
      // disabling the back press
    }

    public void themeToggle(){
        final RelativeLayout menuScreen = (RelativeLayout) findViewById(R.id.menuScreenLayout);
        final ImageView phuTheme = (ImageView) findViewById(R.id.phuTheme);
        final ImageView themeIcon = (ImageView) findViewById(R.id.themeIcon);
        ToggleButton toggleTheme = (ToggleButton) findViewById(R.id.themeToggle);

        if(backgroundThemeToggleValue == 0){
            menuScreen.setBackgroundResource(R.drawable.sky);
            phuTheme.setImageResource(R.drawable.phusleepday);
            themeIcon.setImageResource(R.drawable.daysun);
            toggleTheme.setChecked(false);
        } else {
            menuScreen.setBackgroundResource(R.drawable.nightsky);
            phuTheme.setImageResource(R.drawable.phusleepnight);
            themeIcon.setImageResource(R.drawable.nightmoon);
            toggleTheme.setChecked(true);
        }

        toggleTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    menuScreen.setBackgroundResource(R.drawable.nightsky);
                    phuTheme.setImageResource(R.drawable.phusleepnight);
                    themeIcon.setImageResource(R.drawable.nightmoon);
                    backgroundThemeToggleValue = 1;

                } else {
                    menuScreen.setBackgroundResource(R.drawable.sky);
                    phuTheme.setImageResource(R.drawable.phusleepday);
                    themeIcon.setImageResource(R.drawable.daysun);
                    backgroundThemeToggleValue = 0;
                }
            }
        });
    }

    public void backgroundMusicToggle(){

        final ImageView soundIcon = (ImageView) findViewById(R.id.soundIcon);
        ToggleButton toggleSound = (ToggleButton) findViewById(R.id.soundToggle);
        if(SplashScreenActivity.gameMusic == null) {
            SplashScreenActivity.gameMusic = MediaPlayer.create(this, R.raw.bgmnew);
        }
        if(backgroundMusicToggleValue == 1){
            soundIcon.setImageResource(R.drawable.soundoff);
            toggleSound.setChecked(true);
        } else {
            soundIcon.setImageResource(R.drawable.soundon);
            toggleSound.setChecked(false);
        }

        toggleSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    soundIcon.setImageResource(R.drawable.soundoff);
                    SplashScreenActivity.gameMusic.pause();
                    if(SplashScreenActivity.gameMusic.isPlaying()){
                        SplashScreenActivity.gameMusic.pause();
                    }
                    backgroundMusicToggleValue = 1;

                } else {
                    soundIcon.setImageResource(R.drawable.soundon);
                    if(!SplashScreenActivity.gameMusic.isPlaying()) {
                        SplashScreenActivity.gameMusic = MediaPlayer.create(getBaseContext(), R.raw.bgmnew);
                    }
                    SplashScreenActivity.gameMusic.start();
                    SplashScreenActivity.gameMusic.setLooping(true);
                    backgroundMusicToggleValue = 0;
                }
            }
        });

    }

    public void gameHomeClick(View view){
        openSplashActivity();
    }

    public void openSplashActivity(){
        Intent backToSplashActivity = new Intent(this, SplashScreenActivity.class);
        startActivity(backToSplashActivity);
    }



}
