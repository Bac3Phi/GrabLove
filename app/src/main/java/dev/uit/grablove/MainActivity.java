package dev.uit.grablove;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import dev.uit.grablove.Activity.BirthdayActivity;
import dev.uit.grablove.Fragment.Tab1SettingFragment;
import dev.uit.grablove.Fragment.Tab2SwipeFragment;
import dev.uit.grablove.Fragment.Tab3ChatFragment;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;

    private SharedPreferences pre;

    private FirebaseFirestore db;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;

    static MainActivity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);

        //String msg = "New Latitude: " + location.getLatitude()
         //       + "New Longitude: " + location.getLongitude();

        /*Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();*/

        double latitude = 0, longitude = 0;
        latitude = location.getLatitude();
        longitude = location.getLongitude();


        pre = getSharedPreferences(Constants.REF_NAME, MODE_PRIVATE);
        pre.edit().putString(Constants.LAT_LOCATION, String.valueOf(latitude)).commit();
        pre.edit().putString(Constants.LONG_lOCATION, String.valueOf(longitude)).commit();

/*        Toast.makeText(getBaseContext(), "Lat: " + pre.getString(Constants.LAT_LOCATION,"")
                + "\nLong: "+ pre.getString(Constants.LONG_lOCATION,""), Toast.LENGTH_LONG).show();*/

        updateLocation();
        mainActivity = this;

        // Set up the ViewPager with the sections adapter.
        int limit = (mSectionsPagerAdapter.getCount() > 1 ? mSectionsPagerAdapter.getCount() - 1 : 1);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(limit);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_pen_selecotor);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_tele_selector);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_chat_selector);
    }

    private void updateLocation() {
        Map<String, Object> user = new HashMap<>();
        user.put(Constants.LAT_LOCATION, Double.parseDouble(pre.getString(Constants.LAT_LOCATION,"")));
        user.put(Constants.LONG_lOCATION, Double.parseDouble(pre.getString(Constants.LONG_lOCATION,"")));

        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(pre.getString(Constants.USER_KEY, ""))
                .update(user);
    }

    public static MainActivity getInstance(){
        return   mainActivity;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        /*String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();

        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();*/

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Tab1SettingFragment tab1 = new Tab1SettingFragment();
                return tab1;
            case 1:
                Tab2SwipeFragment tab2 = new Tab2SwipeFragment();
                return tab2;
            case 2:
                Tab3ChatFragment tab3 = new Tab3ChatFragment();
                return tab3;
            default: return  null;
        }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return null;
                case 1:
                    return null;
                case 2:
                    return null;
            }
            return null;
        }
    }
}
