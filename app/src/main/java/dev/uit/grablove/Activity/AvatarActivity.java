package dev.uit.grablove.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import at.markushi.ui.CircleButton;
import dev.uit.grablove.Constants;
import dev.uit.grablove.MainActivity;
import dev.uit.grablove.R;
import info.hoang8f.widget.FButton;

public class AvatarActivity extends AppCompatActivity {
    private  static  final  int PICK_IMAGE = 1;
    private Uri imgUri;
    private ImageView ivAvatar;
    private Bitmap imgAvatar;

    private String strSex;
    private int iAge;

    private FButton btnFinish;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseFirestore db;

    private SharedPreferences pre;
    private SharedPreferences.Editor edit;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        CircleButton btnAdd = (CircleButton)findViewById(R.id.btnAddAvatar);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        btnFinish = (FButton) findViewById(R.id.btnFinishAvatar);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        pre=getSharedPreferences (Constants.REF_NAME,MODE_PRIVATE);
        edit= pre.edit();

        strSex = getIntent().getStringExtra("sex");
        iAge = getIntent().getIntExtra("age", 0);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inprogress();
                uploadInfo();
                uploadImage();
                saveUser();
            }
        });
    }

    private void inprogress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Finishing...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void saveUser() {
        edit.putString(Constants.USER_SEX, getIntent().getStringExtra("sex"));
        edit.putInt(Constants.USER_AGE, getIntent().getIntExtra("age", 0));
        edit.putBoolean(Constants.IS_LOG_IN, true);
        if (strSex.matches("male")){
            edit.putString(Constants.SETTING_SEX_SHOWN, "female");
        }
        else edit.putString(Constants.SETTING_SEX_SHOWN, "male");
        edit.putInt(Constants.SETTING_MAX_DISTANCE, 10);
        edit.putInt(Constants.SETTING_AGE_MIN, 18);
        edit.putInt(Constants.SETTING_AGE_MAX, 22);
        edit.putBoolean(Constants.SETTING_NEW_MATCHES, true);
        edit.putBoolean(Constants.SETTING_MESSAGES, true);
        edit.commit();
    }

    private void uploadInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.DB_USER_SEX,strSex);
        data.put(Constants.DB_USER_AGE, iAge);
        data.put(Constants.DB_USER_IS_NEW, false);

        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(pre.getString(Constants.USER_KEY, ""))
                .update(data);
        addSetting();
    }

    private void addSetting() {
        Map<String, Object> user = new HashMap<>();
        if (strSex.matches("male")){
            user.put(Constants.SETTING_SEX_SHOWN, "female");
        }
        else user.put(Constants.SETTING_SEX_SHOWN, "male");

        user.put(Constants.SETTING_MAX_DISTANCE, 10);
        user.put(Constants.SETTING_AGE_MIN, 18);
        user.put(Constants.SETTING_AGE_MAX, 22);
        user.put(Constants.SETTING_NEW_MATCHES, true);
        user.put(Constants.SETTING_MESSAGES, true);

        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(pre.getString(Constants.USER_KEY, ""))
                .update(user);
    }

    private void uploadImage() {
        if (imgUri != null){
            StorageReference ref = storageRef.child("avatar/" + UUID.randomUUID().toString());
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            db = FirebaseFirestore.getInstance();
                            db.collection("Users")
                                    .document(pre.getString(Constants.USER_KEY, ""))
                                    .update(Constants.DB_USER_AVATAR, taskSnapshot.getDownloadUrl().toString());
                            edit.putString(Constants.USER_AVATAR, taskSnapshot.getDownloadUrl().toString());
                            edit.commit();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            SexActivity.getInstance().finish();
                            BirthdayActivity.getInstance().finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            //Toast.makeText(getBaseContext(), "")
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imgUri = data.getData();
            try
            {
                imgAvatar = MediaStore.Images.Media.getBitmap(this.getContentResolver() , imgUri); // returN bitmap
                ivAvatar.setImageBitmap(imgAvatar);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
