package com.example.omers.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ScoreBoardActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private TextView scores;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        this.scores = (TextView)findViewById(R.id.bodytext);
        this.sharedPref = getSharedPreferences("scores",MODE_PRIVATE);
        Map<String, ?> es = this.sharedPref.getAll();
        List<Map.Entry<String,?>> list = new LinkedList<Map.Entry<String, ?>>( es.entrySet() );
        String txt = "";
        Collections.sort(list, new Comparator<Map.Entry<String,?>>() {
            @Override
            public int compare(Map.Entry<String, ?> o1, Map.Entry<String, ?> o2) {
                double score1 = Double.parseDouble(o1.getValue().toString().split("     ")[2]);
                double score2 = Double.parseDouble(o2.getValue().toString().split("     ")[2]);
                return score1 < score2 ? -1 : (score1 == score2 ? 0 : 1);

            }
        });
        for (Map.Entry<String, ?> entry : list){
            txt += entry.getValue().toString()+"\n";
        }
        this.scores.setText(txt);

    }


}