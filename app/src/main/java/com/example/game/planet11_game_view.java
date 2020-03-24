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

public class planet11_game_view extends SurfaceView implements Runnable {


    private Thread thread;
    private Boolean isPlaying, isGameOver = false, stageFinished = false;
    private int screenX, screenY, kill = 0, life = 5;
    private Paint paint;
    private planet11_enemy[] planet11_enemies;
    private Random random;
    private SharedPreferences prefs;
    private List<planet11_bullet> planet11_bullets;
    private planet11_game_start planet11_Game_start;
    public static float screenRatioX, screenRatioY;
    private planet11_game_activity activity;
    private planet11_background background1, background2;

    public planet11_game_view(planet11_game_activity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 2560f / screenX;
        screenRatioY = 1440f / screenY;

        background1 = new planet11_background(screenX, screenY, getResources());
        background2 = new planet11_background(screenX, screenY, getResources());

        planet11_Game_start = new planet11_game_start(this, screenY, getResources());

        planet11_bullets = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        planet11_enemies = new planet11_enemy[4];

        for(int i = 0; i < 4; i++){

            planet11_enemy planet11_Enemy = new planet11_enemy(getResources());
            planet11_enemies[i] = planet11_Enemy;
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

        if (background1.x + background1.planet11_Background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.planet11_Background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (planet11_Game_start.isGoingUp)
            planet11_Game_start.y -= 30 * screenRatioY;
        else
            planet11_Game_start.y += 30 * screenRatioY;

        if (planet11_Game_start.y < 0)
            planet11_Game_start.y = 0;

        if (planet11_Game_start.y > screenY - planet11_Game_start.height)
            planet11_Game_start.y = screenY - planet11_Game_start.height;


        List<planet11_bullet> planet11_trash = new ArrayList<>();

        for (planet11_bullet planet11_Bullet : planet11_bullets){

            if (planet11_Bullet.x > screenX)
                planet11_trash.add(planet11_Bullet);

            planet11_Bullet.x += 50 * screenRatioX;

            for (planet11_enemy planet11_enemy : planet11_enemies){

                if(Rect.intersects(planet11_enemy.getCollisionShape(), planet11_Bullet.getCollisionShape())){

                    kill++;
                    planet11_enemy.x = -500;
                    planet11_Bullet.x = screenX + 500;
                    planet11_enemy.wasShot = true;
                }
                if(kill == 55){
                    stageFinished = true;
                }

            }
        }

        for (planet11_bullet planet11_Bullet : planet11_trash)
            planet11_bullets.remove(planet11_Bullet);

        for (planet11_enemy planet11_enemy : planet11_enemies) {

            planet11_enemy.x -= planet11_enemy.speed;

            if (planet11_enemy.x + planet11_enemy.width < 0) {

                if (!planet11_enemy.wasShot) {
                    life--;
                    return;
                }


                int bound = (int) (30 * screenRatioX);
                planet11_enemy.speed = random.nextInt(bound);

                if (planet11_enemy.speed < 10 * screenRatioX)
                    planet11_enemy.speed = (int) (10 * screenRatioX);

                planet11_enemy.x = screenX;
                planet11_enemy.y = random.nextInt(screenY - planet11_enemy.height);

                planet11_enemy.wasShot = false;


            }
            if (Rect.intersects(planet11_Game_start.getCollisionShape(), planet11_enemy.getCollisionShape())) {
                life--;
                planet11_enemy.x = -500;
                planet11_enemy.wasShot = true
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
            canvas.drawBitmap(background1.planet11_Background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.planet11_Background, background2.x, background2.y, paint);

            for (planet11_enemy planet11_enemy : planet11_enemies)
                canvas.drawBitmap(planet11_enemy.getplanet11_enemy(), planet11_enemy.x, planet11_enemy.y, paint);

            canvas.drawText(kill + "", screenX / 2f, 164,  paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(planet11_Game_start.getDead(), planet11_Game_start.x, planet11_Game_start.y, paint);
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


            canvas.drawBitmap(planet11_Game_start.getPlanet1_game_start(), planet11_Game_start.x,planet11_Game_start.y, paint);

            for(planet11_bullet planet11Bullet : planet11_bullets)
                canvas.drawBitmap(planet11Bullet.planet11_Bullet, planet11Bullet.x, planet11Bullet.y, paint);

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
            activity.startActivity(new Intent(activity, planet11_game.class));
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
                    planet11_Game_start.isGoingUp = true;
                }
                break;
            case  MotionEvent.ACTION_UP:
                planet11_Game_start.isGoingUp= false;

                if (event.getX() > screenX / 2)
                    planet11_Game_start.toShoot++;
                break;

        }


        return true;
    }

    public void newBullet() {

        planet11_bullet planet11_Bullet = new planet11_bullet(getResources());
        planet11_Bullet.x = planet11_Game_start.x + planet11_Game_start.width;
        planet11_Bullet.y = planet11_Game_start.y + (planet11_Game_start.height/10) ;

        planet11_bullets.add(planet11_Bullet);

    }
}

