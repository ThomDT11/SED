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

public class planet6_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet6_enemy[] planet6_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet6_bullet> planet6_bullets;
    private planet6_game_start planet6_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet6_game_activity activity;
    private planet6_background background1, background2;

    public planet6_game_view(planet6_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet6_background(screenX, screenY, getResources());
        background2 = new planet6_background(screenX, screenY, getResources());

        planet6_Game_start = new planet6_game_start(this, screenY, getResources());

        planet6_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet6_enemies = new planet6_enemy[4];

        for(int i = 0; i < 4; i++){

            planet6_enemy planet6_Enemy = new planet6_enemy(getResources());
            planet6_enemies[i] = planet6_Enemy;
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

        if (background1.x + background1.planet6_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet6_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet6_Game_start.isGoingUp)
            planet6_Game_start.y -= 30 * screenRatioY;
        else
            planet6_Game_start.y += 30 * screenRatioY;

        if (planet6_Game_start.y < 0)
            planet6_Game_start.y = 0;

        if (planet6_Game_start.y > screenY - planet6_Game_start.height)
            planet6_Game_start.y = screenY - planet6_Game_start.height;


        List<planet6_bullet> planet6_trash = new ArrayList<>();

        for (planet6_bullet planet6_Bullet : planet6_bullets){

            if (planet6_Bullet.x > screenX)
                planet6_trash.add(planet6_Bullet);

            planet6_Bullet.x += 50 * screenRatioX;

            for (planet6_enemy planet6_enemy : planet6_enemies){

                if(Rect.intersects(planet6_enemy.getCollisionShape(), planet6_Bullet.getCollisionShape())){

                    kill++;
                    planet6_enemy.x = -500;
                    planet6_Bullet.x = screenX + 500;
                    planet6_enemy.wasShot = true;
                }
                if(kill == 40){
                    stageFinished = true;
                }

            }
        }

        for (planet6_bullet planet6_Bullet : planet6_trash)
            planet6_bullets.remove(planet6_Bullet);

        for (planet6_enemy planet6_enemy : planet6_enemies) {

            planet6_enemy.x -= planet6_enemy.speed;

            if (planet6_enemy.x + planet6_enemy.width < 0) {

                if (!planet6_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet6_enemy.speed = random.nextInt(bound);

                if (planet6_enemy.speed < 10 * screenRatioX)
                    planet6_enemy.speed = (int) (10 * screenRatioX);

                planet6_enemy.x = screenX;
                planet6_enemy.y = random.nextInt(screenY - planet6_enemy.height);

                planet6_enemy.wasShot = false;


            }
            if (Rect.intersects(planet6_Game_start.getCollisionShape(), planet6_enemy.getCollisionShape())) {
                life--;
                planet6_enemy.x = -500;
                planet6_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet6_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet6_Background, background2.x, background2.y, paint);

            for (planet6_enemy planet6_enemy : planet6_enemies)
                canvas.drawBitmap(planet6_enemy.getplanet6_enemy(), planet6_enemy.x, planet6_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet6_Game_start.getDead(), planet6_Game_start.x, planet6_Game_start.y, paint);
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


            canvas.drawBitmap(planet6_Game_start.getPlanet1_game_start(), planet6_Game_start.x,planet6_Game_start.y, paint);

            for(planet6_bullet planet6Bullet : planet6_bullets)
                canvas.drawBitmap(planet6Bullet.planet6_Bullet, planet6Bullet.x, planet6Bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void saveIfDone() {

        if (prefs.getInt("highscore",  0 ) < kill){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt ("highscore", kill);
            editor.apply();

        }

    }

    private void waitBeforeExiting(){

        try {
            Thread.sleep(1000);
            activity.startActivity(new Intent(activity, planet6_game.class));
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
                    planet6_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet6_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet6_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet6_bullet planet6_Bullet = new planet6_bullet(getResources());
        planet6_Bullet.x = planet6_Game_start.x + planet6_Game_start.width;
        planet6_Bullet.y = planet6_Game_start.y + (planet6_Game_start.height/10) ;

        planet6_bullets.add(planet6_Bullet);

    }
}

