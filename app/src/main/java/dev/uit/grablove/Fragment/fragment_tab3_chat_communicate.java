    package dev.uit.grablove.Fragment;

    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.app.ProgressDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.content.pm.PackageManager;
    import android.net.Uri;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Environment;
    import android.provider.MediaStore;
    import android.support.annotation.NonNull;
    import android.support.annotation.RequiresApi;
    import android.support.v4.app.ActivityCompat;
    import android.support.v4.content.ContextCompat;
    import android.support.v4.content.FileProvider;
    import android.support.v7.app.AlertDialog;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.EventListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreException;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;

    import java.io.File;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.UUID;

    import de.hdodenhof.circleimageview.CircleImageView;
    import dev.uit.grablove.Activity.BirthdayActivity;
    import dev.uit.grablove.Activity.SexActivity;
    import dev.uit.grablove.Constants;
    import dev.uit.grablove.MainActivity;
    import dev.uit.grablove.Model.ChatListAdapter;
    import dev.uit.grablove.Model.Message;
    import dev.uit.grablove.Model.UserType;
    import dev.uit.grablove.R;

    public class fragment_tab3_chat_communicate extends AppCompatActivity {
        private Toolbar toolbar;
        private ListView listMessage;
        private ImageButton btnSendText;
        private EditText edtMessage;
        private ChatListAdapter listAdapter;
        private ArrayList<Message> chatMessage;
        private ImageButton btnSendImg;

        private String FriendId;

        private CircleImageView avatar;
        private TextView username;

        private FirebaseFirestore db;
        private SharedPreferences pre;
        private FirebaseStorage storage;
        private StorageReference storageRef;

        private ProgressDialog mProgressDialog;

        private Uri mImageUri = Uri.EMPTY;

        private static final int GALLERY_INTENT = 2;
        private String pictureImagePath = "";
        private String[] permissions= new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,};

        final CharSequence[] options = {"Camera", "Gallery"};

        public static final int READ_EXTERNAL_STORAGE = 0,MULTIPLE_PERMISSIONS = 10;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_tab3_chat_communicate);

            //Ánh xạ
            map();

            FriendId = getIntent().getExtras().getString("id");

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            setFriendInfo();

            getFromDB();

            btnSendText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMessage(edtMessage.getText().toString(), UserType.SELF);
                    edtMessage.getText().clear();
                }
            });

            btnSendImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment_tab3_chat_communicate.this);
                    builder.setTitle("Choose Source ");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Camera")) {
                                if (checkPermissions()) {
                                    callCamera();
                                }
                            }
                            if(options[item].equals("Gallery"))
                            {
                                if (ContextCompat.checkSelfPermission(fragment_tab3_chat_communicate.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                                {
                                    ActivityCompat.requestPermissions(fragment_tab3_chat_communicate.this,
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                                }
                                else
                                {
                                    callgalary();
                                }
                            }
                        }
                    });
                    builder.show();
                }
            });
        }

        private void callCamera() {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = timeStamp + ".jpg";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            Log.d("LOGGED", "imageFileName :  "+ imageFileName);
            pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;

            Uri outputFileUri;

            File file = new File(pictureImagePath);

            if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT))
                outputFileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", file);
            else
                outputFileUri = Uri.fromFile(file);


            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


            Log.d("LOGGED", "pictureImagePath :  "+ pictureImagePath);
            Log.d("LOGGED", "outputFileUri :  "+ outputFileUri);

            startActivityForResult(cameraIntent, 5);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

                mImageUri = data.getData();
                Log.d("LOGGED", "ImageURI : " +mImageUri);


                mProgressDialog.setMessage("Uploading...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                if (mImageUri != null){
                    StorageReference ref = storageRef.child("imageConversation/" + UUID.randomUUID().toString());
                    ref.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                                    sendImageToDB(downloadUri.toString());
                                    mProgressDialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgressDialog.dismiss();
                                    //Toast.makeText(getBaseContext(), "")
                                }
                            });
                }
            }

            else if (requestCode == 5 && resultCode == RESULT_OK ) {



                File imgFile = new  File(pictureImagePath);
                if(imgFile.exists()) {
                    Log.d("LOGGED", "imgFile : " + imgFile);

                    Uri fileUri =Uri.fromFile(imgFile);
                    Log.d("LOGGED", "fileUri : " + fileUri);

                    mProgressDialog.setMessage("Uploading...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();

                    StorageReference ref = storageRef.child("imageConversation/" + UUID.randomUUID().toString());
                    ref.putFile(fileUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                                    sendImageToDB(downloadUri.toString());
                                    mProgressDialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgressDialog.dismiss();
                                    //Toast.makeText(getBaseContext(), "")
                                }
                            });
                }
            }

            else if (requestCode == 5)
            {
                Toast.makeText(this, "resultCode : "+ resultCode, Toast.LENGTH_SHORT).show();
            }
        }

        private void callgalary() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        }

        private  boolean checkPermissions() {
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p:permissions) {
                result = ContextCompat.checkSelfPermission(getApplicationContext(),p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
                return false;
            }
            return true;
        }

        private void getFromDB() {
            db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends")
                    .whereEqualTo(Constants.DB_FRIEND_KEY, FriendId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (!task.getResult().isEmpty()){
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends/"
                                            + document.getId() + "/chat")
                                            .orderBy(Constants.CHAT_TIME)
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                    chatMessage.clear();
                                                    if (!documentSnapshots.isEmpty()) {
                                                        for (DocumentSnapshot document : documentSnapshots) {
                                                            Message chatmsg = new Message();
                                                            chatmsg.setMsg(document.getString(Constants.CHAT_MESS));
                                                            if (document.getString(Constants.CHAT_FROM).matches(pre.getString(Constants.USER_KEY, ""))){
                                                                chatmsg.setUserType(UserType.SELF);
                                                            }else {
                                                                chatmsg.setUserType(UserType.OTHER);
                                                            }
                                                            chatmsg.setTime(document.getLong(Constants.CHAT_TIME));
                                                            chatMessage.add(chatmsg); // add vao` list

                                                            if (listAdapter != null)
                                                                listAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    });
        }

        private void setFriendInfo() {
            db.document("Users/" + FriendId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            Glide.with(getBaseContext()).load(doc.getString(Constants.DB_USER_AVATAR)).into(avatar);
                            username.setText(doc.getString(Constants.DB_USER_FULL_NAME));
                        }
                    });
        }

        private void map() {
            storage = FirebaseStorage.getInstance();
            storageRef = storage.getReference();

            toolbar = (Toolbar) findViewById(R.id.toolbarTab3);
            chatMessage = new ArrayList<>();
            listMessage = (ListView) findViewById(R.id.listviewMessTab3);
            btnSendText = (ImageButton) findViewById(R.id.btnSendText);
            edtMessage = (EditText) findViewById(R.id.edtMsg);
            btnSendImg = (ImageButton) findViewById(R.id.btnSendImg);
            avatar = (CircleImageView) findViewById(R.id.ivAvatarTab3Chat);
            username = (TextView) findViewById(R.id.tvNameUserTab3Chat);
            mProgressDialog = new ProgressDialog(this);

            listAdapter = new ChatListAdapter(chatMessage, this);
            listMessage.setAdapter(listAdapter);

            db = FirebaseFirestore.getInstance();
            pre = getSharedPreferences(Constants.REF_NAME, MODE_PRIVATE);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home)
                this.finish();
            return super.onOptionsItemSelected(item);
        }

        private void sendMessage(final String msg, UserType userType) {

            if (msg.trim().length() == 0)
                return;
            /*Message chatmsg = new Message();
            chatmsg.setMsg(msg);
            chatmsg.setUserType(userType);
            chatmsg.setTime(new Date().getTime());
            chatMessage.add(chatmsg); // add vao` list

            if (listAdapter != null)
                listAdapter.notifyDataSetChanged();*/
            sendToDB(msg);
        }

        private void sendToDB(final String msg) {
            final Map<String, Object> data = new HashMap<>();
            data.put(Constants.CHAT_MESS, msg);
            data.put(Constants.CHAT_FROM, pre.getString(Constants.USER_KEY, ""));
            data.put(Constants.CHAT_TIME, new Date().getTime());

            db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends")
                    .whereEqualTo(Constants.DB_FRIEND_KEY, FriendId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (!task.getResult().isEmpty()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends/"
                                            + document.getId() + "/chat")
                                            .add(data);
                                }
                            }
                        }
                    });

            db.collection("Users/" + FriendId + "/friends")
                    .whereEqualTo(Constants.DB_FRIEND_KEY, pre.getString(Constants.USER_KEY, ""))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (!task.getResult().isEmpty()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("Users/" + FriendId + "/friends/"
                                            + document.getId() + "/chat")
                                            .add(data);
                                }
                            }
                        }
                    });
        }

        private void sendImageToDB(final String img) {
            final Map<String, Object> data = new HashMap<>();
            data.put(Constants.CHAT_MESS, img);
            data.put(Constants.CHAT_FROM, pre.getString(Constants.USER_KEY, ""));
            data.put(Constants.CHAT_TIME, new Date().getTime());

            db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends")
                    .whereEqualTo(Constants.DB_FRIEND_KEY, FriendId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (!task.getResult().isEmpty()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends/"
                                            + document.getId() + "/chat")
                                            .add(data);
                                }
                            }
                        }
                    });

            db.collection("Users/" + FriendId + "/friends")
                    .whereEqualTo(Constants.DB_FRIEND_KEY, pre.getString(Constants.USER_KEY, ""))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (!task.getResult().isEmpty()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    db.collection("Users/" + FriendId + "/friends/"
                                            + document.getId() + "/chat")
                                            .add(data);
                                }
                            }
                        }
                    });
        }

        @Override
        protected void onStart() {
            super.onStart();
            //getFromDB();
        }
    }

