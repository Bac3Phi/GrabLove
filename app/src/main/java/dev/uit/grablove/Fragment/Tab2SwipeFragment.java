package dev.uit.grablove.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import dev.uit.grablove.R;

/**
 * Created by Administrator on 10/14/2017.
 */

public class Tab2SwipeFragment extends Fragment {
    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2_swipe, container, false);
        al = new ArrayList<>();
        al.add("cax");
        al.add("lolz");
        al.add("chim");
        al.add("kooo");
        al.add("du;");
        al.add("bum'");
        al.add("gg");
        al.add("wp");

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.helloText, al );
        SwipeFlingAdapterView flingContainer= (SwipeFlingAdapterView) rootView.findViewById(R.id.frame);


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(Tab2SwipeFragment.this.getContext(), "left", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(Tab2SwipeFragment.this.getContext(), "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(Tab2SwipeFragment.this.getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });




        return rootView;
    }



}