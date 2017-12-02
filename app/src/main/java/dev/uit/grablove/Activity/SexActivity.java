package dev.uit.grablove.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import dev.uit.grablove.R;
import info.hoang8f.widget.FButton;

public class SexActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnMale;
    private ImageButton btnFemale;

    private Intent intent;

    static SexActivity sexActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);

        sexActivity = this;

        btnMale = (ImageButton) findViewById(R.id.btnMaleSex);
        btnFemale = ( ImageButton) findViewById(R.id.btnFemaleSex);

        intent = new Intent(getBaseContext(), BirthdayActivity.class);

        btnMale.setOnClickListener(this);
        btnFemale.setOnClickListener(this);

    }

    public static SexActivity getInstance(){
        return   sexActivity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMaleSex:
                intent.putExtra("sex", "male");
                startActivity(intent);
                break;
            case R.id.btnFemaleSex:
                intent.putExtra("sex", "female");
                startActivity(intent);
                break;
        }
    }
}
