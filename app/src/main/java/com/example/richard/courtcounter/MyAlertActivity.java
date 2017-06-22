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
 * Created by Richard on 6/7/2017.
 */

public class MyAlertActivity extends AppCompatActivity {

   public static final String INTENT_MESSAGE = "Yes";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myalert);

        //setting the height and the width of the popup window
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = (int)(metrics.widthPixels * .5);
        int height = (int)(metrics.heightPixels * .5);

        getWindow().setLayout(width, height);

        Button yesButton = (Button)findViewById(R.id.yes_button);
        Button noButton = (Button)findViewById(R.id.no_button);


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // 0 means that it will remove all the info
                intent.putExtra(INTENT_MESSAGE, 0);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra(INTENT_MESSAGE, 1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
