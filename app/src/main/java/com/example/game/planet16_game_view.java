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

public class planet16_game_view extends SurfaceView implements Runnable {

    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, damage = 0, life = 5;
    private Paint paint;
    private planet16_bossbullet[] planet16_bossbullets;
    private Random random;
    private SharedPreferences prefs;
    private List<planet16_bullet> planet16_bullets;
    private planet16_boss planet16_Boss;
    private planet16_game_start planet16_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet16_game_activity activity;
    private planet16_background background1, background2;

    public planet16_game_view(planet16_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet16_background(screenX, screenY, getResources());
        background2 = new planet16_background(screenX, screenY, getResources());

        planet16_Game_start = new planet16_game_start(this, screenY, getResources());

        planet16_Boss = new planet16_boss(this, screenY, getResources());

        planet16_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet16_bossbullets = new planet16_bossbullet[4];

        for(int i = 0; i < 4; i++){

            planet16_bossbullet planet16_BossBullet = new planet16_bossbullet(getResources());
            planet16_bossbullets[i] = planet16_BossBullet;
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

        if (background1.x + background1.planet16_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet16_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet16_Game_start.isGoingUp)
            planet16_Game_start.y -= 30 * screenRatioY;
        else
            planet16_Game_start.y += 30 * screenRatioY;

        if (planet16_Game_start.y < 0)
            planet16_Game_start.y = 0;

        if (planet16_Game_start.y > screenY - planet16_Game_start.height)
            planet16_Game_start.y = screenY - planet16_Game_start.height;

        if (planet16_Boss.isGoingUp)
            planet16_Boss.y -= 20 * screenRatioY;
        else
            planet16_Boss.y += 20 * screenRatioY;

        if (planet16_Boss.y < 0)
            planet16_Boss.y = 0;



        if (planet16_Boss.y > screenY - planet16_Boss.height)
            planet16_Boss.y = screenY - planet16_Boss.height;


        if (planet16_Boss.y == 0)
            planet16_Boss.isGoingUp = false;

        if (planet16_Boss.y == screenY - planet16_Boss.height)
            planet16_Boss.isGoingUp = true;



        List<planet16_bullet> planet16_trash = new ArrayList<>();

        for (planet16_bullet planet16_Bullet : planet16_bullets){

            if (planet16_Bullet.x > screenX)
                planet16_trash.add(planet16_Bullet);

            planet16_Bullet.x += 50 * screenRatioX;

            for (planet16_bossbullet planet16_bossbullet : planet16_bossbullets){

                if (Rect.intersects(planet16_Boss.getCollisionShape(), planet16_Bullet.getCollisionShape())) {
                    damage++;
                    planet16_Bullet.x = +500;

                }

                if(Rect.intersects(planet16_bossbullet.getCollisionShape(), planet16_Bullet.getCollisionShape())){

                    planet16_bossbullet.x = -500;
                    planet16_Bullet.x = screenX + 500;
                    planet16_bossbullet.wasShot = true;
                }
                if(damage == 70){
                    stageFinished = true;
                }

            }
        }

        for (planet16_bullet planet16_Bullet : planet16_trash)
            planet16_bullets.remove(planet16_Bullet);

        for (planet16_bossbullet planet16_bossbullet : planet16_bossbullets) {

            planet16_bossbullet.x -= planet16_bossbullet.speed;

            if (planet16_bossbullet.x + planet16_bossbullet.width < 0) {

                if (!planet16_bossbullet.wasShot) {
                    planet16_bossbullet.x = -500;
                    planet16_bossbullet.wasShot = true;
                    return;
                }


                int bound = (int) (10 * screenRatioX);
                planet16_bossbullet.speed = random.nextInt(bound);

                if (planet16_bossbullet.speed < 10 * screenRatioX)
                    planet16_bossbullet.speed = (int) (10 * screenRatioX);

                planet16_bossbullet.x = planet16_Boss.x;
                planet16_bossbullet.y = planet16_Boss.y * 2;
                planet16_bossbullet.wasShot = false;

            }
            if (Rect.intersects(planet16_Game_start.getCollisionShape(), planet16_bossbullet.getCollisionShape())) {
                life--;
                planet16_bossbullet.x = -1000;
                planet16_bossbullet.wasShot = true
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
            canvas.drawBitmap(background1.planet16_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet16_Background, background2.x, background2.y, paint);

            canvas.drawBitmap(planet16_Boss.getplanet16_boss(), planet16_Boss.x, planet16_Boss.y, paint);

            for (planet16_bossbullet planet16_boss : planet16_bossbullets)

                canvas.drawBitmap(planet16_boss.getplanet16_bossbullet(), planet16_boss.x, planet16_boss.y, paint);

            canvas.drawText(damage + "", screenX / 2f, 150,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet16_Game_start.getDead(), planet16_Game_start.x, planet16_Game_start.y, paint);
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


            canvas.drawBitmap(planet16_Game_start.getPlanet1_game_start(), planet16_Game_start.x,planet16_Game_start.y, paint);

            for(planet16_bullet planet16Bullet : planet16_bullets)
                canvas.drawBitmap(planet16Bullet.planet16_Bullet, planet16Bullet.x, planet16Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet16_game.class));
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
                    planet16_Game_start.isGoingUp = true;
                }

                break;

            case  MotionEvent.ACTION_UP:
                planet16_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet16_Game_start.toShoot++;
                break;

        }


        return true;
    }


    public void newBullet() {

        planet16_bullet planet16_Bullet = new planet16_bullet(getResources());
        planet16_Bullet.x = planet16_Game_start.x + planet16_Game_start.width;
        planet16_Bullet.y = planet16_Game_start.y + (planet16_Game_start.height/10) ;

        planet16_bullets.add(planet16_Bullet);

    }
}