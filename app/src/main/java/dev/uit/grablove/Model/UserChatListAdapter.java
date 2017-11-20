package dev.uit.grablove.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.uit.grablove.R;

/**
 * Created by Administrator on 19/11/2017.
 */

public class UserChatListAdapter extends BaseAdapter{

    private ArrayList<UserChatList> userChatLists;
    private Context context;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm");

    public UserChatListAdapter(ArrayList<UserChatList> userChatLists, Context context)
    {
        this.userChatLists = userChatLists;
        this.context = context;
    }
    @Override
    public int getCount() {
        return userChatLists.size();
    }

    @Override
    public Object getItem(int position) {
        return userChatLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
            UserChatList userChatList = userChatLists.get(position);
            ViewHolder holder;
            if (convertView == null)
            {
                v = LayoutInflater.from(context).inflate(R.layout.item_tab3_chat, parent, false);
                holder= new ViewHolder();
                holder.avatar= (CircleImageView) v.findViewById(R.id.ivAvatarTab3UserList);
                holder.username= (TextView) v.findViewById(R.id.tvUserNameTab3UserList);
                holder.recentchat= (TextView) v.findViewById(R.id.tvRecentChatTab3UserList);
                holder.time = (TextView) v.findViewById(R.id.tvTimeTab3UserList);

                if (userChatList.getRecentChat() == null){
                    holder.recentchat.setVisibility(View.GONE);
                }

                Glide.with(v.getContext()).load(userChatList.getAvatar()).into(holder.avatar);
                holder.username.setText(userChatList.getRecentUser());
                holder.recentchat.setText(userChatList.getRecentChat());
                holder.time.setText(SIMPLE_DATE_FORMAT.format(userChatList.getTime()));
                v.setTag(holder);

            }
            else
            {
                v = convertView;
                holder = (ViewHolder) v.getTag();
                //holder.avatar= (CircleImageView) v.findViewById(R.id.ivAvatarTab3UserList);
               // holder.username= (TextView) v.findViewById(R.id.tvUserNameTab3UserList);
                //holder.recentchat= (TextView) v.findViewById(R.id.tvRecentChatTab3UserList);
               //holder.time = (TextView) v.findViewById(R.id.tvTimeTab3UserList);

                Glide.with(v.getContext()).load(userChatList.getAvatar()).into(holder.avatar);
                holder.username.setText(userChatList.getRecentUser());
                holder.recentchat.setText(userChatList.getRecentChat());
                holder.time.setText(SIMPLE_DATE_FORMAT.format(userChatList.getTime()));
            }
        return v;
    }
    private class ViewHolder
    {
        CircleImageView avatar;
        TextView username;
        TextView recentchat;
        TextView time;

    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
