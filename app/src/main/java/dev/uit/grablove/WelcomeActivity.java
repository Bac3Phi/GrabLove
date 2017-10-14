package dev.uit.grablove;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    Button btnSignIn,btnSignUp;
    TextView txtSlogan;

    //Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
/*
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
       Button btnTest  = (Button)findViewById(R.id.button2);

        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
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
        });
*/
    }
}
