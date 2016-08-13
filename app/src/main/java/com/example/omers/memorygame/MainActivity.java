package com.example.omers.memorygame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    public final static String USER_NAME = "USERNAME";
    public final static String LEVEL = "LEVEL";
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private RadioGroup group;
    private Button btnPlay;
    private Button btnScoreBoard;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);
        this.sharedPref = this.getSharedPreferences("users", Context.MODE_PRIVATE);
        this.editor = sharedPref.edit();
        this.btnPlay = (Button) findViewById(R.id.btnPlay);
        this.btnScoreBoard = (Button) findViewById(R.id.btnScoreBoard);
        this.group = (RadioGroup) findViewById(R.id.radio_group);
        this.username = (EditText) findViewById(R.id.username);


        if (btnPlay != null) {
            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (group.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(MainActivity.this, "Please Select a Level!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (username.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "Please Enter a Player's Name!", Toast.LENGTH_SHORT).show();
                        } else {
                            int selection = group.getCheckedRadioButtonId();
                            String user =  username.getText().toString();
                            editor.putString(user,user);
                            editor.commit();
                            int level = 0;
                            switch (selection) {
                                case R.id.lvl1:
                                    level = 1;
                                    break;
                                case R.id.lvl2:
                                    level = 2;
                                    break;
                                case R.id.lvl3:
                                    level = 3;
                                    break;
                            }
                            intent = new Intent(MainActivity.this, LevelActivity.class);
                            intent.putExtra(USER_NAME, user);
                            intent.putExtra(LEVEL,level);
                            startActivity(intent);
                        }

                    }


                }

            });

            if(btnScoreBoard != null){
                btnScoreBoard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(MainActivity.this,ScoreBoardActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

    }


}

