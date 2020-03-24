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

public class planet4_game_view extends SurfaceView implements Runnable {

    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, damage = 0, life = 5;
    private Paint paint;
    private planet4_bossbullet[] planet4_bossbullets;
    private Random random;
    private SharedPreferences prefs;
    private List<planet4_bullet> planet4_bullets;
    private planet4_boss planet4_Boss;
    private planet4_game_start planet4_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet4_game_activity activity;
    private planet4_background background1, background2;

    public planet4_game_view(planet4_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet4_background(screenX, screenY, getResources());
        background2 = new planet4_background(screenX, screenY, getResources());

        planet4_Game_start = new planet4_game_start(this, screenY, getResources());

        planet4_Boss = new planet4_boss(this, screenY, getResources());

        planet4_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet4_bossbullets = new planet4_bossbullet[4];

        for(int i = 0; i < 4; i++){

            planet4_bossbullet planet4_BossBullet = new planet4_bossbullet(getResources());
            planet4_bossbullets[i] = planet4_BossBullet;
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

        if (background1.x + background1.planet4_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet4_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet4_Game_start.isGoingUp)
            planet4_Game_start.y -= 30 * screenRatioY;
        else
            planet4_Game_start.y += 30 * screenRatioY;

        if (planet4_Game_start.y < 0)
            planet4_Game_start.y = 0;

        if (planet4_Game_start.y > screenY - planet4_Game_start.height)
            planet4_Game_start.y = screenY - planet4_Game_start.height;

        if (planet4_Boss.isGoingUp)
            planet4_Boss.y -= 20 * screenRatioY;
        else
            planet4_Boss.y += 20 * screenRatioY;

        if (planet4_Boss.y < 0)
            planet4_Boss.y = 0;



        if (planet4_Boss.y > screenY - planet4_Boss.height)
            planet4_Boss.y = screenY - planet4_Boss.height;


        if (planet4_Boss.y == 0)
            planet4_Boss.isGoingUp = false;

        if (planet4_Boss.y == screenY - planet4_Boss.height)
            planet4_Boss.isGoingUp = true;



        List<planet4_bullet> planet4_trash = new ArrayList<>();

        for (planet4_bullet planet4_Bullet : planet4_bullets){

            if (planet4_Bullet.x > screenX)
                planet4_trash.add(planet4_Bullet);

            planet4_Bullet.x += 50 * screenRatioX;

            for (planet4_bossbullet planet4_bossbullet : planet4_bossbullets){

                if (Rect.intersects(planet4_Boss.getCollisionShape(), planet4_Bullet.getCollisionShape())) {
                    damage++;
                    planet4_Bullet.x = +500;

                }

                if(Rect.intersects(planet4_bossbullet.getCollisionShape(), planet4_Bullet.getCollisionShape())){

                    planet4_bossbullet.x = -500;
                    planet4_Bullet.x = screenX + 500;
                    planet4_bossbullet.wasShot = true;
                }
                if(damage == 40){
                    stageFinished = true;
                }

            }
        }

        for (planet4_bullet planet4_Bullet : planet4_trash)
            planet4_bullets.remove(planet4_Bullet);

        for (planet4_bossbullet planet4_bossbullet : planet4_bossbullets) {

            planet4_bossbullet.x -= planet4_bossbullet.speed;

            if (planet4_bossbullet.x + planet4_bossbullet.width < 0) {

                if (!planet4_bossbullet.wasShot) {
                    planet4_bossbullet.x = -500;
                    planet4_bossbullet.wasShot = true;
                    return;
                }


                int bound = (int) (10 * screenRatioX);
                planet4_bossbullet.speed = random.nextInt(bound);

                if (planet4_bossbullet.speed < 10 * screenRatioX)
                    planet4_bossbullet.speed = (int) (10 * screenRatioX);

                planet4_bossbullet.x = planet4_Boss.x;
                planet4_bossbullet.y = planet4_Boss.y;

                planet4_bossbullet.wasShot = false;

            }
            if (Rect.intersects(planet4_Game_start.getCollisionShape(), planet4_bossbullet.getCollisionShape())) {
                life--;
                planet4_bossbullet.x = -1000;
                planet4_bossbullet.wasShot = true
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
            canvas.drawBitmap(background1.planet4_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet4_Background, background2.x, background2.y, paint);

            canvas.drawBitmap(planet4_Boss.getPlanet4_boss(), planet4_Boss.x, planet4_Boss.y, paint);

            for (planet4_bossbullet planet4_boss : planet4_bossbullets)

                canvas.drawBitmap(planet4_boss.getplanet4_bossbullet(), planet4_boss.x, planet4_boss.y, paint);

            canvas.drawText(damage + "", screenX / 2f, 150,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet4_Game_start.getDead(), planet4_Game_start.x, planet4_Game_start.y, paint);
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


            canvas.drawBitmap(planet4_Game_start.getPlanet1_game_start(), planet4_Game_start.x,planet4_Game_start.y, paint);

            for(planet4_bullet planet4Bullet : planet4_bullets)
                canvas.drawBitmap(planet4Bullet.planet4_Bullet, planet4Bullet.x, planet4Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet4_game.class));
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
                    planet4_Game_start.isGoingUp = true;
                }

                break;

            case  MotionEvent.ACTION_UP:
                planet4_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet4_Game_start.toShoot++;
                break;

        }


        return true;
    }


    public void newBullet() {

        planet4_bullet planet4_Bullet = new planet4_bullet(getResources());
        planet4_Bullet.x = planet4_Game_start.x + planet4_Game_start.width;
        planet4_Bullet.y = planet4_Game_start.y + (planet4_Game_start.height/3) ;

        planet4_bullets.add(planet4_Bullet);

    }
            }