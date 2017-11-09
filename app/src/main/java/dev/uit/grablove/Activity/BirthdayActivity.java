package dev.uit.grablove.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import dev.uit.grablove.R;
import info.hoang8f.widget.FButton;

public class BirthdayActivity extends AppCompatActivity {
    private MaterialEditText etDob;
    private FButton btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        etDob = (MaterialEditText) findViewById(R.id.edtBirthdayBirthday);
        btnNext = (FButton) findViewById(R.id.btnNextBirthday);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSex = getIntent().getStringExtra("sex");

                Intent intent = new Intent(getBaseContext(), AvatarActivity.class);
                intent.putExtra("sex", strSex);
                intent.putExtra("dob", etDob.getText().toString());
                startActivity(intent);
            }
        });
    }
}
