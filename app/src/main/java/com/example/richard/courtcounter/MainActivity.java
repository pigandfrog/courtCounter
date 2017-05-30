package com.example.richard.courtcounter;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */


    public native int teamAScore(int a, int prevScore);
    public native int teamBScore(int a, int prevScore);


    /**
     * this a native method that I put in that is found in 'native-lib' library
    */
    public native int [] myIntArry(int arr []);

    public void pointsA(View view){

        TextView v = (TextView)findViewById(R.id.team_a_score);
        int score = Integer.parseInt(v.getText().toString());
        int points = Integer.parseInt(view.getTag().toString());

       points =  teamAScore(points, score);

        v.setText(Integer.toString(points));
    }

    public void pointsB(View view){

        TextView v = (TextView)findViewById(R.id.team_b_score);
        int score = Integer.parseInt(v.getText().toString());
        int points = Integer.parseInt(view.getTag().toString());

       points =  teamBScore(points, score);

        v.setText(Integer.toString(points));
    }

    public void reset(View v){

        TextView teamA = (TextView)findViewById(R.id.team_a_score);
        TextView teamB = (TextView)findViewById(R.id.team_b_score);

        teamA.setText(Integer.toString(0));
        teamB.setText((Integer.toString(0)));
    }
}
