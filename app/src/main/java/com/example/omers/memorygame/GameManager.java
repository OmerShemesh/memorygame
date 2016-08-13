package com.example.omers.memorygame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Collections;



public class GameManager {
    private  SharedPreferences sharePref;
    private Chronometer chronometer;
    private  ArrayList<ImageView> images;
    private Integer[] cubesArray;
    private int flipped = -1;
    private int winningCounter = 0;
    private int numOfImages;
    private Activity currentActivity;
    private String player;
    private String level;
    private SharedPreferences.Editor editor;
    private int[] imagesIds = {

            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,


    };
    public GameManager(int numOfImages,Activity current,TableLayout tableLayout,Chronometer chronometer,String player, String level, SharedPreferences sharedPref)
    {
        this.chronometer=chronometer;
        this.chronometer.setBase(SystemClock.elapsedRealtime());
        this.chronometer.start();
        this.images = getLevelImages(tableLayout);
        this.currentActivity=current;
        ArrayList<Integer> cubes = new ArrayList<>();
        this.numOfImages=numOfImages;
        for(int i=0;i<this.numOfImages;i++){
            cubes.add(i);
            cubes.add(i);
        }
        Collections.shuffle(cubes);
        this.cubesArray = (Integer[])cubes.toArray(new Integer[numOfImages]);

        this.player = player;
        this.level = level;
        this.editor = sharedPref.edit();
        this.sharePref = sharedPref;

        for(int i=0;i<numOfImages*2;i++){
            checkClicks(i);
        }




    }


    private ArrayList<ImageView> getLevelImages(TableLayout layout) {


        ArrayList<ImageView> images = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            TableRow row = (TableRow) layout.getChildAt(i);

            for (int j = 0; j < row.getChildCount(); j++) {

                View subView = row.getChildAt(j);

                if (subView instanceof ImageView) {
                    ImageView imageView = (ImageView) subView;
                    images.add(imageView);
                    //manipulate the imageView
                }
            }

        }
        return images;
    }
    private void checkClicks(final int index){
        ImageView image = this.images.get(index);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip(index);
                if(flipped == -1 || flipped == index){
                    flipped = index;
                }
                else {

                    if(cubesArray[flipped] == cubesArray[index]){
                        removeListener(index);
                        removeListener(flipped);
                        winningCounter++;
                        if(winningCounter == numOfImages){
                            chronometer.stop();

                           final double score = (double)(SystemClock.elapsedRealtime() - chronometer.getBase())/1000;

                            new AlertDialog.Builder(currentActivity)
                                    .setTitle("Well Played!")
                                    .setMessage("Finished Level in: " + Double.toString(score)+ " Seconds")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            currentActivity.finish();
                                            saveHighScore(score);


                                        }
                                    }).show();

                        }
                        flipped = -1;
                    }
                    else{
                        unSetClickable();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                unflip(index);
                                unflip(flipped);
                                setClickable();
                                flipped = -1;
                            }
                        },600);

                    }


                }


            }
        });
    }

    private void saveHighScore(double score){
        String newScoreString = String.format("%s     %s     %.3f",this.player,this.level,score);
        String lastScoreString = this.sharePref.getString(this.player+this.level,null);

        if(lastScoreString == null){
            this.editor.putString(this.player+this.level,newScoreString);
            this.editor.commit();
        }
        else{
            String[] arr = lastScoreString.split("     ");
            double lastScore = Double.parseDouble(arr[2]);
            if(score < lastScore) {
                this.editor.putString(this.player+this.level, newScoreString);
                this.editor.commit();
            }

        }


    }
    private void unSetClickable(){
        for (ImageView image : this.images) {
            image.setClickable(false);

        }
    }
    private void setClickable(){
        for (ImageView image : this.images) {
            image.setClickable(true);

        }
    }
    private void flip(int index){
        ImageView image = this.images.get(index);
        image.setImageResource(this.imagesIds[cubesArray[index]]);

    }

    private void unflip(int index){
        ImageView image = this.images.get(index);

        image.setImageResource(R.drawable.blank);

    }

    private void removeListener(int index){
        ImageView image = this.images.get(index);
        image.setOnClickListener(null);
    }


    }

