    package dev.uit.grablove.Fragment;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

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

public class fragment_tab3_chat_communicate extends AppCompatActivity {

    ListView listMessage ;
    ImageButton btnSendText;
    ImageButton btnSendText1;
    EditText edtMessage;
    EditText edtMessage1;
    ImageButton btnSendImg;
    ImageButton btnSendImg1;
    ChatListAdapter listAdapter;
    ArrayList<Message> chatMessage;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab3_chat_communicate);

        chatMessage = new ArrayList<>();
        listMessage =(ListView) findViewById(R.id.listviewMessTab3);
        btnSendText =(ImageButton) findViewById(R.id.btnSendText);
        btnSendText1 =(ImageButton) findViewById(R.id.btnSendText1);
        edtMessage =(EditText) findViewById(R.id.edtMsg);
        edtMessage1 =(EditText) findViewById(R.id.edtMsg1);
        btnSendImg =(ImageButton) findViewById(R.id.btnSendImg);
        btnSendImg1 =(ImageButton) findViewById(R.id.btnSendImg1);

        listAdapter= new ChatListAdapter(chatMessage,this);
        listMessage.setAdapter(listAdapter);

        btnSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(edtMessage.getText().toString(), UserType.SELF);
            }
        });
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
