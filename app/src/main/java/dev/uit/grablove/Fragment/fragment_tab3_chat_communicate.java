    package dev.uit.grablove.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.uit.grablove.Constants;
import dev.uit.grablove.Model.ChatListAdapter;
import dev.uit.grablove.Model.Message;
import dev.uit.grablove.Model.UserType;
import dev.uit.grablove.R;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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
            toolbar = (Toolbar) findViewById(R.id.toolbarTab3);
            chatMessage = new ArrayList<>();
            listMessage = (ListView) findViewById(R.id.listviewMessTab3);
            btnSendText = (ImageButton) findViewById(R.id.btnSendText);
            edtMessage = (EditText) findViewById(R.id.edtMsg);
            btnSendImg = (ImageButton) findViewById(R.id.btnSendImg);
            avatar = (CircleImageView) findViewById(R.id.ivAvatarTab3Chat);
            username = (TextView) findViewById(R.id.tvNameUserTab3Chat);

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

        @Override
        protected void onStart() {
            super.onStart();
            //getFromDB();
        }
    }

