package dev.uit.grablove.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import at.markushi.ui.CircleButton;
import dev.uit.grablove.Model.TouristSpot;
import dev.uit.grablove.Model.TouristSpotCardAdapter;
import dev.uit.grablove.R;

/**
 * Created by Phi Poz on 10/14/2017.
 */

public class Tab2SwipeFragment extends Fragment {
    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;

    private CircleButton btnRefresh,btnLike,btnDislike,btnSuperLike;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2_swipe, container, false);
        actionButton(rootView);
        setup(rootView);
        reload();
        return rootView;
    }

    private void actionButton(View rootView) {
        btnRefresh = (CircleButton) rootView.findViewById(R.id.btnRefreshTab2);
        btnDislike= (CircleButton) rootView.findViewById(R.id.btnDislikeTab2);
        btnLike = (CircleButton) rootView.findViewById(R.id.btnLikeTab2);
        btnSuperLike = (CircleButton) rootView.findViewById(R.id.btnSuperLikeTab2);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload();
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

    private TouristSpot createTouristSpot() {
        return new TouristSpot("Yasaka Shrine", "22", "https://i.imgur.com/qUGY7gL.jpg");
    }
    private List<TouristSpot> createTouristSpots() {
        List<TouristSpot> spots = new ArrayList<>();
        spots.add(new TouristSpot("Yasaka Shrine", "21", "https://i.imgur.com/GfXdZjo.jpg"));
        spots.add(new TouristSpot("Fushimi Inari Shrine", "22", "https://i.imgur.com/v1mEgOd.jpg"));
        spots.add(new TouristSpot("Bamboo Forest", "20", "https://i.imgur.com/dUU2UO6.jpg"));
        spots.add(new TouristSpot("Brooklyn Bridge", "25", "https://i.imgur.com/2Fa14rb.jpg"));
        spots.add(new TouristSpot("Sevenbaby", "25", "https://i.imgur.com/mBmnbo2.jpg"));
        spots.add(new TouristSpot("Xeimei", "21", "https://i.imgur.com/0L6SQ0D.jpg"));
        return spots;
    }
    private TouristSpotCardAdapter createTouristSpotCardAdapter() {
        final TouristSpotCardAdapter adapter = new TouristSpotCardAdapter(getContext());
        adapter.addAll(createTouristSpots());
        return adapter;
    }
    private void setup(View rootView) {
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

    private void reload() {
        cardStackView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createTouristSpotCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private LinkedList<TouristSpot> extractRemainingTouristSpots() {
        LinkedList<TouristSpot> spots = new LinkedList<>();
        for (int i = cardStackView.getTopIndex(); i < adapter.getCount(); i++) {
            spots.add(adapter.getItem(i));
        }
        return spots;
    }

    private void addFirst() {
        LinkedList<TouristSpot> spots = extractRemainingTouristSpots();
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
    }

    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.addAll(createTouristSpots());
        adapter.notifyDataSetChanged();
    }

    public void swipeLeft() {
        List<TouristSpot> spots = extractRemainingTouristSpots();
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
        List<TouristSpot> spots = extractRemainingTouristSpots();
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