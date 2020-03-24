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

public class planet14_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet14_enemy[] planet14_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet14_bullet> planet14_bullets;
    private planet14_game_start planet14_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet14_game_activity activity;
    private planet14_background background1, background2;

    public planet14_game_view(planet14_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet14_background(screenX, screenY, getResources());
        background2 = new planet14_background(screenX, screenY, getResources());

        planet14_Game_start = new planet14_game_start(this, screenY, getResources());

        planet14_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet14_enemies = new planet14_enemy[4];

        for(int i = 0; i < 4; i++){

            planet14_enemy planet14_Enemy = new planet14_enemy(getResources());
            planet14_enemies[i] = planet14_Enemy;
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

        if (background1.x + background1.planet14_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet14_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet14_Game_start.isGoingUp)
            planet14_Game_start.y -= 30 * screenRatioY;
        else
            planet14_Game_start.y += 30 * screenRatioY;

        if (planet14_Game_start.y < 0)
            planet14_Game_start.y = 0;

        if (planet14_Game_start.y > screenY - planet14_Game_start.height)
            planet14_Game_start.y = screenY - planet14_Game_start.height;


        List<planet14_bullet> planet14_trash = new ArrayList<>();

        for (planet14_bullet planet14_Bullet : planet14_bullets){

            if (planet14_Bullet.x > screenX)
                planet14_trash.add(planet14_Bullet);

            planet14_Bullet.x += 50 * screenRatioX;

            for (planet14_enemy planet14_enemy : planet14_enemies){

                if(Rect.intersects(planet14_enemy.getCollisionShape(), planet14_Bullet.getCollisionShape())){

                    kill++;
                    planet14_enemy.x = -500;
                    planet14_Bullet.x = screenX + 500;
                    planet14_enemy.wasShot = true;
                }
                if(kill == 60){
                    stageFinished = true;
                }

            }
        }

        for (planet14_bullet planet14_Bullet : planet14_trash)
            planet14_bullets.remove(planet14_Bullet);

        for (planet14_enemy planet14_enemy : planet14_enemies) {

            planet14_enemy.x -= planet14_enemy.speed;

            if (planet14_enemy.x + planet14_enemy.width < 0) {

                if (!planet14_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet14_enemy.speed = random.nextInt(bound);

                if (planet14_enemy.speed < 10 * screenRatioX)
                    planet14_enemy.speed = (int) (10 * screenRatioX);

                planet14_enemy.x = screenX;
                planet14_enemy.y = random.nextInt(screenY - planet14_enemy.height);

                planet14_enemy.wasShot = false;


            }
            if (Rect.intersects(planet14_Game_start.getCollisionShape(), planet14_enemy.getCollisionShape())) {
                life--;
                planet14_enemy.x = -500;
                planet14_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet14_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet14_Background, background2.x, background2.y, paint);

            for (planet14_enemy planet14_enemy : planet14_enemies)
                canvas.drawBitmap(planet14_enemy.getplanet14_enemy(), planet14_enemy.x, planet14_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet14_Game_start.getDead(), planet14_Game_start.x, planet14_Game_start.y, paint);
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


            canvas.drawBitmap(planet14_Game_start.getPlanet1_game_start(), planet14_Game_start.x,planet14_Game_start.y, paint);

            for(planet14_bullet planet14Bullet : planet14_bullets)
                canvas.drawBitmap(planet14Bullet.planet14_Bullet, planet14Bullet.x, planet14Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet14_game.class));
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
                    planet14_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet14_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet14_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet14_bullet planet14_Bullet = new planet14_bullet(getResources());
        planet14_Bullet.x = planet14_Game_start.x + planet14_Game_start.width;
        planet14_Bullet.y = planet14_Game_start.y + (planet14_Game_start.height/10) ;

        planet14_bullets.add(planet14_Bullet);

    }
}

