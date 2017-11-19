    package dev.uit.grablove.Fragment;

import android.annotation.SuppressLint;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dev.uit.grablove.Model.ChatListAdapter;
import dev.uit.grablove.Model.Message;
import dev.uit.grablove.Model.UserType;
import dev.uit.grablove.R;
import android.support.v7.widget.Toolbar;

public class fragment_tab3_chat_communicate extends AppCompatActivity {
    Toolbar toolbar;
    ListView listMessage ;
    ImageButton btnSendText;
    ImageButton btnSendText1;
    EditText edtMessage;
    EditText edtMessage1;
    ImageButton btnSendImg;
    ImageButton btnSendImg1;
    ChatListAdapter listAdapter;
    ArrayList<Message> chatMessage;

    ImageView avatar;
    TextView username;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab3_chat_communicate);

        toolbar=(Toolbar) findViewById(R.id.toolbarTab3);
        chatMessage = new ArrayList<>();
        listMessage =(ListView) findViewById(R.id.listviewMessTab3);
        btnSendText =(ImageButton) findViewById(R.id.btnSendText);
        btnSendText1 =(ImageButton) findViewById(R.id.btnSendText1);
        edtMessage =(EditText) findViewById(R.id.edtMsg);
        edtMessage1 =(EditText) findViewById(R.id.edtMsg1);
        btnSendImg =(ImageButton) findViewById(R.id.btnSendImg);
        btnSendImg1 =(ImageButton) findViewById(R.id.btnSendImg1);
        avatar = (ImageView)findViewById(R.id.ivAvatarTab3Chat);
        username= (TextView)findViewById(R.id.tvNameUserTab3Chat);

        listAdapter= new ChatListAdapter(chatMessage,this);
        listMessage.setAdapter(listAdapter);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = new BitmapDrawable(getResources(), createCircleBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.avatar_test)));

        avatar.setImageDrawable(drawable);
        username.setText("Hello WORLD");


        btnSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(edtMessage.getText().toString(), UserType.SELF);
                edtMessage.getText().clear();
            }
        });

        btnSendText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(edtMessage1.getText().toString(), UserType.OTHER);
                edtMessage1.getText().clear();
            }
        });
    }
    public Bitmap createCircleBitmap(Bitmap bitmapimg){
        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
                bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
                bitmapimg.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmapimg.getWidth() / 2,
                bitmapimg.getHeight() / 2, bitmapimg.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    } // Hàm bo tròn bitmap

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void sendMessage(final String msg, UserType userType)
    {

        if(msg.trim().length() == 0)
            return;
        Message chatmsg = new Message();
        chatmsg.setMsg(msg);
        chatmsg.setUserType(userType);
        chatMessage.add(chatmsg); // add vao` list

        if (listAdapter!=null)
        listAdapter.notifyDataSetChanged();
/*
        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

        exec.schedule(new Runnable() {
            @Override
            public void run() {

                final Message message = new Message();
                message.setMsg(msg);
                message.setUserType(UserType.SELF);
                chatMessage.add(message);

                fragment_tab3_chat_communicate.this.runOnUiThread(new Runnable() {
                    public void run() {
                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, 1, TimeUnit.SECONDS);

*/
   }
}
