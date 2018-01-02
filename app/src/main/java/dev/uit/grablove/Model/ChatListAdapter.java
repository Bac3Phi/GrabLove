package dev.uit.grablove.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dev.uit.grablove.R;
import dev.uit.grablove.View.EnlargeImageView;

/**
 * Created by PhiPoz on 14/11/2017.
 */

public class ChatListAdapter extends BaseAdapter {

    private ArrayList<Message> chatMessages;
    private Context context;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

    public ChatListAdapter(ArrayList<Message> chatMessages, Context context) {
        this.chatMessages = chatMessages;
        this.context = context;

    }


    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
            final Message message = chatMessages.get(position);
            final ViewHolder1 holder1;
            final ViewHolder2 holder2;

            if (message.getUserType() == UserType.SELF) {
                if (convertView == null) {
                    v = LayoutInflater.from(context).inflate(R.layout.item_tab3_message_self, null, false);
                    holder1 = new ViewHolder1();
                    holder1.messageTextView = (TextView) v.findViewById(R.id.txtTab3MessageUser2);
                    holder1.tvTime = (TextView) v.findViewById(R.id.tvTimeMessageUser2);
                    holder1.ibMessPic = (ImageView) v.findViewById(R.id.ivMessagePicUser2);
                    holder1.tvTime.setText(SIMPLE_DATE_FORMAT.format(message.getTime()));

                    if (message.getMsg().startsWith("https://firebasestorage.googleapis.com/")){
                        holder1.messageTextView.setVisibility(View.GONE);
                        holder1.ibMessPic.setVisibility(View.VISIBLE);

                        Glide.with(context).load(message.getMsg())
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.loading)
                                        .dontAnimate()
                                        .dontTransform()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .into(holder1.ibMessPic);
                    }
                    else {
                        holder1.messageTextView.setVisibility(View.VISIBLE);
                        holder1.ibMessPic.setVisibility(View.GONE);

                        holder1.messageTextView.setText(message.getMsg());
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder1.tvTime.getVisibility() == View.GONE){
                                holder1.tvTime.setVisibility(View.VISIBLE);
                            }else
                                holder1.tvTime.setVisibility(View.GONE);
                        }
                    });

                    v.setTag(holder1);
                } else {
                    v = convertView;
                    holder1 = (ViewHolder1) v.getTag();

                    if (message.getMsg().startsWith("https://firebasestorage.googleapis.com/")){
                        holder1.messageTextView.setVisibility(View.GONE);
                        holder1.ibMessPic.setVisibility(View.VISIBLE);

                        Glide.with(context).load(message.getMsg())
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.loading)
                                        .dontAnimate()
                                        .dontTransform()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .into(holder1.ibMessPic);
                    }
                    else {
                        holder1.messageTextView.setVisibility(View.VISIBLE);
                        holder1.ibMessPic.setVisibility(View.GONE);

                        holder1.messageTextView.setText(message.getMsg());
                    }

                    holder1.tvTime.setText(SIMPLE_DATE_FORMAT.format(message.getTime()));
                }

                holder1.ibMessPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = (new Intent(view.getContext(),EnlargeImageView.class));
                        intent.putExtra("url", message.getMsg());
                        context.startActivity(intent);
                    }
                });

            } else if (message.getUserType() == UserType.OTHER) {

                if (convertView == null) {
                    v = LayoutInflater.from(context).inflate(R.layout.item_tab3_message_other, null, false);
                    holder2 = new ViewHolder2();
                    holder2.messageTextView = (TextView) v.findViewById(R.id.txtTab3MessageUser1);
                    holder2.tvTime = (TextView) v.findViewById(R.id.tvTimeMessageUser1);
                    holder2.ibMessPic = (ImageView) v.findViewById(R.id.ivMessagePicUser1);
                    holder2.tvTime.setText(SIMPLE_DATE_FORMAT.format(message.getTime()));

                    if (message.getMsg().startsWith("https://firebasestorage.googleapis.com/")){
                        holder2.messageTextView.setVisibility(View.GONE);
                        holder2.ibMessPic.setVisibility(View.VISIBLE);

                        Glide.with(context).load(message.getMsg())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.loading)
                                    .dontAnimate()
                                    .dontTransform()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(holder2.ibMessPic);
                    }
                    else {
                        holder2.messageTextView.setVisibility(View.VISIBLE);
                        holder2.ibMessPic.setVisibility(View.GONE);

                        holder2.messageTextView.setText(message.getMsg());
                    }

                    v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder2.tvTime.getVisibility() == View.GONE){
                            holder2.tvTime.setVisibility(View.VISIBLE);
                        }else
                            holder2.tvTime.setVisibility(View.GONE);
                    }
                    });

                    v.setTag(holder2);

                } else {
                    v = convertView;

                    holder2 = (ViewHolder2) v.getTag();

                    if (message.getMsg().startsWith("https://firebasestorage.googleapis.com/")){
                        holder2.messageTextView.setVisibility(View.GONE);
                        holder2.ibMessPic.setVisibility(View.VISIBLE);

                        Glide.with(context).load(message.getMsg())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.loading)
                                    .dontAnimate()
                                    .dontTransform()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(holder2.ibMessPic);
                    }
                    else {
                        holder2.messageTextView.setVisibility(View.VISIBLE);
                        holder2.ibMessPic.setVisibility(View.GONE);

                        holder2.messageTextView.setText(message.getMsg());
                    }

                    holder2.tvTime.setText(SIMPLE_DATE_FORMAT.format(message.getTime()));
                }

                holder2.ibMessPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = (new Intent(view.getContext(),EnlargeImageView.class));
                        intent.putExtra("url", message.getMsg());
                        context.startActivity(intent);
                    }
                });

            }

        return v;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = chatMessages.get(position);
        return message.getUserType().ordinal();
    }

    class ViewHolder1 {
        public TextView messageTextView;
        public TextView timeTextView;
        public TextView tvTime;
        public ImageView ibMessPic;

    }

    private class ViewHolder2 {
        public ImageView messageStatus;
        public TextView messageTextView;
        public TextView timeTextView;
        public TextView tvTime;
        public ImageView ibMessPic;

    }
}
