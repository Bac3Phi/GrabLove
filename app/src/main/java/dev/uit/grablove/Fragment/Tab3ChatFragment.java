package dev.uit.grablove.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.uit.grablove.Model.UserChatList;
import dev.uit.grablove.Model.UserChatListAdapter;
import dev.uit.grablove.R;

/**
 * Created by Administrator on 10/14/2017.
 */

public class Tab3ChatFragment extends Fragment {
    ListView UserList;
    UserChatListAdapter userChatListAdapter;
    ArrayList<UserChatList> userChatLists;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3_chat, container, false);
        userChatLists= new ArrayList<>();
        UserList= (ListView) rootView.findViewById(R.id.UserListView);
        userChatListAdapter = new UserChatListAdapter(userChatLists,this.getContext());
        UserList.setAdapter(userChatListAdapter);

        addUserToList(null,"Long Lons","DMM");
        addUserToList(null,"Shjn","cax cax cax");
        return rootView;
    }

    private void  addUserToList(String avatar, String recentUser, String recentChat)
    {
        UserChatList userchat= new UserChatList();
        //userchat.setAvatar(); tu cover qua string xong set lai nhe'
        userchat.setRecentUser(recentUser);
        userchat.setRecentChat(recentChat);
        userchat.setTime(new Date().getTime());

        userChatLists.add(userchat);
        if (userChatListAdapter!=null)
            userChatListAdapter.notifyDataSetChanged();
    }
}