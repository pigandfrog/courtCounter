package com.example.richard.courtcounter;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Richard on 6/9/2017.
 */

public class StatsActivity extends AppCompatActivity {

    //constants
    private final static int CLEAR = 1;
    private final static int NO_CLEAR = 0;

    private static int TEAM_ID;

    Button otherStats;
    Button back_To_main;
    TextView teamName;

    TeamStats stats;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_stats);

        Intent theIntent = getIntent();

        stats = (TeamStats) theIntent.getSerializableExtra("theTeamStats");



        settingTheContents(stats, NO_CLEAR);
        TEAM_ID = stats.getTeaamId();


        otherStats = (Button) findViewById(R.id.other_teams_stats_button);
        back_To_main = (Button) findViewById(R.id.return_button);



        otherStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TEAM_ID = (TEAM_ID == 0)? 1:0;

               stats = getStats(TEAM_ID);
                settingTheContents(stats, CLEAR);
            }
        });

        back_To_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * this is a method that will set the view that is sent in
     * if the clear flag is 1 then will run the clearing of the previous number that was place in the
     * textView -- this is so that when switching from one teams stats to the other teams the
     * previous teams info is removed
     */
    private void setTheView(int resourceId, int number, int clearFlag) {

        TextView tv = (TextView) findViewById(resourceId);
        String text = tv.getText().toString();
       if(clearFlag ==  NO_CLEAR){

           tv.setText(text + "   " + number);
       }
       else {

           String newText = text.substring(0, (text.indexOf(':')+1));
           tv.setText(newText + "  " + number);
       }

    }

    private void setTheView(int resourceId, double number, int clearFlag) {

        TextView tv = (TextView) findViewById(resourceId);
        String text = tv.getText().toString();

        if(clearFlag == NO_CLEAR){
            tv.setText(text + "   " + number);
        }

        else {
            String newText = text.substring(0, (text.indexOf(':') + 1));
            tv.setText(newText + "  " + number);
        }

    }


    /**
     * method to set all the contents
     * if flag is at 1, then means need to clear the old numbers out of the textViews
     */
    private void settingTheContents(TeamStats stats, int clearFlag) {

        int madeShots = stats.made_2 + stats.made_3;
        int trys = stats.shot_attempts_3 + stats.shot_attempts_2;
        double percentage = (madeShots / (double) trys) *100.0 ;
        percentage = Math.round(percentage * 100.0) / 100.0;
        double free_throw_percentage = (stats.made_1/(double) stats.shot_attempts_1) * 100;
        free_throw_percentage = Math.round(free_throw_percentage * 10)/10;

        //this is for just setting the Team name;
        teamName = (TextView)findViewById(R.id.team_name);
        teamName.setText( stats.getName());

        setTheView(R.id.score_tv, stats.score, clearFlag);
        setTheView(R.id.three_attempted_tv, stats.shot_attempts_3, clearFlag);
        setTheView(R.id.shooting_percentage_tv, percentage, clearFlag);
        setTheView(R.id.three_made_tv, stats.made_3, clearFlag);
        setTheView(R.id.two_attempted_tv, stats.shot_attempts_2, clearFlag);
        setTheView(R.id.two_made_tv, stats.made_2, clearFlag);
        setTheView(R.id.free_throw_made_tv, stats.made_1, clearFlag);
        setTheView(R.id.free_throw_attempted_tv, stats.shot_attempts_1, clearFlag);
        setTheView(R.id.free_throw_percentage_tv, free_throw_percentage,clearFlag);
    }


    public native TeamStats getStats(int teamId);
}
