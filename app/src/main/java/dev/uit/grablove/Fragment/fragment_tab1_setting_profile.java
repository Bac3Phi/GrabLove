package dev.uit.grablove.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.uit.grablove.Activity.BirthdayActivity;
import dev.uit.grablove.R;

public class fragment_tab1_setting_profile extends AppCompatActivity {
    Toolbar toolbar;
    private  static  final  int PICK_IMAGE = 1;
    CircleImageView imgAvatar;
    private Calendar calendarDob;
    private MaterialEditText edtDob,edtName,edtdDes;
     int day, month,year, currentYear;
    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fragment_tab1_setting_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbarTab1Profile);
        CircleButton btnAdd = (CircleButton)findViewById(R.id.btnAddSettingProfile);
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatarSettingProfile) ;
        edtDob = (MaterialEditText) findViewById(R.id.edtBirthdayTab1Profile);
        edtName = (MaterialEditText)findViewById(R.id.edtNameTab1Profile);
        edtdDes = (MaterialEditText) findViewById(R.id.edtDesTab1Profile);
        calendarDob = Calendar.getInstance();


        day= calendarDob.get(Calendar.DAY_OF_MONTH);
        month= calendarDob.get(Calendar.MONTH);
        year= calendarDob.get(Calendar.YEAR);

        month = month +1;
        edtDob.setText(day+"/"+month+"/"+year);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(fragment_tab1_setting_profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        edtDob.setText(i2+"/"+i1+"/"+i);
                        currentYear = i;
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imgUri = data.getData();
            imgAvatar.setImageURI(imgUri);
        }
    }
}
