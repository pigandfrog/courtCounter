package com.example.richard.courtcounter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    //these are the global references

    static final int REQUEST_CODE = 1;
    static final int REMOVE_NAMES_CODE = 2;
    TeamStats theTeamStats;

    //TextView of the teams
    TextView team_1_tv;
    TextView team_2_tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the names of the two different teams
        Intent theIntent = getIntent();
       String firstTeam = theIntent.getStringExtra("firstTeam");
        String secondTeam = theIntent.getStringExtra("secondTeam");

        //sending the teamName to the native library
        createTeam(0, firstTeam);
        createTeam(1, secondTeam);

        team_1_tv = (TextView)findViewById(R.id.team_A);
        team_2_tv = (TextView)findViewById(R.id.teamB);

        team_1_tv.setText( firstTeam);
        team_2_tv.setText(secondTeam);




    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */


    public native int teamAScore(int a, int prevScore);
    public native int teamBScore(int a, int prevScore);
    public native void attemptANative(int typeAttempt, int team, boolean madeShot);
    public native int removeLastShot(int team);
    public native void resetAll();
    public native TeamStats getStats(int team);
    public native void createTeam(int teamId, String name);


    /**
     * this a native method that I put in that is found in 'native-lib' library
    */
    public native int [] myIntArry(int arr []);

    public void pointsA(View view){

        TextView v = (TextView)findViewById(R.id.team_a_score);
        int score = Integer.parseInt(v.getText().toString());
        int points = Integer.parseInt(view.getTag().toString());

        //calling the method that will register the attempts
        attemptANative(points, 0, true);

       points =  teamAScore(points, score);

        v.setText(Integer.toString(points));
    }

    public void pointsB(View view){

        TextView v = (TextView)findViewById(R.id.team_b_score);
        int score = Integer.parseInt(v.getText().toString());
        int points = Integer.parseInt(view.getTag().toString());

        attemptANative(points, 1, true);
       points =  teamBScore(points, score);


        v.setText(Integer.toString(points));
    }

    public void reset(View v){

        Intent intent = new Intent(this, MyAlertActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        

    }

    public void attemptA(View view){


        int type = Integer.parseInt(view.getTag().toString());
        attemptANative(type, 0, false); //zero is the int for team a
    }


    /*
    method for registering an attempted shot

     */
    public void attemptB(View view){

        int type = Integer.parseInt(view.getTag().toString());
        attemptANative(type, 1, false);
    }


    /**
     * method for removing a shot make or not
     * will recieve an int from the native method that will say the amount that will be removed
     */
    public void removeShot(View view){
        int id = view.getId();

        if(id == R.id.team_a_remove_attempt){

            int point = removeLastShot(0);
            if(point > 0){
                TextView score = (TextView)findViewById(R.id.team_a_score);
                int curScore =   Integer.parseInt(score.getText().toString());
                curScore -=point;
                score.setText(Integer.toString(curScore));
                Toast toast = Toast.makeText(this, "a " + point + " point basket has been removed",  Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            else if(point < 0){
                Toast toast = Toast.makeText(this, "a " +(- point) + " point missed shot removed", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            return;
        }
        else {
            int point = removeLastShot(1);

            if(point > 0){
                TextView score = (TextView)findViewById(R.id.team_b_score);
                int curScore =   Integer.parseInt(score.getText().toString());
                curScore -=point;
                score.setText(Integer.toString(curScore));
                Toast toast = Toast.makeText(this, "a " + point + " point basket has been removed",  Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            else if(point < 0){
                Toast toast = Toast.makeText(this, "a " +(- point) + " point missed shot removed", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        }
    }


    /**
     * overriding the onActiviyResult
     * using this to remove all the info that is stored
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //means it is the correct code -- means that it came from the MyAlertActivity class
        if(requestCode == REQUEST_CODE){

            if(resultCode == RESULT_OK){
                if(data.getIntExtra(MyAlertActivity.INTENT_MESSAGE, 1) == 0){

                    Intent intent = new Intent(MainActivity.this, RemoveNames.class);
                  startActivityForResult(intent,REMOVE_NAMES_CODE);
                        


                }
                return;
            }
        }
        if(requestCode == REMOVE_NAMES_CODE){
            // this will start the intent that will go back and try to get the teams names;
            if(resultCode == RESULT_OK && data.getIntExtra(RemoveNames.MESSAGE, 1) == 1){
                //if the code gets into this spot then we will remove the names of teams
                Intent theIntent = new Intent(this, StartingClass.class);
                //this will remove all the info that is found in the native area
                removeAllInfo();
                startActivity(theIntent);

            }
            else{
                removeAllInfo();
                return;
            }
        }
    }



    /**
     * the base method that will remove all the info from both the c++ side of the program and also the
     * java side
     */
    private void removeAllInfo(){

        TextView teamA = (TextView)findViewById(R.id.team_a_score);
        TextView teamB = (TextView)findViewById(R.id.team_b_score);

        teamA.setText(Integer.toString(0));
        teamB.setText((Integer.toString(0)));

        resetAll();
    }


    public void theStats(View view){

    }
    /**
     * method to begin getting the theTeamStats of the team that is chosen
     */
  public void stats(View view){

        int theTag = Integer.parseInt(view.getTag().toString());



      MyAsync task = new MyAsync();
      task.execute(theTag);



    }


    //this is a class that will be involved with calculating the stats of the team
    private class MyAsync extends AsyncTask <Integer, Void,TeamStats> {

        @Override
        protected TeamStats doInBackground(Integer... params) {

            theTeamStats = getStats(params[0]);


            return theTeamStats;
        }

        @Override
        protected void onPostExecute(TeamStats teamStats) {
            super.onPostExecute(teamStats);

            Intent theNewIntent = new Intent(MainActivity.this, StatsActivity.class);
            theNewIntent.putExtra("theTeamStats", theTeamStats);
            startActivity(theNewIntent);


        }
    }
}
