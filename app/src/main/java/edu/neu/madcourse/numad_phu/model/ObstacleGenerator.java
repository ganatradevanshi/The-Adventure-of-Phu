package edu.neu.madcourse.numad_phu.model;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static edu.neu.madcourse.numad_phu.utils.Constants.AEROPLANE1_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.AEROPLANE2_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.AEROPLANE_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.AEROPLANE_SPEEDX;
import static edu.neu.madcourse.numad_phu.utils.Constants.AEROPLANE_SPEEDY;
import static edu.neu.madcourse.numad_phu.utils.Constants.AEROPLANE_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON1_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON2_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON3_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON_PAIR_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON_PAIR_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON_SPEEDX;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON_SPEEDY;
import static edu.neu.madcourse.numad_phu.utils.Constants.BALLOON_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.BIRD1_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.BIRD2_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.BIRD3_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.BIRD_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.BIRD_SPEEDX;
import static edu.neu.madcourse.numad_phu.utils.Constants.BIRD_SPEEDY;
import static edu.neu.madcourse.numad_phu.utils.Constants.BIRD_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.CLOUD1_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.CLOUD1_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.CLOUD1_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.CLOUD2_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.CLOUD2_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.CLOUD2_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.CLOUD_SPEED;
import static edu.neu.madcourse.numad_phu.utils.Constants.FINAL_OBSTACLE_PROB;
import static edu.neu.madcourse.numad_phu.utils.Constants.FINAL_PROB_DIST;
import static edu.neu.madcourse.numad_phu.utils.Constants.INITIAL_OBSTACLE_PROB;
import static edu.neu.madcourse.numad_phu.utils.Constants.INITIAL_PROB_DIST;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE1_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE1_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE1_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE2_HEIGHT;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE2_IMG;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE2_WIDTH;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE_SPEEDX;
import static edu.neu.madcourse.numad_phu.utils.Constants.KITE_SPEEDY;
import static edu.neu.madcourse.numad_phu.utils.Constants.NUM_CLOUD_TYPES;
import static edu.neu.madcourse.numad_phu.utils.Constants.NUM_OTHER_OBSTACLES;

public class ObstacleGenerator {

    private double[] probDistribution = INITIAL_PROB_DIST;
    private int obstacleProb = INITIAL_OBSTACLE_PROB;
    private int viewHeight;
    private int viewWidth;
    private boolean otherObstaclesAllowed = false;
    private Resources resources;
    private int phuY;
    private int aeroplaneMinHeight;
    private int balloonMinHeight;
    private int birdMinHeight;
    private int kiteMinHeight;

    private void resetMinHeights() {
        aeroplaneMinHeight = getMinHeight(AEROPLANE_SPEEDX, AEROPLANE_SPEEDY);
        balloonMinHeight = getMinHeight(BALLOON_SPEEDX, BALLOON_SPEEDY);
        birdMinHeight = getMinHeight(BIRD_SPEEDX, BIRD_SPEEDY);
        kiteMinHeight = getMinHeight(KITE_SPEEDX, KITE_SPEEDY);
    }

    private Obstacle generateCloud1() {
        int initialX = new Random().nextInt(viewWidth - 1);
        return new Obstacle(new Coordinates(initialX, viewHeight),
                CLOUD1_HEIGHT,
                CLOUD1_WIDTH,
                CLOUD1_IMG,
                this.resources,
                0,
                CLOUD_SPEED);
    }

    private Obstacle generateCloud2() {
        int initialX = new Random().nextInt(viewWidth - 1);
        return new Obstacle(new Coordinates(initialX, viewHeight),
                CLOUD2_HEIGHT,
                CLOUD2_WIDTH,
                CLOUD2_IMG,
                this.resources,
                0,
                CLOUD_SPEED);
    }

    private Obstacle generateAeroplaneLeft() {
        int height = new Random().nextInt(aeroplaneMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(0, height),
                AEROPLANE_HEIGHT,
                AEROPLANE_WIDTH,
                AEROPLANE1_IMG,
                this.resources,
                AEROPLANE_SPEEDX,
                AEROPLANE_SPEEDY);
    }

    private Obstacle generateAeroplaneRight() {
        int height = new Random().nextInt(aeroplaneMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(viewWidth, height),
                AEROPLANE_HEIGHT,
                AEROPLANE_WIDTH,
                AEROPLANE2_IMG,
                this.resources,
                -AEROPLANE_SPEEDX,
                AEROPLANE_SPEEDY);
    }

    private Obstacle generateBalloon1() {
        int height = new Random().nextInt(balloonMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(0, height),
                BALLOON_HEIGHT,
                BALLOON_WIDTH,
                BALLOON1_IMG,
                this.resources,
                BALLOON_SPEEDX,
                BALLOON_SPEEDY);
    }

    private Obstacle generateBalloon2() {
        int height = new Random().nextInt(balloonMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(viewWidth, height),
                BALLOON_HEIGHT,
                BALLOON_WIDTH,
                BALLOON2_IMG,
                this.resources,
                -BALLOON_SPEEDX,
                BALLOON_SPEEDY);
    }

    private Obstacle generateBalloon3() {
        int height = new Random().nextInt(balloonMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(viewWidth, height),
                BALLOON_HEIGHT,
                BALLOON_WIDTH,
                BALLOON3_IMG,
                this.resources,
                -BALLOON_SPEEDX,
                BALLOON_SPEEDY);
    }

    private Obstacle generateBalloonPair() {
        int height = new Random().nextInt(balloonMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(0, height),
                BALLOON_HEIGHT,
                BALLOON_PAIR_WIDTH,
                BALLOON_PAIR_IMG,
                this.resources,
                BALLOON_SPEEDX,
                BALLOON_SPEEDY);
    }

    private Obstacle generateBird1() {
        int height = new Random().nextInt(birdMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(viewWidth, height),
                BIRD_HEIGHT,
                BIRD_WIDTH,
                BIRD1_IMG,
                this.resources,
                -BIRD_SPEEDX,
                BIRD_SPEEDY);
    }

    private Obstacle generateBird2() {
        int height = new Random().nextInt(birdMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(0, height),
                BIRD_HEIGHT,
                BIRD_WIDTH,
                BIRD2_IMG,
                this.resources,
                BIRD_SPEEDX,
                BIRD_SPEEDY);
    }

    private Obstacle generateBird3() {
        int height = new Random().nextInt(birdMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(viewWidth, height),
                BIRD_HEIGHT,
                BIRD_WIDTH,
                BIRD3_IMG,
                this.resources,
                -BIRD_SPEEDX,
                BIRD_SPEEDY);
    }

    private Obstacle generateKite1() {
        int height = new Random().nextInt(kiteMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(0, height),
                KITE1_HEIGHT,
                KITE1_WIDTH,
                KITE1_IMG,
                this.resources,
                KITE_SPEEDX,
                KITE_SPEEDY);
    }

    private Obstacle generateKite2() {
        int height = new Random().nextInt(kiteMinHeight - phuY) + phuY;
        return new Obstacle(new Coordinates(viewWidth, height),
                KITE2_HEIGHT,
                KITE2_WIDTH,
                KITE2_IMG,
                this.resources,
                -KITE_SPEEDX,
                KITE_SPEEDY);
    }

    private int getMinHeight(int dx, int dy) {
        int h = (int) (phuY + ((float)dy/ dx) * viewWidth);
        if(h > viewHeight) {
            return viewHeight;
        }
        return h;
    }

    private Obstacle generateCloud(int type) {
        switch (type) {
            case 0:
                return generateCloud1();
            default:
                return generateCloud2();
        }
    }

    private Obstacle generateOtherObstacles(int type) {
        switch (type) {
            case 0: return generateAeroplaneLeft();
            case 1: return generateAeroplaneRight();
            case 2: return generateBalloon1();
            case 3: return generateBalloon2();
            case 4: return generateBalloon3();
            case 5: return generateBalloonPair();
            case 6: return generateBird1();
            case 7: return generateBird2();
            case 8: return generateBird3();
            case 9: return generateKite1();
            default: return generateKite2();
        }
    }

    private int getNumberOfObstacles() {
        double r = Math.random();
        int numOfObstacles = 1;
        if(r > probDistribution[0]) {
            numOfObstacles = 2;
            if(r >= probDistribution[1]) {
                numOfObstacles = 3;
            }
        }
        return numOfObstacles;
    }

    public ObstacleGenerator(int viewHeight, int viewWidth, Resources resources) {
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        this.resources = resources;
    }

    public void setPhuY(int phuY) {
        this.phuY = phuY;
        resetMinHeights();
    }

    public void updateViewSize(int w, int h) {
        viewWidth = w;
        viewHeight = h;
    }

    public void setOtherObstaclesAllowed() {
        this.otherObstaclesAllowed = true;
        this.probDistribution = FINAL_PROB_DIST;
        this.obstacleProb = FINAL_OBSTACLE_PROB;
    }

    public List<Obstacle> generateObstacle() {
        if(new Random().nextInt(obstacleProb) == 0) {
            List<Obstacle> newObstacles = new ArrayList<>();
            int numOfObstacles = getNumberOfObstacles();

            // If three items appear on the sky and other obstacles are allowed, make one of them as a non-cloud obstacle
            if(otherObstaclesAllowed && numOfObstacles == 3) {
                newObstacles.add(generateOtherObstacles(new Random().nextInt(NUM_OTHER_OBSTACLES)));
                numOfObstacles--;
            }

            for(int i = 0; i < numOfObstacles; i++) {
                int index = new Random().nextInt(NUM_CLOUD_TYPES);
                newObstacles.add(generateCloud(index));
            }
            return newObstacles;
        }
        return null;
    }

}
