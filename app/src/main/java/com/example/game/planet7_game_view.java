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

public class planet7_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet7_enemy[] planet7_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet7_bullet> planet7_bullets;
    private planet7_game_start planet7_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet7_game_activity activity;
    private planet7_background background1, background2;

    public planet7_game_view(planet7_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet7_background(screenX, screenY, getResources());
        background2 = new planet7_background(screenX, screenY, getResources());

        planet7_Game_start = new planet7_game_start(this, screenY, getResources());

        planet7_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet7_enemies = new planet7_enemy[4];

        for(int i = 0; i < 4; i++){

            planet7_enemy planet7_Enemy = new planet7_enemy(getResources());
            planet7_enemies[i] = planet7_Enemy;
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

        if (background1.x + background1.planet7_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet7_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet7_Game_start.isGoingUp)
            planet7_Game_start.y -= 30 * screenRatioY;
        else
            planet7_Game_start.y += 30 * screenRatioY;

        if (planet7_Game_start.y < 0)
            planet7_Game_start.y = 0;

        if (planet7_Game_start.y > screenY - planet7_Game_start.height)
            planet7_Game_start.y = screenY - planet7_Game_start.height;


        List<planet7_bullet> planet7_trash = new ArrayList<>();

        for (planet7_bullet planet7_Bullet : planet7_bullets){

            if (planet7_Bullet.x > screenX)
                planet7_trash.add(planet7_Bullet);

            planet7_Bullet.x += 50 * screenRatioX;

            for (planet7_enemy planet7_enemy : planet7_enemies){

                if(Rect.intersects(planet7_enemy.getCollisionShape(), planet7_Bullet.getCollisionShape())){

                    kill++;
                    planet7_enemy.x = -500;
                    planet7_Bullet.x = screenX + 500;
                    planet7_enemy.wasShot = true;
                }
                if(kill == 45){
                    stageFinished = true;
                }

            }
        }

        for (planet7_bullet planet7_Bullet : planet7_trash)
            planet7_bullets.remove(planet7_Bullet);

        for (planet7_enemy planet7_enemy : planet7_enemies) {

            planet7_enemy.x -= planet7_enemy.speed;

            if (planet7_enemy.x + planet7_enemy.width < 0) {

                if (!planet7_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet7_enemy.speed = random.nextInt(bound);

                if (planet7_enemy.speed < 10 * screenRatioX)
                    planet7_enemy.speed = (int) (10 * screenRatioX);

                planet7_enemy.x = screenX;
                planet7_enemy.y = random.nextInt(screenY - planet7_enemy.height);

                planet7_enemy.wasShot = false;


            }
            if (Rect.intersects(planet7_Game_start.getCollisionShape(), planet7_enemy.getCollisionShape())) {
                life--;
                planet7_enemy.x = -500;
                planet7_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet7_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet7_Background, background2.x, background2.y, paint);

            for (planet7_enemy planet7_enemy : planet7_enemies)
                canvas.drawBitmap(planet7_enemy.getplanet7_enemy(), planet7_enemy.x, planet7_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet7_Game_start.getDead(), planet7_Game_start.x, planet7_Game_start.y, paint);
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


            canvas.drawBitmap(planet7_Game_start.getPlanet1_game_start(), planet7_Game_start.x,planet7_Game_start.y, paint);

            for(planet7_bullet planet7Bullet : planet7_bullets)
                canvas.drawBitmap(planet7Bullet.planet7_Bullet, planet7Bullet.x, planet7Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet7_game.class));
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
                    planet7_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet7_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet7_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet7_bullet planet7_Bullet = new planet7_bullet(getResources());
        planet7_Bullet.x = planet7_Game_start.x + planet7_Game_start.width;
        planet7_Bullet.y = planet7_Game_start.y + (planet7_Game_start.height/10) ;

        planet7_bullets.add(planet7_Bullet);

    }
}

