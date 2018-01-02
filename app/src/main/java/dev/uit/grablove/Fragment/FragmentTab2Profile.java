package dev.uit.grablove.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import dev.uit.grablove.R;

public class FragmentTab2Profile extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout toolbarScrolling;
    private TextView tvTime;
    private TextView tvDescription;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab2_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tab2_profile);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        toolbarScrolling = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        tvTime = (TextView) findViewById(R.id.tvage_content_srcolling);
        tvDescription =(TextView) findViewById(R.id.tvdes_content_scrolling);
        progressBar = findViewById(R.id.pbAvatarProfile);
        progressBar.setVisibility(View.VISIBLE);

        receiveBundle();
        /// Truyền Url avatar qua đây đổ lên iv
        //đổ avatar lên background
        //appBarLayout.setBackground(iv.getDrawable());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnLove);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Love <3 ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void receiveBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Clicked");

        toolbarScrolling.setTitle(bundle.getString("Name"));
        String temp = bundle.getString("Avatar");
        ImageView iv =new ImageView(this);
        Glide.with(this).load(temp)
                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    toolbarScrolling.setBackground(resource);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        appBarLayout.setBackground(iv.getDrawable());
        tvTime.setText(String.valueOf(bundle.getInt("Age")));
       //Toast.makeText(this,temp , Toast.LENGTH_SHORT).show();
        tvDescription.setText(bundle.getString("Description"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }
}
