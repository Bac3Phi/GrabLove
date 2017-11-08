package dev.uit.grablove.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import at.markushi.ui.CircleButton;
import dev.uit.grablove.R;

public class AvatarActivity extends AppCompatActivity {
    private  static  final  int PICK_IMAGE = 1;
    Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        CircleButton btnAdd = (CircleButton)findViewById(R.id.btnAddAvatar);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            Bitmap imgAvatar;
            imgUri = data.getData();
            try
            {
               imgAvatar = MediaStore.Images.Media.getBitmap(this.getContentResolver() , imgUri); // returN bitmap
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
