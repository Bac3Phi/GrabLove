package dev.uit.grablove.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dev.uit.grablove.R;
import info.hoang8f.widget.FButton;

public class SexActivity extends AppCompatActivity implements View.OnClickListener {
    private FButton btnMale;
    private FButton btnFemale;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);

        btnMale = (FButton) findViewById(R.id.btnMaleSex);
        btnFemale = (FButton) findViewById(R.id.btnFemaleSex);

        intent = new Intent(getBaseContext(), BirthdayActivity.class);

        btnMale.setOnClickListener(this);
        btnFemale.setOnClickListener(this);

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
