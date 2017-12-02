package dev.uit.grablove.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.uit.grablove.R;
import info.hoang8f.widget.FButton;

public class BirthdayActivity extends AppCompatActivity {
    private MaterialEditText etDob;
    private FButton btnNext;
    private TextView tvDob;
    private Calendar calendarDob;
    private int day, month,year, currentYear;
    private ImageButton btnCalendarDob;
    private ImageButton btnBack;
    static BirthdayActivity birthdayActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        birthdayActivity = this;

        tvDob= (TextView) findViewById(R.id.tvDob);
        btnCalendarDob= (ImageButton) findViewById(R.id.btnCalenderBirthday) ;
        calendarDob = Calendar.getInstance();
        btnBack= (ImageButton) findViewById(R.id.btnBackBirthday);

        day= calendarDob.get(Calendar.DAY_OF_MONTH);
        month= calendarDob.get(Calendar.MONTH);
        year= calendarDob.get(Calendar.YEAR);

        month = month +1;
        tvDob.setText(day+"/"+month+"/"+year);


        btnNext = (FButton) findViewById(R.id.btnNextBirthday);

        btnCalendarDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BirthdayActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    i1 = i1+1;
                    tvDob.setText(i2+"/"+i1+"/"+i);
                    currentYear = i;
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSex = getIntent().getStringExtra("sex");

                Intent intent = new Intent(getBaseContext(), AvatarActivity.class);
                intent.putExtra("sex", strSex);
                intent.putExtra("age", Calendar.getInstance().get(Calendar.YEAR)-currentYear);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public static BirthdayActivity getInstance(){
        return   birthdayActivity;
    }
}
