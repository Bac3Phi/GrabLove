package dev.uit.grablove.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.uit.grablove.MainActivity;
import dev.uit.grablove.R;

public class WelcomeActivity extends Activity implements View.OnClickListener {
    Button btnSignIn,btnSignUp;
    TextView txtSlogan;
    Animation downtoup,uptodown;

    Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnTest  = (Button)findViewById(R.id.button2);
        LinearLayout layoutLogo = (LinearLayout) findViewById(R.id.layoutLogoWelcome);
        LinearLayout layoutButton = (LinearLayout) findViewById(R.id.layoutButtonWelcome);

        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        uptodown =  AnimationUtils.loadAnimation(this,R.anim.uptodown);

        layoutLogo.setAnimation(uptodown);
        layoutButton.setAnimation(downtoup);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(WelcomeActivity.this,SexActivity.class);
                startActivity(test);
            }
        });

        btnSignIn = (Button) findViewById(R.id.btnSignInWelcome);
        btnSignUp = (Button)findViewById(R.id.btnSignUpWelcome);

        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

/*        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/UVFLHLST.ttf");
        txtSlogan.setTypeface(face);


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(test);
            }
        });
       btnSignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent signIn = new Intent(WelcomeActivity.this,SignInActivity.class);
               startActivity(signIn);

           }
       });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(WelcomeActivity.this,SignUpActivity.class);
                startActivity(signUp);
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUpWelcome:
                Intent signUp = new Intent(WelcomeActivity.this,SignUpActivity.class);
                startActivity(signUp);
                break;
            case R.id.btnSignInWelcome:
                Intent signIn = new Intent(WelcomeActivity.this,SignInActivity.class);
                startActivity(signIn);
                break;
        }
    }
}
