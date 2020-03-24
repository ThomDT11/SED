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

public class planet2_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet2_enemy[] planet2_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet2_bullet> planet2_bullets;
    private planet2_game_start planet2_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet2_game_activity activity;
    private planet2_background background1, background2;

    public planet2_game_view(planet2_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet2_background(screenX, screenY, getResources());
        background2 = new planet2_background(screenX, screenY, getResources());

        planet2_Game_start = new planet2_game_start(this, screenY, getResources());

        planet2_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet2_enemies = new planet2_enemy[4];

        for(int i = 0; i < 4; i++){

            planet2_enemy planet2_Enemy = new planet2_enemy(getResources());
            planet2_enemies[i] = planet2_Enemy;
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

        if (background1.x + background1.planet2_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet2_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet2_Game_start.isGoingUp)
            planet2_Game_start.y -= 30 * screenRatioY;
        else
            planet2_Game_start.y += 30 * screenRatioY;

        if (planet2_Game_start.y < 0)
            planet2_Game_start.y = 0;

        if (planet2_Game_start.y > screenY - planet2_Game_start.height)
            planet2_Game_start.y = screenY - planet2_Game_start.height;


        List<planet2_bullet> planet2_trash = new ArrayList<>();

        for (planet2_bullet planet2_Bullet : planet2_bullets){

            if (planet2_Bullet.x > screenX)
                planet2_trash.add(planet2_Bullet);

            planet2_Bullet.x += 50 * screenRatioX;

            for (planet2_enemy planet2_enemy : planet2_enemies){

                if(Rect.intersects(planet2_enemy.getCollisionShape(), planet2_Bullet.getCollisionShape())){

                    kill++;
                    planet2_enemy.x = -500;
                    planet2_Bullet.x = screenX + 500;
                    planet2_enemy.wasShot = true;
                }
                if(kill == 30){
                    stageFinished = true;
                }

            }
        }

        for (planet2_bullet planet2_Bullet : planet2_trash)
            planet2_bullets.remove(planet2_Bullet);

        for (planet2_enemy planet2_enemy : planet2_enemies) {

            planet2_enemy.x -= planet2_enemy.speed;

            if (planet2_enemy.x + planet2_enemy.width < 0) {

                if (!planet2_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet2_enemy.speed = random.nextInt(bound);

                if (planet2_enemy.speed < 10 * screenRatioX)
                    planet2_enemy.speed = (int) (10 * screenRatioX);

                planet2_enemy.x = screenX;
                planet2_enemy.y = random.nextInt(screenY - planet2_enemy.height);

                planet2_enemy.wasShot = false;


            }
            if (Rect.intersects(planet2_Game_start.getCollisionShape(), planet2_enemy.getCollisionShape())) {
                life--;
                planet2_enemy.x = -500;
                planet2_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet2_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet2_Background, background2.x, background2.y, paint);

            for (planet2_enemy planet2_enemy : planet2_enemies)
                canvas.drawBitmap(planet2_enemy.getplanet2_enemy(), planet2_enemy.x, planet2_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet2_Game_start.getDead(), planet2_Game_start.x, planet2_Game_start.y, paint);
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


            canvas.drawBitmap(planet2_Game_start.getPlanet1_game_start(), planet2_Game_start.x,planet2_Game_start.y, paint);

            for(planet2_bullet planet2Bullet : planet2_bullets)
                canvas.drawBitmap(planet2Bullet.planet2_Bullet, planet2Bullet.x, planet2Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet2_game.class));
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
                    planet2_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet2_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet2_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet2_bullet planet2_Bullet = new planet2_bullet(getResources());
        planet2_Bullet.x = planet2_Game_start.x + planet2_Game_start.width;
        planet2_Bullet.y = planet2_Game_start.y + (planet2_Game_start.height/10) ;

        planet2_bullets.add(planet2_Bullet);

    }
}

