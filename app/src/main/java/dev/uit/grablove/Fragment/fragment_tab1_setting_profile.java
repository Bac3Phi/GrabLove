package dev.uit.grablove.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import at.markushi.ui.CircleButton;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.uit.grablove.Activity.BirthdayActivity;
import dev.uit.grablove.Activity.SexActivity;
import dev.uit.grablove.Constants;
import dev.uit.grablove.MainActivity;
import dev.uit.grablove.R;

public class fragment_tab1_setting_profile extends AppCompatActivity {
    private Toolbar toolbar;
    private  static  final  int PICK_IMAGE = 1;
    private CircleImageView imgAvatar;
    private Calendar calendarDob;
    private MaterialEditText edtDob,edtName,edtdDes;
    private int year;
    private Uri imgUri;
    private RadioGroup rgpSex;
    private RadioButton rbMale, rbFemale;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseFirestore db;

    private SharedPreferences pre;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab1_setting_profile);


        toolbar = (Toolbar) findViewById(R.id.toolbarTab1Profile);
        CircleButton btnAdd = (CircleButton)findViewById(R.id.btnAddSettingProfile);
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatarSettingProfile) ;
        edtDob = (MaterialEditText) findViewById(R.id.edtBirthdayTab1Profile);
        edtName = (MaterialEditText)findViewById(R.id.edtNameTab1Profile);
        edtdDes = (MaterialEditText) findViewById(R.id.edtDesTab1Profile);
        rgpSex = findViewById(R.id.radioGroup_character);
        rbMale = findViewById(R.id.radioButton_male);
        rbFemale = findViewById(R.id.radioButton_female);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        calendarDob = Calendar.getInstance();

        year= calendarDob.get(Calendar.YEAR);

        pre = getSharedPreferences(Constants.REF_NAME, MODE_PRIVATE);

        Glide.with(this).load(pre.getString(Constants.USER_AVATAR, ""))
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.loading))
                                .into(imgAvatar);

        edtdDes.setText(pre.getString(Constants.USER_DESCRIPTION, "Hãy mô tả về bản thân của bạn"));
        edtName.setText(pre.getString(Constants.USER_NAME, ""));
        edtDob.setText("" + (year - pre.getInt(Constants.USER_AGE, 0)));

        if (pre.getString(Constants.USER_SEX, "").matches("male")){
            rbMale.setChecked(true);
        }
        else rbFemale.setChecked(true);

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

    private void saveChages() {
        SharedPreferences.Editor edit = pre.edit();

        edit.putString(Constants.USER_DESCRIPTION, edtdDes.getText().toString());
        edit.putString(Constants.USER_NAME, edtName.getText().toString());
        edit.putInt(Constants.USER_AGE, year - Integer.parseInt(edtDob.getText().toString()));

        if (rgpSex.getCheckedRadioButtonId() == R.id.radioButton_male){
            edit.putString(Constants.USER_SEX, "male");
        }
        else edit.putString(Constants.USER_SEX, "female");

        edit.commit();

        addSetting();
    }

    private void addSetting() {
        Map<String, Object> user = new HashMap<>();
        user.put(Constants.DB_USER_DESCRIPTION, pre.getString(Constants.USER_DESCRIPTION, ""));
        user.put(Constants.DB_USER_FULL_NAME, pre.getString(Constants.USER_NAME, ""));
        user.put(Constants.USER_AGE, pre.getInt(Constants.USER_AGE, 0));
        user.put(Constants.DB_USER_SEX, pre.getString(Constants.USER_SEX, ""));

        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(pre.getString(Constants.USER_KEY, ""))
                .update(user);
    }

    private void inprogress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Finishing...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void uploadImage() {
        if (imgUri != null){
            final SharedPreferences.Editor edit = pre.edit();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
            saveChages();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imgUri = data.getData();
            Glide.with(this).load(imgUri)
                    .apply(new RequestOptions()
                            .dontAnimate()
                            .dontTransform())
                    .into(imgAvatar);

            inprogress();
            uploadImage();
        }
    }
}
