package dev.uit.grablove.Fragment;

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
import android.widget.TextView;

import dev.uit.grablove.R;

public class FragmentTab2Profile extends AppCompatActivity {
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout toolbarScrolling;
    TextView tvLocation;
    TextView tvTime;
    TextView tvDescription;
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

        tvLocation = (TextView) findViewById(R.id.tvloction_content_srcolling);
        tvTime = (TextView) findViewById(R.id.tvloction_content_srcolling);
        tvDescription =(TextView) findViewById(R.id.tvdes_content_scrolling);
        ImageView iv =new ImageView(this);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }
}
