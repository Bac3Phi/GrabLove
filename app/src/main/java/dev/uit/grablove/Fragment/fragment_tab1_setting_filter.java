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
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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

    private Switch switchMale;
    private Switch switchFemale;
    private Switch switchNewMatches;
    private Switch switchMessages;

    private Button btnLogOut;

    private SharedPreferences pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab1_setting_filter);

        pre=getSharedPreferences (Constants.REF_NAME,MODE_PRIVATE);

        map();

        settingSwitches();

        rangeAge.setMin(18);
        rangeAge.setMax(100);
        rangeAge.getThumb(0).setValue(pre.getInt(Constants.SETTING_AGE_MIN, 18));
        rangeAge.getThumb(1).setValue(pre.getInt(Constants.SETTING_AGE_MAX, 22));

        txtMinAge.setText(String.valueOf(rangeAge.getThumb(0).getValue()));
        txtMaxAge.setText(String.valueOf(rangeAge.getThumb(1).getValue()));

        seekDistance.setProgress(pre.getInt(Constants.SETTING_MAX_DISTANCE, 10));
        txtDistance.setText(String.valueOf(pre.getInt(Constants.SETTING_MAX_DISTANCE, 10)));
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

    private void settingSwitches() {
        if (pre.getString(Constants.SETTING_SEX_SHOWN, "").matches("male")){
            switchMale.setChecked(true);
            switchFemale.setChecked(false);
        } else if (pre.getString(Constants.SETTING_SEX_SHOWN, "").matches("female")){
            switchFemale.setChecked(true);
            switchMale.setChecked(false);
        }
        else if (pre.getString(Constants.SETTING_SEX_SHOWN, "").matches("both")){
            switchMale.setChecked(true);
            switchFemale.setChecked(true);
        }

        if (pre.getBoolean(Constants.SETTING_NEW_MATCHES, false)){
            switchNewMatches.setChecked(true);
        }

        if (pre.getBoolean(Constants.SETTING_MESSAGES, false)){
            switchMessages.setChecked(true);
        }
    }

    private void map() {
        toolbar =(Toolbar) findViewById(R.id.toolbarTab1Filter);
        seekDistance = (SeekBar)findViewById(R.id.seekDistance);
        txtDistance = (TextView) findViewById(R.id.txtDistance);
        rangeAge = (MultiSlider) findViewById(R.id.rangeAgeTab1);

        txtMinAge = (TextView) findViewById(R.id.txtMinAge);
        txtMaxAge = (TextView) findViewById(R.id.txtMaxAge);

        switchMale = (Switch) findViewById(R.id.switchMale);
        switchFemale = (Switch) findViewById(R.id.switchFemale);
        switchNewMatches = (Switch) findViewById(R.id.switchNewMatches);
        switchMessages = (Switch) findViewById(R.id.switchMessages);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
            saveChages();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveChages() {
        SharedPreferences.Editor edit = pre.edit();
        if (switchMale.isChecked() && switchFemale.isChecked()){
            edit.putString(Constants.SETTING_SEX_SHOWN,"both");
        }else if (switchMale.isChecked()){
            edit.putString(Constants.SETTING_SEX_SHOWN, "male");
        }else if (switchFemale.isChecked()){
            edit.putString(Constants.SETTING_SEX_SHOWN, "female");
        }

        if (switchNewMatches.isChecked()){
            edit.putBoolean(Constants.SETTING_NEW_MATCHES, true);
        }else edit.putBoolean(Constants.SETTING_NEW_MATCHES, false);

        if (switchMessages.isChecked()){
            edit.putBoolean(Constants.SETTING_MESSAGES, true);
        }else edit.putBoolean(Constants.SETTING_MESSAGES, false);

        edit.putInt(Constants.SETTING_MAX_DISTANCE, seekDistance.getProgress());
        edit.putInt(Constants.SETTING_AGE_MIN, rangeAge.getThumb(0).getValue());
        edit.putInt(Constants.SETTING_AGE_MAX, rangeAge.getThumb(1).getValue());
        edit.commit();

        addSetting();
    }

    private void addSetting() {
        FirebaseFirestore db;
        Map<String, Object> user = new HashMap<>();
        user.put(Constants.SETTING_SEX_SHOWN, pre.getString(Constants.SETTING_SEX_SHOWN, ""));
        user.put(Constants.SETTING_MAX_DISTANCE, pre.getInt(Constants.SETTING_MAX_DISTANCE, 10));
        user.put(Constants.SETTING_AGE_MIN, pre.getInt(Constants.SETTING_AGE_MIN, 18));
        user.put(Constants.SETTING_AGE_MAX, pre.getInt(Constants.SETTING_AGE_MAX, 22));
        user.put(Constants.SETTING_NEW_MATCHES, pre.getBoolean(Constants.SETTING_NEW_MATCHES, false));
        user.put(Constants.SETTING_MESSAGES, pre.getBoolean(Constants.SETTING_MESSAGES, false));

        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(pre.getString(Constants.USER_KEY, ""))
                .update(user);
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
