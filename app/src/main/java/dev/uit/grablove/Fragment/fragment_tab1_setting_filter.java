package dev.uit.grablove.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import dev.uit.grablove.Activity.SignInActivity;
import dev.uit.grablove.Activity.WelcomeActivity;
import dev.uit.grablove.Constants;
import dev.uit.grablove.MainActivity;
import dev.uit.grablove.R;
import io.apptik.widget.MultiSlider;

public class fragment_tab1_setting_filter extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar seekDistance;
    private TextView txtDistance;
    private MultiSlider rangeAge;
    private TextView txtMinAge,txtMaxAge;
    private Toolbar toolbar;

    private Button btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab1_setting_filter);

        toolbar =(Toolbar) findViewById(R.id.toolbarTab1Filter);
        seekDistance = (SeekBar)findViewById(R.id.seekDistance);
        txtDistance = (TextView) findViewById(R.id.txtDistance);
        rangeAge = (MultiSlider) findViewById(R.id.rangeAgeTab1);

        txtMinAge = (TextView) findViewById(R.id.txtMinAge);
        txtMaxAge = (TextView) findViewById(R.id.txtMaxAge);

        rangeAge.setMin(18);
        rangeAge.setMax(100);
        rangeAge.getThumb(1).setValue(29);

        txtMinAge.setText(String.valueOf(rangeAge.getThumb(0).getValue()));
        txtMaxAge.setText(String.valueOf(rangeAge.getThumb(1).getValue()));
        seekDistance.setOnSeekBarChangeListener(this);
        rangeAge.setOnThumbValueChangeListener(new MultiSlider.SimpleChangeListener() {
                @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int
                    thumbIndex, int value) {
                if (thumbIndex == 0) {
                    txtMinAge.setText(String.valueOf(value));
                } else {
                    txtMaxAge.setText(String.valueOf(value));
                }
            }
        });
        setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(Constants.REF_NAME, Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent welcome = new Intent(getBaseContext(),WelcomeActivity.class);
                startActivity(welcome);
                finish();
                MainActivity.getInstance().finish();
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        txtDistance.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


}
