package dev.uit.grablove.Fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import dev.uit.grablove.R;
import io.apptik.widget.MultiSlider;

public class fragment_tab1_setting_filter extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    SeekBar seekDistance;
    TextView txtDistance;
    MultiSlider rangeAge;
    TextView txtMinAge,txtMaxAge;
    Toolbar toolbar;
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
