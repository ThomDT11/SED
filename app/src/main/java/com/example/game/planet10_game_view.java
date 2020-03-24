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

public class planet10_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet10_enemy[] planet10_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet10_bullet> planet10_bullets;
    private planet10_game_start planet10_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet10_game_activity activity;
    private planet10_background background1, background2;

    public planet10_game_view(planet10_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet10_background(screenX, screenY, getResources());
        background2 = new planet10_background(screenX, screenY, getResources());

        planet10_Game_start = new planet10_game_start(this, screenY, getResources());

        planet10_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet10_enemies = new planet10_enemy[4];

        for(int i = 0; i < 4; i++){

            planet10_enemy planet10_Enemy = new planet10_enemy(getResources());
            planet10_enemies[i] = planet10_Enemy;
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

        if (background1.x + background1.planet10_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet10_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet10_Game_start.isGoingUp)
            planet10_Game_start.y -= 30 * screenRatioY;
        else
            planet10_Game_start.y += 30 * screenRatioY;

        if (planet10_Game_start.y < 0)
            planet10_Game_start.y = 0;

        if (planet10_Game_start.y > screenY - planet10_Game_start.height)
            planet10_Game_start.y = screenY - planet10_Game_start.height;


        List<planet10_bullet> planet10_trash = new ArrayList<>();

        for (planet10_bullet planet10_Bullet : planet10_bullets){

            if (planet10_Bullet.x > screenX)
                planet10_trash.add(planet10_Bullet);

            planet10_Bullet.x += 50 * screenRatioX;

            for (planet10_enemy planet10_enemy : planet10_enemies){

                if(Rect.intersects(planet10_enemy.getCollisionShape(), planet10_Bullet.getCollisionShape())){

                    kill++;
                    planet10_enemy.x = -500;
                    planet10_Bullet.x = screenX + 500;
                    planet10_enemy.wasShot = true;
                }
                if(kill == 50){
                    stageFinished = true;
                }

            }
        }

        for (planet10_bullet planet10_Bullet : planet10_trash)
            planet10_bullets.remove(planet10_Bullet);

        for (planet10_enemy planet10_enemy : planet10_enemies) {

            planet10_enemy.x -= planet10_enemy.speed;

            if (planet10_enemy.x + planet10_enemy.width < 0) {

                if (!planet10_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet10_enemy.speed = random.nextInt(bound);

                if (planet10_enemy.speed < 10 * screenRatioX)
                    planet10_enemy.speed = (int) (10 * screenRatioX);

                planet10_enemy.x = screenX;
                planet10_enemy.y = random.nextInt(screenY - planet10_enemy.height);

                planet10_enemy.wasShot = false;


            }
            if (Rect.intersects(planet10_Game_start.getCollisionShape(), planet10_enemy.getCollisionShape())) {
                life--;
                planet10_enemy.x = -500;
                planet10_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet10_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet10_Background, background2.x, background2.y, paint);

            for (planet10_enemy planet10_enemy : planet10_enemies)
                canvas.drawBitmap(planet10_enemy.getplanet10_enemy(), planet10_enemy.x, planet10_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet10_Game_start.getDead(), planet10_Game_start.x, planet10_Game_start.y, paint);
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


            canvas.drawBitmap(planet10_Game_start.getPlanet1_game_start(), planet10_Game_start.x,planet10_Game_start.y, paint);

            for(planet10_bullet planet10Bullet : planet10_bullets)
                canvas.drawBitmap(planet10Bullet.planet10_Bullet, planet10Bullet.x, planet10Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet10_game.class));
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
                    planet10_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet10_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet10_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet10_bullet planet10_Bullet = new planet10_bullet(getResources());
        planet10_Bullet.x = planet10_Game_start.x + planet10_Game_start.width;
        planet10_Bullet.y = planet10_Game_start.y + (planet10_Game_start.height/10) ;

        planet10_bullets.add(planet10_Bullet);

    }
}

