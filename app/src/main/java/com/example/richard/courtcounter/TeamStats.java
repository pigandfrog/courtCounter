package com.example.richard.courtcounter;

import java.io.Serializable;

/**
 * Created by Richard on 6/8/2017.
 */

class TeamStats implements Serializable {

    int shot_attempts_3 = 0;
    int shot_attempts_2 = 0;
    int shot_attempts_1 = 0;

    int score = 0;
    int made_3 = 0;
    int made_2 = 0;
    int made_1 = 0;

    int the_Team_id;//this id will tell which team this is 0 for team a and 1 for team b

    String [] team_members;
    String name;


    /**
     *
     * @param the_shot_attempts_1
     * @param the_shot_attempts_2
     * @param the_shot_attempts_3
     * @param score_
     * @param made_1_
     * @param made_2_
     * @param made_3_
     * @param team_id
     *
     * this constructor will not utilize the team_member names
     */
    TeamStats(int the_shot_attempts_1, int the_shot_attempts_2, int the_shot_attempts_3, int score_, int made_1_,
              int made_2_, int made_3_, int team_id, String theName){

        shot_attempts_1 = the_shot_attempts_1;
        shot_attempts_2 = the_shot_attempts_2;
        shot_attempts_3 = the_shot_attempts_3;

        score = score_;
        made_3 = made_3_;
        made_2 = made_2_;
        made_1 = made_1_;

        the_Team_id = team_id;
        name = theName;

    }

    int getTeaamId(){
        return the_Team_id;
    }

    public  String toString(){

        return "team id is:  " + the_Team_id;
    }

   public String getName(){
        return name;
    }
}
