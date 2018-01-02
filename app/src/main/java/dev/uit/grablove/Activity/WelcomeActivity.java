package dev.uit.grablove.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import dev.uit.grablove.Fragment.Tab3ChatFragment;
import dev.uit.grablove.Fragment.fragment_tab3_chat_communicate;
import dev.uit.grablove.MainActivity;
import dev.uit.grablove.R;

public class WelcomeActivity extends Activity implements View.OnClickListener {
    private Button btnSignIn,btnSignUp;
    private LoginButton btnLoginFacebook;
    private TextView txtSlogan;
    private Animation downtoup,uptodown;

    private CallbackManager callbackManager;

    private Button btnTest;

    static WelcomeActivity welcomeActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeActivity = this;

        callbackManager = CallbackManager.Factory.create();

        Button btnTest  = (Button)findViewById(R.id.btnTest);
        LinearLayout layoutLogo = (LinearLayout) findViewById(R.id.layoutLogoWelcome);
        LinearLayout layoutButton = (LinearLayout) findViewById(R.id.layoutButtonWelcome);

        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        uptodown =  AnimationUtils.loadAnimation(this,R.anim.uptodown);

        layoutLogo.setAnimation(uptodown);
        layoutButton.setAnimation(downtoup);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(WelcomeActivity.this,fragment_tab3_chat_communicate.class);
                startActivity(test);

            }
        });

        btnSignIn = (Button) findViewById(R.id.btnSignInWelcome);
        btnSignUp = (Button)findViewById(R.id.btnSignUpWelcome);
        btnLoginFacebook = findViewById(R.id.btnLoginFaceBook);
        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));

        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static WelcomeActivity getInstance(){
        return   welcomeActivity;
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
