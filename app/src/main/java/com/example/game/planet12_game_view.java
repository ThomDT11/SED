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

public class planet12_game_view extends SurfaceView implements Runnable {

    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, damage = 0, life = 5;
    private Paint paint;
    private planet12_bossbullet[] planet12_bossbullets;
    private Random random;
    private SharedPreferences prefs;
    private List<planet12_bullet> planet12_bullets;
    private planet12_boss planet12_Boss;
    private planet12_game_start planet12_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet12_game_activity activity;
    private planet12_background background1, background2;

    public planet12_game_view(planet12_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet12_background(screenX, screenY, getResources());
        background2 = new planet12_background(screenX, screenY, getResources());

        planet12_Game_start = new planet12_game_start(this, screenY, getResources());

        planet12_Boss = new planet12_boss(this, screenY, getResources());

        planet12_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet12_bossbullets = new planet12_bossbullet[4];

        for(int i = 0; i < 4; i++){

            planet12_bossbullet planet12_BossBullet = new planet12_bossbullet(getResources());
            planet12_bossbullets[i] = planet12_BossBullet;
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

        if (background1.x + background1.planet12_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet12_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet12_Game_start.isGoingUp)
            planet12_Game_start.y -= 30 * screenRatioY;
        else
            planet12_Game_start.y += 30 * screenRatioY;

        if (planet12_Game_start.y < 0)
            planet12_Game_start.y = 0;

        if (planet12_Game_start.y > screenY - planet12_Game_start.height)
            planet12_Game_start.y = screenY - planet12_Game_start.height;

        if (planet12_Boss.isGoingUp)
            planet12_Boss.y -= 20 * screenRatioY;
        else
            planet12_Boss.y += 20 * screenRatioY;

        if (planet12_Boss.y < 0)
            planet12_Boss.y = 0;



        if (planet12_Boss.y > screenY - planet12_Boss.height)
            planet12_Boss.y = screenY - planet12_Boss.height;


        if (planet12_Boss.y == 0)
            planet12_Boss.isGoingUp = false;

        if (planet12_Boss.y == screenY - planet12_Boss.height)
            planet12_Boss.isGoingUp = true;



        List<planet12_bullet> planet12_trash = new ArrayList<>();

        for (planet12_bullet planet12_Bullet : planet12_bullets){

            if (planet12_Bullet.x > screenX)
                planet12_trash.add(planet12_Bullet);

            planet12_Bullet.x += 50 * screenRatioX;

            for (planet12_bossbullet planet12_bossbullet : planet12_bossbullets){

                if (Rect.intersects(planet12_Boss.getCollisionShape(), planet12_Bullet.getCollisionShape())) {
                    damage++;
                    planet12_Bullet.x = +500;

                }

                if(Rect.intersects(planet12_bossbullet.getCollisionShape(), planet12_Bullet.getCollisionShape())){

                    planet12_bossbullet.x = -500;
                    planet12_Bullet.x = screenX + 500;
                    planet12_bossbullet.wasShot = true;
                }
                if(damage == 60){
                    stageFinished = true;
                }

            }
        }

        for (planet12_bullet planet12_Bullet : planet12_trash)
            planet12_bullets.remove(planet12_Bullet);

        for (planet12_bossbullet planet12_bossbullet : planet12_bossbullets) {

            planet12_bossbullet.x -= planet12_bossbullet.speed;

            if (planet12_bossbullet.x + planet12_bossbullet.width < 0) {

                if (!planet12_bossbullet.wasShot) {
                    planet12_bossbullet.x = -500;
                    planet12_bossbullet.wasShot = true;
                    return;
                }


                int bound = (int) (10 * screenRatioX);
                planet12_bossbullet.speed = random.nextInt(bound);

                if (planet12_bossbullet.speed < 10 * screenRatioX)
                    planet12_bossbullet.speed = (int) (10 * screenRatioX);

                planet12_bossbullet.x = planet12_Boss.x;
                planet12_bossbullet.y = planet12_Boss.y * 2;

                planet12_bossbullet.wasShot = false;

            }
            if (Rect.intersects(planet12_Game_start.getCollisionShape(), planet12_bossbullet.getCollisionShape())) {
                life--;
                planet12_bossbullet.x = -1000;
                planet12_bossbullet.wasShot = true
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
            canvas.drawBitmap(background1.planet12_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet12_Background, background2.x, background2.y, paint);

            canvas.drawBitmap(planet12_Boss.getplanet12_boss(), planet12_Boss.x, planet12_Boss.y, paint);

            for (planet12_bossbullet planet12_boss : planet12_bossbullets)

                canvas.drawBitmap(planet12_boss.getplanet12_bossbullet(), planet12_boss.x, planet12_boss.y, paint);

            canvas.drawText(damage + "", screenX / 2f, 150,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet12_Game_start.getDead(), planet12_Game_start.x, planet12_Game_start.y, paint);
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


            canvas.drawBitmap(planet12_Game_start.getPlanet1_game_start(), planet12_Game_start.x,planet12_Game_start.y, paint);

            for(planet12_bullet planet12Bullet : planet12_bullets)
                canvas.drawBitmap(planet12Bullet.planet12_Bullet, planet12Bullet.x, planet12Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet12_game.class));
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
                    planet12_Game_start.isGoingUp = true;
                }

                break;

            case  MotionEvent.ACTION_UP:
                planet12_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet12_Game_start.toShoot++;
                break;

        }


        return true;
    }


    public void newBullet() {

        planet12_bullet planet12_Bullet = new planet12_bullet(getResources());
        planet12_Bullet.x = planet12_Game_start.x + planet12_Game_start.width;
        planet12_Bullet.y = planet12_Game_start.y + (planet12_Game_start.height/10) ;

        planet12_bullets.add(planet12_Bullet);

    }
}