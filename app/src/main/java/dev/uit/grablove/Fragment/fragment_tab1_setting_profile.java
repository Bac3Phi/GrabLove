package dev.uit.grablove.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import at.markushi.ui.CircleButton;
import dev.uit.grablove.R;

public class fragment_tab1_setting_profile extends AppCompatActivity {
    Toolbar toolbar;
    private  static  final  int PICK_IMAGE = 1;
    ImageView imgAvatar;
    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab1_setting_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbarTab1Profile);
        CircleButton btnAdd = (CircleButton)findViewById(R.id.btnAddSettingProfile);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatarSettingProfile) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imgUri = data.getData();
            imgAvatar.setImageURI(imgUri);
        }
    }
}
