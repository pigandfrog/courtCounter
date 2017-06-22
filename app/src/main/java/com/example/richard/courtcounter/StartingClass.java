package com.example.richard.courtcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Richard on 6/19/2017.
 */

public class StartingClass extends AppCompatActivity{

    EditText teamName;
    TextView team_to_choose;

    String firstTeam;
    String secondTeam = "";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acitivity_start_page);

        team_to_choose = (TextView)findViewById(R.id.enter_team_name_tv);
        teamName = (EditText)findViewById(R.id.team_edit_text);



        teamName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

               if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT){
                   if(firstTeam == null){
                       firstTeam = teamName.getText().toString();
                       team_to_choose.setText(R.string.enter_second_team_name);
                       teamName.setText("");
                       return true;



                   }
                   else{
                       secondTeam = teamName.getText().toString();
                    Intent newIntent   = new Intent(StartingClass.this, MainActivity.class);
                       newIntent.putExtra("firstTeam" ,firstTeam);
                       newIntent.putExtra("secondTeam", secondTeam);
                       startActivity(newIntent);
                       finish();
                   }





               }

                return false;
            }
        });


        /*teamName.requestFocus();
        //setting the focus listener that will bring up the soft keyboard when the edit Text has focus
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);*/
    }





}
