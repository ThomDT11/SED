package com.example.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class planet8_game_view extends SurfaceView implements Runnable {

    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, damage = 0, life = 5;
    private Paint paint;
    private planet8_bossbullet[] planet8_bossbullets;
    private Random random;
    private SharedPreferences prefs;
    private List<planet8_bullet> planet8_bullets;
    private planet8_boss planet8_Boss;
    private planet8_game_start planet8_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet8_game_activity activity;
    private planet8_background background1, background2;

    public planet8_game_view(planet8_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet8_background(screenX, screenY, getResources());
        background2 = new planet8_background(screenX, screenY, getResources());

        planet8_Game_start = new planet8_game_start(this, screenY, getResources());

        planet8_Boss = new planet8_boss(this, screenY, getResources());

        planet8_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet8_bossbullets = new planet8_bossbullet[4];

        for(int i = 0; i < 4; i++){

            planet8_bossbullet planet8_BossBullet = new planet8_bossbullet(getResources());
            planet8_bossbullets[i] = planet8_BossBullet;
        }

        random = new Random();

    }

    @Override
    public void run() {

        while (isPlaying) {

            update();
            draw();
            sleep();

        }


    }

    private void update() {

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.planet8_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet8_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet8_Game_start.isGoingUp)
            planet8_Game_start.y -= 30 * screenRatioY;
        else
            planet8_Game_start.y += 30 * screenRatioY;

        if (planet8_Game_start.y < 0)
            planet8_Game_start.y = 0;

        if (planet8_Game_start.y > screenY - planet8_Game_start.height)
            planet8_Game_start.y = screenY - planet8_Game_start.height;

        if (planet8_Boss.isGoingUp)
            planet8_Boss.y -= 20 * screenRatioY;
        else
            planet8_Boss.y += 20 * screenRatioY;

        if (planet8_Boss.y < 0)
            planet8_Boss.y = 0;



        if (planet8_Boss.y > screenY - planet8_Boss.height)
            planet8_Boss.y = screenY - planet8_Boss.height;


        if (planet8_Boss.y == 0)
            planet8_Boss.isGoingUp = false;

        if (planet8_Boss.y == screenY - planet8_Boss.height)
            planet8_Boss.isGoingUp = true;



        List<planet8_bullet> planet8_trash = new ArrayList<>();

        for (planet8_bullet planet8_Bullet : planet8_bullets){

            if (planet8_Bullet.x > screenX)
                planet8_trash.add(planet8_Bullet);

            planet8_Bullet.x += 50 * screenRatioX;

            for (planet8_bossbullet planet8_bossbullet : planet8_bossbullets){

                if (Rect.intersects(planet8_Boss.getCollisionShape(), planet8_Bullet.getCollisionShape())) {
                    damage++;
                    planet8_Bullet.x = +500;

                }

                if(Rect.intersects(planet8_bossbullet.getCollisionShape(), planet8_Bullet.getCollisionShape())){

                    planet8_bossbullet.x = -500;
                    planet8_Bullet.x = screenX + 500;
                    planet8_bossbullet.wasShot = true;
                }
                if(damage == 50){
                    stageFinished = true;
                }

            }
        }

        for (planet8_bullet planet8_Bullet : planet8_trash)
            planet8_bullets.remove(planet8_Bullet);

        for (planet8_bossbullet planet8_bossbullet : planet8_bossbullets) {

            planet8_bossbullet.x -= planet8_bossbullet.speed;

            if (planet8_bossbullet.x + planet8_bossbullet.width < 0) {

                if (!planet8_bossbullet.wasShot) {
                    planet8_bossbullet.x = -500;
                    planet8_bossbullet.wasShot = true;
                    return;
                }


                int bound = (int) (10 * screenRatioX);
                planet8_bossbullet.speed = random.nextInt(bound);

                if (planet8_bossbullet.speed < 10 * screenRatioX)
                    planet8_bossbullet.speed = (int) (10 * screenRatioX);

                planet8_bossbullet.x = planet8_Boss.x;
                planet8_bossbullet.y = planet8_Boss.y * 2 ;

                planet8_bossbullet.wasShot = false;

            }
            if (Rect.intersects(planet8_Game_start.getCollisionShape(), planet8_bossbullet.getCollisionShape())) {
                life--;
                planet8_bossbullet.x = -1000;
                planet8_bossbullet.wasShot = true
                ;

            }

            if (life == 0 ){
                isGameOver = true;
                return;
            }
        }

    }






    private void draw () {

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.planet8_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet8_Background, background2.x, background2.y, paint);

            canvas.drawBitmap(planet8_Boss.getplanet8_boss(), planet8_Boss.x, planet8_Boss.y, paint);

            for (planet8_bossbullet planet8_boss : planet8_bossbullets)

                canvas.drawBitmap(planet8_boss.getplanet8_bossbullet(), planet8_boss.x, planet8_boss.y, paint);

            canvas.drawText(damage + "", screenX / 2f, 150,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet8_Game_start.getDead(), planet8_Game_start.x, planet8_Game_start.y, paint);
                saveIfDone();
                waitBeforeExiting();
                getHolder().unlockCanvasAndPost(canvas);
                return ;
            }

            if (stageFinished){
                isPlaying = false;
                saveIfDone();
                waitBeforeExiting();
            }


            canvas.drawBitmap(planet8_Game_start.getPlanet1_game_start(), planet8_Game_start.x,planet8_Game_start.y, paint);

            for(planet8_bullet planet8Bullet : planet8_bullets)
                canvas.drawBitmap(planet8Bullet.planet8_Bullet, planet8Bullet.x, planet8Bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void saveIfDone() {

        if (prefs.getInt("highscore",  0 ) < damage){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt ("highscore", damage);
            editor.apply();

        }

    }

    private void waitBeforeExiting(){

        try {
            Thread.sleep(1000);
            activity.startActivity(new Intent(activity, planet8_game.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2){
                    planet8_Game_start.isGoingUp = true;
                }

                break;

            case  MotionEvent.ACTION_UP:
                planet8_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet8_Game_start.toShoot++;
                break;

        }


        return true;
    }


    public void newBullet() {

        planet8_bullet planet8_Bullet = new planet8_bullet(getResources());
        planet8_Bullet.x = planet8_Game_start.x + planet8_Game_start.width;
        planet8_Bullet.y = planet8_Game_start.y + (planet8_Game_start.height/10) ;

        planet8_bullets.add(planet8_Bullet);

    }
}