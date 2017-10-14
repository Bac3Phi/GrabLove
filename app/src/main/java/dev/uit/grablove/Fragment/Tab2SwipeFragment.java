package dev.uit.grablove.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.uit.grablove.R;

/**
 * Created by Administrator on 10/14/2017.
 */

public class Tab2SwipeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2_swipe, container, false);
        return rootView;
    }
}