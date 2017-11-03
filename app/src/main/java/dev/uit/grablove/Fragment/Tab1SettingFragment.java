package dev.uit.grablove.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.markushi.ui.CircleButton;
import dev.uit.grablove.R;

/**
 * Created by Administrator on 10/14/2017.
 */

public class Tab1SettingFragment extends Fragment {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_tab1_setting, container, false);
  //              RelativeLayout layout =(RelativeLayout) rootView.findViewById(R.id.relativeLayoutTab1);
//                layout.setBackgroundResource(R.drawable.bg_2);

                CircleButton btnSettingFilter = (CircleButton) rootView.findViewById(R.id.btnFilterSettingTab1);
                btnSettingFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent test = new Intent(Tab1SettingFragment.this.getActivity(),fragment_tab1_setting_filter.class);
                        startActivity(test);
                    }
                });
                CircleButton btnSettingProfile = (CircleButton) rootView.findViewById(R.id.btnProfileSettingTab1);
                btnSettingProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent test = new Intent(Tab1SettingFragment.this.getActivity(),fragment_tab1_setting_profile.class);
                        startActivity(test);
                    }
                });
                return rootView;

    }
}
