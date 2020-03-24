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

public class planet15_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet15_enemy[] planet15_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet15_bullet> planet15_bullets;
    private planet15_game_start planet15_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet15_game_activity activity;
    private planet15_background background1, background2;

    public planet15_game_view(planet15_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet15_background(screenX, screenY, getResources());
        background2 = new planet15_background(screenX, screenY, getResources());

        planet15_Game_start = new planet15_game_start(this, screenY, getResources());

        planet15_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet15_enemies = new planet15_enemy[4];

        for(int i = 0; i < 4; i++){

            planet15_enemy planet15_Enemy = new planet15_enemy(getResources());
            planet15_enemies[i] = planet15_Enemy;
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

        if (background1.x + background1.planet15_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet15_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet15_Game_start.isGoingUp)
            planet15_Game_start.y -= 30 * screenRatioY;
        else
            planet15_Game_start.y += 30 * screenRatioY;

        if (planet15_Game_start.y < 0)
            planet15_Game_start.y = 0;

        if (planet15_Game_start.y > screenY - planet15_Game_start.height)
            planet15_Game_start.y = screenY - planet15_Game_start.height;


        List<planet15_bullet> planet15_trash = new ArrayList<>();

        for (planet15_bullet planet15_Bullet : planet15_bullets){

            if (planet15_Bullet.x > screenX)
                planet15_trash.add(planet15_Bullet);

            planet15_Bullet.x += 50 * screenRatioX;

            for (planet15_enemy planet15_enemy : planet15_enemies){

                if(Rect.intersects(planet15_enemy.getCollisionShape(), planet15_Bullet.getCollisionShape())){

                    kill++;
                    planet15_enemy.x = -500;
                    planet15_Bullet.x = screenX + 500;
                    planet15_enemy.wasShot = true;
                }
                if(kill == 65){
                    stageFinished = true;
                }

            }
        }

        for (planet15_bullet planet15_Bullet : planet15_trash)
            planet15_bullets.remove(planet15_Bullet);

        for (planet15_enemy planet15_enemy : planet15_enemies) {

            planet15_enemy.x -= planet15_enemy.speed;

            if (planet15_enemy.x + planet15_enemy.width < 0) {

                if (!planet15_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet15_enemy.speed = random.nextInt(bound);

                if (planet15_enemy.speed < 10 * screenRatioX)
                    planet15_enemy.speed = (int) (10 * screenRatioX);

                planet15_enemy.x = screenX;
                planet15_enemy.y = random.nextInt(screenY - planet15_enemy.height);

                planet15_enemy.wasShot = false;


            }
            if (Rect.intersects(planet15_Game_start.getCollisionShape(), planet15_enemy.getCollisionShape())) {
                life--;
                planet15_enemy.x = -500;
                planet15_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet15_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet15_Background, background2.x, background2.y, paint);

            for (planet15_enemy planet15_enemy : planet15_enemies)
                canvas.drawBitmap(planet15_enemy.getplanet15_enemy(), planet15_enemy.x, planet15_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet15_Game_start.getDead(), planet15_Game_start.x, planet15_Game_start.y, paint);
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


            canvas.drawBitmap(planet15_Game_start.getPlanet1_game_start(), planet15_Game_start.x,planet15_Game_start.y, paint);

            for(planet15_bullet planet15Bullet : planet15_bullets)
                canvas.drawBitmap(planet15Bullet.planet15_Bullet, planet15Bullet.x, planet15Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet15_game.class));
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
                    planet15_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet15_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet15_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet15_bullet planet15_Bullet = new planet15_bullet(getResources());
        planet15_Bullet.x = planet15_Game_start.x + planet15_Game_start.width;
        planet15_Bullet.y = planet15_Game_start.y + (planet15_Game_start.height/10) ;

        planet15_bullets.add(planet15_Bullet);

    }
}

