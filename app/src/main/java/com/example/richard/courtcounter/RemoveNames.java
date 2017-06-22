package com.example.richard.courtcounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Created by Richard on 6/19/2017.
 */

public class RemoveNames extends AppCompatActivity {


    Button yesButton;
    Button noButton;

    //the flag that will be yes is 0 and no is 1;
    int answerFlag;


    //this is the message that means that the names should be removed
    public  static final String MESSAGE = "answer";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remove_names);

        DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

       int height = (int)(displayMetrics.heightPixels * .4);
        int width = (int)(displayMetrics.widthPixels * .4);

        getWindow().setLayout(width, height);


        yesButton = (Button)findViewById(R.id.yes_button);
        noButton = (Button)findViewById(R.id.no_button);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerFlag = 0;
                sendAnswer(answerFlag);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerFlag = 1;
                sendAnswer(answerFlag);
            }
        });
    }


    protected void sendAnswer(int answerFlag)
    {
        Intent theIntent = new Intent();
        theIntent.putExtra(MESSAGE, answerFlag);
        //this is sending back the result of the the intent
        setResult(Activity.RESULT_OK, theIntent);
        finish();
    }
}
