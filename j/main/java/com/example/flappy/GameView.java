package com.example.flappy;

import static com.example.flappy.Constants.SCREEN_HEIGHT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class GameView extends View  {
    private Bird bird;
    private Handler handler;//13 android.os
    private Runnable r;
    private ArrayList<Pipe>arrPipes;
    private int sumpipe,distance;
    private int score;
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        score =0;
        initBird();
        initPipe();
//        bird  = new Bird();
//        bird.setWidth(100*Constants.SCREEN_WIDTH/1000);
//        bird.setHeight(100*Constants.SCREEN_HEIGHT/1920);
//        bird.setX(100*Constants.SCREEN_WIDTH/1080);
//        bird.setY(SCREEN_HEIGHT/2-bird.getHeight()/2);
//        ArrayList<Bitmap> arrBms = new ArrayList<>();
//
//        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird));//IMAGE
//        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));//IMAGE
//        bird.setArrBms(arrBms);
        handler = new Handler();//13:10
        r = new Runnable() {
            @Override
            public void run() { invalidate(); }};
    }

    private void initPipe() {
        sumpipe = 6;
        distance =300*Constants.SCREEN_HEIGHT/1920;
        arrPipes = new ArrayList<>();
        for(int i=0;i<sumpipe;i++)
        {
         if(i<sumpipe/2){
             this.arrPipes.add(new Pipe(Constants.SCREEN_WIDTH+i*((Constants.SCREEN_WIDTH+200*Constants.SCREEN_WIDTH/1080)/(sumpipe/2)),
                     0,200*Constants.SCREEN_WIDTH/1080,Constants.SCREEN_HEIGHT/2));
            this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.toptube));
            this.arrPipes.get(this.arrPipes.size()-1).randomY();
         }else{
             this.arrPipes.add(new Pipe(this.arrPipes.get(i-sumpipe/2).getX(),this.arrPipes.get(i-sumpipe/2).getY()
             +this.arrPipes.get(i-sumpipe/2).getHeight()+this.distance,200*Constants.SCREEN_WIDTH/1080,SCREEN_HEIGHT/2));
             this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.bottomtube));
         }
        }
    }

    private void initBird() {
        bird  = new Bird();
        bird.setWidth(100*Constants.SCREEN_WIDTH/1000);
        bird.setHeight(100*Constants.SCREEN_HEIGHT/1920);
        bird.setX(100*Constants.SCREEN_WIDTH/1080);
        bird.setY(SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();

        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird));//IMAGE
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));//IMAGE
        bird.setArrBms(arrBms);
    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        bird.draw(canvas);//TEST
        for(int i=0;i<sumpipe;i++)
        {
            if(this.bird.getX()+this.bird.getWidth()>arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2
            &&this.bird.getX()+this.bird.getWidth()<=arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2+Pipe.speed
            &&i<sumpipe/2){
                score++;
                MainActivity.txt_score.setText(""+score);
            }
            if(this.arrPipes.get(i).getX()<-arrPipes.get(i).getWidth()){
                this.arrPipes.get(i).setX(Constants.SCREEN_WIDTH);
                if(i<sumpipe/2)
                {
                    arrPipes.get(i).randomY();
                }
                else{
                    arrPipes.get(i).setY(this.arrPipes.get(i-sumpipe/2).getY()
                            +this.arrPipes.get(i-sumpipe/2).getHeight()+this.distance);
                }
            }
           this.arrPipes.get(i).draw(canvas);//TEST
        }
        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
         bird.setDrop(-15);
        }//TEST
        return true;
    }
}
