package edu.neu.madcourse.numad_phu.utils;

import edu.neu.madcourse.numad_phu.R;

public interface Constants {

    int PHU_HEIGHT = 200;
    int PHU_WIDTH = 150;
    double PHU_SPEEDX = 3;
    double PHU_SPEEDY = 1;

    int SMALL_PHU_HEIGHT = 110;
    int SMALL_PHU_WIDTH = 85;
    int SMALL_PHU_X =20;
    int SMALL_PHU_Y = 20;

    int NUM_OTHER_OBSTACLES = 11;
    int NUM_CLOUD_TYPES = 2;

    int INITIAL_OBSTACLE_PROB = 50;
    int FINAL_OBSTACLE_PROB = 40;

    double[] INITIAL_PROB_DIST = {0.75, 0.95};
    double[] FINAL_PROB_DIST = {0.6, 0.85};

    int BLINK_PER_TICK = 10;
    int NUM_OF_FLIPS_FOR_BLINK = 5;

    double SCORE_PER_TICK = 0.1;
    long RETRY_ALLOWED_AFTER_TICKS = 2000;
    long INCREASE_DIFFICULTY_AFTER_TICKS = 5000;

    int CLOUD1_IMG = R.drawable.cloud4;
    int CLOUD1_HEIGHT = 100;
    int CLOUD1_WIDTH = 150;

    int CLOUD2_IMG = R.drawable.cloud5;
    int CLOUD2_HEIGHT = 130;
    int CLOUD2_WIDTH = 200;

    int CLOUD_SPEED = 3;

    int AEROPLANE1_IMG = R.drawable.aeroplaneleft;
    int AEROPLANE2_IMG = R.drawable.aeroplaneright;
    int AEROPLANE_HEIGHT = 100;
    int AEROPLANE_WIDTH = 240;
    int AEROPLANE_SPEEDX = 5;
    int AEROPLANE_SPEEDY = 3;

    int BALLOON1_IMG = R.drawable.balloonthree;
    int BALLOON2_IMG = R.drawable.balloontwo;
    int BALLOON3_IMG = R.drawable.balloonone;
    int BALLOON_HEIGHT = 200;
    int BALLOON_WIDTH = 75;

    int BALLOON_PAIR_IMG = R.drawable.balloonpair;
    int BALLOON_PAIR_WIDTH = 100;
    int BALLOON_SPEEDX = 1;
    int BALLOON_SPEEDY = 2;

    int BIRD1_IMG = R.drawable.birdone;
    int BIRD2_IMG = R.drawable.birdtwo;
    int BIRD3_IMG = R.drawable.birdthree;
    int BIRD_HEIGHT = 50;
    int BIRD_WIDTH = 150;
    int BIRD_SPEEDX = 4;
    int BIRD_SPEEDY = 2;

    int KITE1_IMG = R.drawable.kite1;
    int KITE2_IMG = R.drawable.kite2;
    int KITE1_HEIGHT = 220;
    int KITE1_WIDTH = 120;

    int KITE2_HEIGHT = 170;
    int KITE2_WIDTH = 130;
    int KITE_SPEEDX = 2;
    int KITE_SPEEDY = 3;

    String PREF_NAME = "score";
    String CURR_SCORE_KEY = "currScore";
    String HIGH_SCORE_KEY = "highScore";

    int BUTTON_WIDTH = 120;
    int BUTTON_HEIGHT = 120;
    int BUTTON_PADDING = 50;
}
