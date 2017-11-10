package dev.uit.grablove.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;

import dev.uit.grablove.Constants;
import dev.uit.grablove.MainActivity;
import dev.uit.grablove.R;

public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    ArrayList listBG= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutSplashScreen);

        listBG.add(R.drawable.background_firewatch_1);
        listBG.add(R.drawable.background_firewatch_2);
        listBG.add(R.drawable.background_firewatch_3);
        listBG.add(R.drawable.background_firewatch_4);
        listBG.add(R.drawable.background_firewatch_5);

        Collections.shuffle(listBG);

        relativeLayout.setBackgroundResource((Integer) listBG.get(0));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences pre=getSharedPreferences (Constants.REF_NAME,MODE_PRIVATE);

                if (pre.getBoolean(Constants.IS_LOG_IN,false)){
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
