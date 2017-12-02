package dev.uit.grablove.Fragment;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import at.markushi.ui.CircleButton;
import dev.uit.grablove.Constants;
import dev.uit.grablove.Model.TouristSpot;
import dev.uit.grablove.Model.TouristSpotCardAdapter;
import dev.uit.grablove.Model.User;
import dev.uit.grablove.R;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by Phi Poz on 10/14/2017.
 */

public class Tab2SwipeFragment extends Fragment  {
    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;

    private User user;

    private FirebaseFirestore db;
    private SharedPreferences pre;

    private List<User> userList;

    private View rootView;

    private CircleButton btnRefresh, btnLike, btnDislike, btnSuperLike;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab2_swipe, container, false);

        pre = getActivity().getSharedPreferences(Constants.REF_NAME, MODE_PRIVATE);
        userList = new LinkedList<>();

        actionButton(rootView);
        getListFromDB();
        return rootView;
    }

    private void getListFromDB() {
        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo(Constants.DB_USER_SEX, pre.getString(Constants.SETTING_SEX_SHOWN,""))
/*                .orderBy(Constants.DB_USER_AGE)
                .startAt(pre.getInt(Constants.SETTING_AGE_MIN,18))
                .endAt(pre.getInt(Constants.SETTING_AGE_MAX, 22))*/
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                user = document.toObject(User.class);
                                user.setUserKey(document.getId());
                                //checkUnliked(document);
                                userList.add(user);
                            }
                            checkDistance();
                            adapter = createTouristSpotCardAdapter(userList);
                            setup();
                            reload();
                        } else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    /*private void checkUnliked(DocumentSnapshot document) {
        db = FirebaseFirestore.getInstance();
        db.collection("users/" + document.getId() +"/unliked")
                .whereEqualTo(Constants.DB_USER_UNLIKED_ID, pre.getString(Constants.USER_KEY, ""))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().isEmpty()) {
                            userList.add(user);
                        }
                    }
                });
    }*/

    private void checkDistance() {
        for (int  i = 0; i < userList.size(); i++)
        {
            User user = userList.get(i);
            Location startPoint=new Location("locationA");
            startPoint.setLatitude(Double.parseDouble(pre.getString(Constants.LAT_LOCATION,"")));
            startPoint.setLongitude(Double.parseDouble(pre.getString(Constants.LONG_lOCATION,"")));

            Location endPoint=new Location("locationA");
            endPoint.setLatitude(user.getLat_location());
            endPoint.setLongitude(user.getLong_location());

            int distance=(int) startPoint.distanceTo(endPoint)/1000;

            if (distance > pre.getInt(Constants.SETTING_MAX_DISTANCE,0)
                    && (user.getAge() < pre.getInt(Constants.SETTING_AGE_MIN,0)
                    || user.getAge() > pre.getInt(Constants.SETTING_AGE_MAX,0))){
                userList.remove(user);
                i--;
            }
            else {
                user.setDistance(distance);
            }
        }
    }

    private void actionButton(View rootView) {
        btnRefresh = (CircleButton) rootView.findViewById(R.id.btnRefreshTab2);
        btnDislike= (CircleButton) rootView.findViewById(R.id.btnDislikeTab2);
        btnLike = (CircleButton) rootView.findViewById(R.id.btnLikeTab2);
        btnSuperLike = (CircleButton) rootView.findViewById(R.id.btnSuperLikeTab2);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                swipeLeft();
            }
        });
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               swipeRight();
            }
        });
    }
/*
    private TouristSpot createTouristSpot() {
        return new TouristSpot("Yasaka Shrine", "22", "10","https://i.imgur.com/qUGY7gL.jpg");
    }
    private List<TouristSpot> createTouristSpots() {
        List<TouristSpot> spots = new ArrayList<>();
        spots.add(new TouristSpot("Yasaka Shrine", "21", "10","https://i.imgur.com/GfXdZjo.jpg"));
        spots.add(new TouristSpot("Fushimi Inari Shrine", "22", "10","https://i.imgur.com/v1mEgOd.jpg"));
        spots.add(new TouristSpot("Bamboo Forest", "20", "10","https://i.imgur.com/dUU2UO6.jpg"));
        spots.add(new TouristSpot("Brooklyn Bridge", "25", "10","https://i.imgur.com/2Fa14rb.jpg"));
        spots.add(new TouristSpot("Sevenbaby", "25", "10","https://i.imgur.com/mBmnbo2.jpg"));
        spots.add(new TouristSpot("Xeimei", "21", "10","https://i.imgur.com/0L6SQ0D.jpg"));
        return spots;
    }*/
    private TouristSpotCardAdapter createTouristSpotCardAdapter(List<User> users) {
        final TouristSpotCardAdapter adapter = new TouristSpotCardAdapter(getContext());
        adapter.addAll(users);
        return adapter;
    }
    private void setup() {
        progressBar = (ProgressBar) rootView.findViewById(R.id.activity_main_progress_bar);

        cardStackView = (CardStackView) rootView.findViewById(R.id.activity_main_card_stack_view);
        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
                if (direction == direction.Left){
             /*       Map<String, Object> user = new HashMap<>();
                    user.put(Constants.DB_USER_UNLIKED_ID,pre.getString(Constants.USER_KEY, ""));

                    db = FirebaseFirestore.getInstance();
                    db.collection("users/" + adapter.getItem(cardStackView.getTopIndex()-1).getUserKey() +"/unliked")
                            .add(user);*/
                }
                if (direction == direction.Right){
                    swipeRightToDB();
                }
            }

            @Override
            public void onCardReversed() {
                Log.d("CardStackView", "onCardReversed");
            }

            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
            }
        });
    }

    private void swipeRightToDB() {
        Map<String, Object> user = new HashMap<>();
        user.put(Constants.DB_USER_LIKED_ID,pre.getString(Constants.USER_KEY, ""));

        db = FirebaseFirestore.getInstance();
        db.collection("Users/" + adapter.getItem(cardStackView.getTopIndex()-1).getUserKey() +"/liked")
                .add(user);
        checkLikedToMatch();
      /*  Toast.makeText(getContext(), "Left - " + adapter.getItem(cardStackView.getTopIndex()-1).getUserKey()
                + "\nindex: " + (cardStackView.getTopIndex()-1), Toast.LENGTH_LONG).show();*/
    }

    private void checkLikedToMatch() {
        db = FirebaseFirestore.getInstance();
        db.collection("Users/" + pre.getString(Constants.USER_KEY,"") +"/liked")
                .whereEqualTo(Constants.DB_USER_LIKED_ID, adapter.getItem(cardStackView.getTopIndex()-1).getUserKey())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()) {
                            Map<String, Object> data = new HashMap<>();
                            data.put(Constants.DB_FRIEND_KEY, adapter.getItem(cardStackView.getTopIndex()-1).getUserKey());

                            db.collection("Users/" + pre.getString(Constants.USER_KEY,"") +"/friends")
                                    .add(data);

                            data = new HashMap<>();
                            data.put(Constants.DB_FRIEND_KEY, pre.getString(Constants.USER_KEY,""));
                            db.collection("Users/" + adapter.getItem(cardStackView.getTopIndex()-1).getUserKey() +"/friends")
                                    .add(data);
                        }
                    }
                });
    }

    private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private LinkedList<User> extractRemainingTouristSpots() {
        LinkedList<User> spots = new LinkedList<>();
        for (int i = cardStackView.getTopIndex(); i < adapter.getCount(); i++) {
            spots.add(adapter.getItem(i));
        }
        return spots;
    }

    /*private void addFirst() {
        LinkedList<User> spots = extractRemainingTouristSpots();
        spots.addFirst(createTouristSpot());
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }

    private void addLast() {
        LinkedList<TouristSpot> spots = extractRemainingTouristSpots();
        spots.addLast(createTouristSpot());
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }

    private void removeFirst() {
        LinkedList<TouristSpot> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }

        spots.removeFirst();
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }

    private void removeLast() {
        LinkedList<TouristSpot> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }

        spots.removeLast();
        adapter.clear();
        adapter.addAll(spots);
        adapter.notifyDataSetChanged();
    }*/

    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.addAll(userList);
        adapter.notifyDataSetChanged();
    }

    public void swipeLeft() {
        List<User> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }

        View target = cardStackView.getTopView();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 200f));
        translateX.setStartDelay(300);
        translateY.setStartDelay(300);
        translateX.setDuration(200);
        translateY.setDuration(200);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotation, translateX, translateY);

        cardStackView.swipe(SwipeDirection.Left, set);
    }

    public void swipeRight() {
        List<User> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }

        View target = cardStackView.getTopView();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 200f));
        translateX.setStartDelay(300);
        translateY.setStartDelay(300);
        translateX.setDuration(200);
        translateY.setDuration(200);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotation, translateX, translateY);

        cardStackView.swipe(SwipeDirection.Right, set);
    }

    private void reverse() {
        cardStackView.reverse();
    }

}