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

public class planet9_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet9_enemy[] planet9_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet9_bullet> planet9_bullets;
    private planet9_game_start planet9_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet9_game_activity activity;
    private planet9_background background1, background2;

    public planet9_game_view(planet9_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet9_background(screenX, screenY, getResources());
        background2 = new planet9_background(screenX, screenY, getResources());

        planet9_Game_start = new planet9_game_start(this, screenY, getResources());

        planet9_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet9_enemies = new planet9_enemy[4];

        for(int i = 0; i < 4; i++){

            planet9_enemy planet9_Enemy = new planet9_enemy(getResources());
            planet9_enemies[i] = planet9_Enemy;
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

        if (background1.x + background1.planet9_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet9_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet9_Game_start.isGoingUp)
            planet9_Game_start.y -= 30 * screenRatioY;
        else
            planet9_Game_start.y += 30 * screenRatioY;

        if (planet9_Game_start.y < 0)
            planet9_Game_start.y = 0;

        if (planet9_Game_start.y > screenY - planet9_Game_start.height)
            planet9_Game_start.y = screenY - planet9_Game_start.height;


        List<planet9_bullet> planet9_trash = new ArrayList<>();

        for (planet9_bullet planet9_Bullet : planet9_bullets){

            if (planet9_Bullet.x > screenX)
                planet9_trash.add(planet9_Bullet);

            planet9_Bullet.x += 50 * screenRatioX;

            for (planet9_enemy planet9_enemy : planet9_enemies){

                if(Rect.intersects(planet9_enemy.getCollisionShape(), planet9_Bullet.getCollisionShape())){

                    kill++;
                    planet9_enemy.x = -500;
                    planet9_Bullet.x = screenX + 500;
                    planet9_enemy.wasShot = true;
                }
                if(kill == 40){
                    stageFinished = true;
                }

            }
        }

        for (planet9_bullet planet9_Bullet : planet9_trash)
            planet9_bullets.remove(planet9_Bullet);

        for (planet9_enemy planet9_enemy : planet9_enemies) {

            planet9_enemy.x -= planet9_enemy.speed;

            if (planet9_enemy.x + planet9_enemy.width < 0) {

                if (!planet9_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet9_enemy.speed = random.nextInt(bound);

                if (planet9_enemy.speed < 10 * screenRatioX)
                    planet9_enemy.speed = (int) (10 * screenRatioX);

                planet9_enemy.x = screenX;
                planet9_enemy.y = random.nextInt(screenY - planet9_enemy.height);

                planet9_enemy.wasShot = false;


            }
            if (Rect.intersects(planet9_Game_start.getCollisionShape(), planet9_enemy.getCollisionShape())) {
                life--;
                planet9_enemy.x = -500;
                planet9_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet9_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet9_Background, background2.x, background2.y, paint);

            for (planet9_enemy planet9_enemy : planet9_enemies)
                canvas.drawBitmap(planet9_enemy.getplanet9_enemy(), planet9_enemy.x, planet9_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet9_Game_start.getDead(), planet9_Game_start.x, planet9_Game_start.y, paint);
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


            canvas.drawBitmap(planet9_Game_start.getPlanet1_game_start(), planet9_Game_start.x,planet9_Game_start.y, paint);

            for(planet9_bullet planet9Bullet : planet9_bullets)
                canvas.drawBitmap(planet9Bullet.planet9_Bullet, planet9Bullet.x, planet9Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet9_game.class));
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
                    planet9_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet9_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet9_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet9_bullet planet9_Bullet = new planet9_bullet(getResources());
        planet9_Bullet.x = planet9_Game_start.x + planet9_Game_start.width;
        planet9_Bullet.y = planet9_Game_start.y + (planet9_Game_start.height/10) ;

        planet9_bullets.add(planet9_Bullet);

    }
}

