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

public class planet3_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet3_enemy[] planet3_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet3_bullet> planet3_bullets;
    private planet3_game_start planet3_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet3_game_activity activity;
    private planet3_background background1, background2;

    public planet3_game_view(planet3_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet3_background(screenX, screenY, getResources());
        background2 = new planet3_background(screenX, screenY, getResources());

        planet3_Game_start = new planet3_game_start(this, screenY, getResources());

        planet3_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet3_enemies = new planet3_enemy[4];

        for(int i = 0; i < 4; i++){

            planet3_enemy planet3_Enemy = new planet3_enemy(getResources());
            planet3_enemies[i] = planet3_Enemy;
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

        if (background1.x + background1.planet3_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet3_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet3_Game_start.isGoingUp)
            planet3_Game_start.y -= 30 * screenRatioY;
        else
            planet3_Game_start.y += 30 * screenRatioY;

        if (planet3_Game_start.y < 0)
            planet3_Game_start.y = 0;

        if (planet3_Game_start.y > screenY - planet3_Game_start.height)
            planet3_Game_start.y = screenY - planet3_Game_start.height;


        List<planet3_bullet> planet3_trash = new ArrayList<>();

        for (planet3_bullet planet3_Bullet : planet3_bullets){

            if (planet3_Bullet.x > screenX)
                planet3_trash.add(planet3_Bullet);

            planet3_Bullet.x += 50 * screenRatioX;

            for (planet3_enemy planet3_enemy : planet3_enemies){

                if(Rect.intersects(planet3_enemy.getCollisionShape(), planet3_Bullet.getCollisionShape())){

                    kill++;
                    planet3_enemy.x = -500;
                    planet3_Bullet.x = screenX + 500;
                    planet3_enemy.wasShot = true;
                }
                if(kill == 35){
                    stageFinished = true;
                }

            }
        }

        for (planet3_bullet planet3_Bullet : planet3_trash)
            planet3_bullets.remove(planet3_Bullet);

        for (planet3_enemy planet3_enemy : planet3_enemies) {

            planet3_enemy.x -= planet3_enemy.speed;

            if (planet3_enemy.x + planet3_enemy.width < 0) {

                if (!planet3_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet3_enemy.speed = random.nextInt(bound);

                if (planet3_enemy.speed < 10 * screenRatioX)
                    planet3_enemy.speed = (int) (10 * screenRatioX);

                planet3_enemy.x = screenX;
                planet3_enemy.y = random.nextInt(screenY - planet3_enemy.height);

                planet3_enemy.wasShot = false;


            }
            if (Rect.intersects(planet3_Game_start.getCollisionShape(), planet3_enemy.getCollisionShape())) {
                life--;
                planet3_enemy.x = -500;
                planet3_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet3_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet3_Background, background2.x, background2.y, paint);

            for (planet3_enemy planet3_enemy : planet3_enemies)
                canvas.drawBitmap(planet3_enemy.getplanet3_enemy(), planet3_enemy.x, planet3_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet3_Game_start.getDead(), planet3_Game_start.x, planet3_Game_start.y, paint);
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


            canvas.drawBitmap(planet3_Game_start.getPlanet1_game_start(), planet3_Game_start.x,planet3_Game_start.y, paint);

            for(planet3_bullet planet3Bullet : planet3_bullets)
                canvas.drawBitmap(planet3Bullet.planet3_Bullet, planet3Bullet.x, planet3Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet3_game.class));
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
                    planet3_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet3_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet3_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet3_bullet planet3_Bullet = new planet3_bullet(getResources());
        planet3_Bullet.x = planet3_Game_start.x + planet3_Game_start.width;
        planet3_Bullet.y = planet3_Game_start.y + (planet3_Game_start.height/10) ;

        planet3_bullets.add(planet3_Bullet);

    }
}

