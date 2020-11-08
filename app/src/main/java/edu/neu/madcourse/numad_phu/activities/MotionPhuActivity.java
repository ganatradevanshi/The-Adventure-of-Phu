package edu.neu.madcourse.numad_phu.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.numad_phu.R;
import edu.neu.madcourse.numad_phu.model.Coordinates;
import edu.neu.madcourse.numad_phu.model.MovingPhu;
import edu.neu.madcourse.numad_phu.model.Obstacle;
import edu.neu.madcourse.numad_phu.model.ObstacleGenerator;
import edu.neu.madcourse.numad_phu.model.Phu;

import static edu.neu.madcourse.numad_phu.utils.Constants.BLINK_PER_TICK;
import static edu.neu.madcourse.numad_phu.utils.Constants.BUTTON_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.BUTTON_PADDING;
import static edu.neu.madcourse.numad_phu.utils.Constants.BUTTON_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.CURR_SCORE_KEY;
import static edu.neu.madcourse.numad_phu.utils.Constants.HIGH_SCORE_KEY;
import static edu.neu.madcourse.numad_phu.utils.Constants.INCREASE_DIFFICULTY_AFTER_TICKS;
import static edu.neu.madcourse.numad_phu.utils.Constants.NUM_OF_FLIPS_FOR_BLINK;
import static edu.neu.madcourse.numad_phu.utils.Constants.PHU_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.PHU_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.PREF_NAME;
import static edu.neu.madcourse.numad_phu.utils.Constants.RETRY_ALLOWED_AFTER_TICKS;
import static edu.neu.madcourse.numad_phu.utils.Constants.SCORE_PER_TICK;
import static edu.neu.madcourse.numad_phu.utils.Constants.SMALL_PHU_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.SMALL_PHU_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.SMALL_PHU_X;
import static edu.neu.madcourse.numad_phu.utils.Constants.SMALL_PHU_Y;

/**
 * Activity that contains a view that reads accelerometer sensor input and
 * translates a Penguin image(Phu) on the changes.
 */
public class MotionPhuActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private int gameTheme = MenuActivity.backgroundThemeToggleValue;
    private int backgroundMusic = MenuActivity.backgroundMusicToggleValue;
    private boolean pauseFlag = false;

    private AnimatedView mAnimatedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mAnimatedView = new AnimatedView(this);
        //Set our content to a view, not like the traditional setting to a layout
        setContentView(mAnimatedView);

        // Keep screen awake while playing the game
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_GAME);
        if(backgroundMusic == 0) {
            SplashScreenActivity.gameMusic.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SplashScreenActivity.gameMusic.pause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAnimatedView.onSensorEvent(event);
        }
    }

    @Override
    public void onBackPressed()
    {
        // disabling the back press
    }

    public class AnimatedView extends View {

        private MovingPhu phu;
        private Phu smallPhu;
        private Bitmap gameOverPhu;
        private Bitmap pauseButton;
        private Bitmap homeButton;
        private Bitmap playButton;

        private List<Obstacle> obstacles;
        private ObstacleGenerator obstacleGenerator;
        private long ticks = 0;

        private boolean isCollisionAllowed = true;
        private long lastCollisionTick = Long.MIN_VALUE;
        private List<Obstacle> collidedObstacles = new ArrayList<>();
        private boolean isShow;

        private int viewWidth;
        private int viewHeight;
        private Intent restartActivity;
        private Intent splashScreenActivity;

        public AnimatedView(Context context) {
            super(context);

            phu = new MovingPhu(0, 0, viewWidth, viewHeight, getResources(),
                    PHU_WIDTH, PHU_HEIGHT);
            smallPhu = new Phu(SMALL_PHU_X, SMALL_PHU_Y, getResources(), SMALL_PHU_WIDTH,
                    SMALL_PHU_HEIGHT);
            BitmapFactory.Options options = new BitmapFactory.Options();
            gameOverPhu = BitmapFactory.decodeResource(getResources(), R.drawable.phucrashed,
                    options);
            gameOverPhu = Bitmap.createScaledBitmap(gameOverPhu, PHU_WIDTH,
                    PHU_HEIGHT/2, true);

            pauseButton = BitmapFactory.decodeResource(getResources(), R.drawable.pause, options);
            pauseButton = Bitmap.createScaledBitmap(pauseButton, BUTTON_WIDTH, BUTTON_HEIGHT,
                    true);

            playButton = BitmapFactory.decodeResource(getResources(), R.drawable.play, options);
            playButton = Bitmap.createScaledBitmap(playButton, BUTTON_WIDTH, BUTTON_HEIGHT,
                    true);

            homeButton = BitmapFactory.decodeResource(getResources(), R.drawable.home, options);
            homeButton = Bitmap.createScaledBitmap(homeButton, BUTTON_WIDTH, BUTTON_HEIGHT,
                    true);

            obstacles = new ArrayList<>();
            obstacleGenerator = new ObstacleGenerator(viewHeight, viewWidth, getResources());

            restartActivity = new Intent(getContext(), RePlayActivity.class);
            splashScreenActivity = new Intent(getContext(), SplashScreenActivity.class);

        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            if (changed) {
                int xPositionForPhu = (right - left) / 2;
                int yPositionForPhu = (bottom - top) / 4;
                phu.moveHorizontally(xPositionForPhu);
                phu.moveVertically(yPositionForPhu);
                obstacleGenerator.setPhuY((bottom - top) / 4);
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
            phu.updateViewSize(w, h);
            obstacleGenerator.updateViewSize(w, h);
        }

        //listening to motion sensor(Accelerometer)
        public void onSensorEvent (SensorEvent event) {
            if (!pauseFlag) {
                phu.movePhu(event.values[0], event.values[2]);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {

            boolean isGameOver = false;
            if (gameTheme == 0) {
                setBackgroundResource(R.drawable.sky);
            } else {
                setBackgroundResource(R.drawable.nightsky);
            }

            if (obstacles.size() == 0 ||
                    obstacles.get(obstacles.size() - 1).isFullyInScreen(viewHeight)) {
                List<Obstacle> newObstacles = obstacleGenerator.generateObstacle();
                if (newObstacles != null) {
                    obstacles.addAll(newObstacles);
                }
            }

            if(!pauseFlag){
            List<Obstacle> obstaclesOutOfFrame = new ArrayList<>();
            for (Obstacle obstacle : obstacles) {
                if (obstacle.move()) {
                    if (!collidedObstacles.contains(obstacle)
                            && phu.hasCollided(obstacle.getBoundingRectangle())) {
                        if (isCollisionAllowed) {
                            isCollisionAllowed = false;
                            lastCollisionTick = ticks;
                            collidedObstacles.add(obstacle);
                        } else {
                            // If collided object overlaps with already collided object at the same
                            // tick, then add it to the collided obstacle and not cause a game over
                            if(ticks == lastCollisionTick) {
                                collidedObstacles.add(obstacle);
                            }
                            isGameOver = true;
                        }
                    }
                    obstacle.draw(canvas);
                } else {
                    collidedObstacles.remove(obstacle);
                    obstaclesOutOfFrame.add(obstacle);
                }
            }


            if (lastCollisionTick + RETRY_ALLOWED_AFTER_TICKS == ticks) {
                isCollisionAllowed = true;
                lastCollisionTick = Long.MIN_VALUE;
            }

            obstacles.removeAll(obstaclesOutOfFrame);

            // Draw smaller phu on top right corner if the player has a life
            if (isCollisionAllowed) {
                smallPhu.drawPhu(canvas);
            }

            // Update ticks
            ticks++;
            if (ticks == INCREASE_DIFFICULTY_AFTER_TICKS) {
                obstacleGenerator.setOtherObstaclesAllowed();
            }

            // Format paint for displaying score
            Paint paint = new Paint();
            paint.setTextSize(64);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.joti_one);
            paint.setTypeface(typeface);

            canvas.drawText(String.valueOf((int) (ticks * SCORE_PER_TICK)), viewWidth / 2,
                    viewHeight / 6, paint);
            }

            if(pauseFlag){
                Paint paint = new Paint();
                paint.setTextSize(150);
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.joti_one);
                paint.setTypeface(typeface);
                canvas.drawText("PAUSED", viewWidth/2, viewHeight/2, paint);
            }


            // If gameover, display broken parachute and redirect to restart screen
            if (isGameOver) {
                Coordinates coordinates = phu.getCoordinates();
                canvas.drawBitmap(gameOverPhu, coordinates.getX(),
                        coordinates.getY() + PHU_HEIGHT / 2, null);

                SharedPreferences appSharedPrefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = appSharedPrefs.edit();
                int currScore = (int) (ticks * SCORE_PER_TICK);
                editor.putInt(CURR_SCORE_KEY, currScore);

                if (currScore > appSharedPrefs.getInt(HIGH_SCORE_KEY, 0)) {
                    editor.putInt(HIGH_SCORE_KEY, currScore);
                }
                editor.apply();
                startActivity(restartActivity);
            } else {
                if (lastCollisionTick != Long.MIN_VALUE
                        && ticks - lastCollisionTick < NUM_OF_FLIPS_FOR_BLINK * BLINK_PER_TICK) {
                    if ((ticks - lastCollisionTick) % BLINK_PER_TICK == 0) {
                        isShow = !isShow;
                    }
                    if (isShow) {
                        phu.drawPhu(canvas);
                    }
                } else {
                    phu.drawPhu(canvas);
                }
                //We need to call invalidate each time, so that the view continuously draws
                invalidate();
            }

            if(pauseFlag) {
                canvas.drawBitmap(playButton, BUTTON_PADDING,
                        viewHeight - BUTTON_HEIGHT - BUTTON_PADDING, null);
            } else {
                canvas.drawBitmap(pauseButton, BUTTON_PADDING,
                        viewHeight - BUTTON_HEIGHT - BUTTON_PADDING, null);
            }
            canvas.drawBitmap(homeButton, 2 * BUTTON_PADDING + BUTTON_WIDTH,
                    viewHeight - BUTTON_HEIGHT - BUTTON_PADDING, null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    //Check if the x and y position of the touch is inside the pauseButton Bitmap
                    if( x > BUTTON_PADDING
                            && x < BUTTON_PADDING + BUTTON_WIDTH
                            && y > viewHeight - BUTTON_HEIGHT - BUTTON_PADDING
                            && y < viewHeight - BUTTON_PADDING )
                    {
                        //Bitmap pauseButton touched
                        pauseFlag = !pauseFlag;

                    }

                    //Check if the x and y position of the touch is inside the homeButton Bitmap
                    if( x > (BUTTON_PADDING*2) + BUTTON_WIDTH
                            && x < (BUTTON_PADDING*2) + (BUTTON_WIDTH*2)
                            && y > viewHeight - BUTTON_HEIGHT - BUTTON_PADDING
                            && y < viewHeight - BUTTON_PADDING )
                    {
                        //Bitmap homeButton touched
                        startActivity(splashScreenActivity);

                    }
            }
            return false;
        }

    }

}