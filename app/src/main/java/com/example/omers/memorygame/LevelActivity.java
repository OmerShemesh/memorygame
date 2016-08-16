package com.example.omers.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class LevelActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private TextView userName;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.USER_NAME);
        int level = intent.getIntExtra(MainActivity.LEVEL,0);

        this.sharedPref = getSharedPreferences("scores",MODE_PRIVATE);
        this.editor = sharedPref.edit();

        TableLayout table;
        switch (level){
            case 1:
                setContentView(R.layout.level1);
                this.userName=(TextView)findViewById(R.id.level1_userText);
                this.userName.setText(message);
                this.chronometer=(Chronometer)findViewById(R.id.level1_chronometer);
                table = (TableLayout) findViewById(R.id.tableLayout1);
                new GameManager(2, this, table,this.chronometer,message,"Level 1",sharedPref);
                break;
            case 2:
                setContentView(R.layout.level2);
                this.userName=(TextView)findViewById(R.id.level2_userText);
                this.userName.setText(message);
                this.chronometer=(Chronometer)findViewById(R.id.level2_chronometer);
                table = (TableLayout) findViewById(R.id.tableLayout2);
                new GameManager(4, this, table,this.chronometer,message,"Level 2",sharedPref);
                break;
            case 3:
                setContentView(R.layout.level3);
                this.userName=(TextView)findViewById(R.id.level3_userText);
                this.userName.setText(message);
                this.chronometer=(Chronometer)findViewById(R.id.level3_chronometer);
                table = (TableLayout) findViewById(R.id.tableLayout3);
                new GameManager(6, this, table,this.chronometer,message,"Level 3",sharedPref);
                break;
        }
    }


}