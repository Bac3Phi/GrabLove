package dev.uit.grablove.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dev.uit.grablove.Constants;
import dev.uit.grablove.Model.User;
import dev.uit.grablove.Model.UserChatList;
import dev.uit.grablove.Model.UserChatListAdapter;
import dev.uit.grablove.R;

/**
 * Created by Administrator on 10/14/2017.
 */

public class Tab3ChatFragment extends Fragment {
    private ListView UserList;
    private UserChatListAdapter userChatListAdapter;
    private ArrayList<UserChatList> userChatLists;

    private FirebaseFirestore db;
    private SharedPreferences pre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3_chat, container, false);
        userChatLists= new ArrayList<>();
        UserList= (ListView) rootView.findViewById(R.id.UserListView);
        userChatListAdapter = new UserChatListAdapter(userChatLists,this.getContext());
        UserList.setAdapter(userChatListAdapter);

        db = FirebaseFirestore.getInstance();


        pre = getActivity().getSharedPreferences(Constants.REF_NAME, Context.MODE_PRIVATE);

        getFriendsList();

        UserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* if (userChatLists.get(position).isNewMess()){
                    userChatLists.get(position).setNewMess(false);
                    userChatListAdapter.notifyDataSetChanged();
                }*/
                Intent intent = new Intent(getContext(),fragment_tab3_chat_communicate.class);
                intent.putExtra("id", userChatLists.get(position).getId());
                startActivity(intent);
            }
        });

        //updateData();

        //Toast.makeText(getContext(), "On Create", Toast.LENGTH_LONG).show();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateData();
        //Toast.makeText(getContext(), "On Start", Toast.LENGTH_LONG).show();
    }

    private void updateData() {
        db.collection("Users/" + pre.getString(Constants.USER_KEY, "") +"/friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()){
                            for (DocumentSnapshot doc : task.getResult()) {
                                db.document("Users/" + doc.getString(Constants.DB_FRIEND_KEY))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    final UserChatList friend = new UserChatList();
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document != null) {
                                                        friend.setId(document.getId());
                                                        friend.setAvatar(document.getString(Constants.DB_USER_AVATAR));
                                                        friend.setRecentUser(document.getString(Constants.DB_USER_FULL_NAME));
                                                        db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends")
                                                                .whereEqualTo(Constants.DB_FRIEND_KEY, document.getId())
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (!task.getResult().isEmpty()){
                                                                            for (final DocumentSnapshot document : task.getResult()) {
                                                                                db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends/"
                                                                                        + document.getId() + "/chat")
                                                                                        .orderBy(Constants.CHAT_TIME)
                                                                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                                            @Override
                                                                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                                                                if (e == null) {
                                                                                                    if (!documentSnapshots.isEmpty())
                                                                                                    {
                                                                                                        DocumentSnapshot document = documentSnapshots.getDocuments().get(documentSnapshots.size()-1);
                                                                                                        friend.setRecentChat(document.getString(Constants.CHAT_MESS));
                                                                                                        friend.setTime(document.getLong(Constants.CHAT_TIME));
                                                                                                    }
                                                                                                    updateUser(friend);
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    private void updateUser(UserChatList friend) {
        for (int i = 0; i < userChatLists.size(); i++){
            if (userChatLists.get(i).getRecentUser().matches(friend.getRecentUser())){
                userChatLists.get(i).setRecentChat(friend.getRecentChat());
                userChatLists.get(i).setTime(friend.getTime());
                //userChatLists.get(i).setNewMess(true);
                break;
            }
        }
        if (userChatListAdapter!=null)
            userChatListAdapter.notifyDataSetChanged();
    }

    private void getFriendsList() {
        db.collection("Users/" + pre.getString(Constants.USER_KEY, "") +"/friends")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        userChatLists.clear();
                        for (DocumentSnapshot doc : documentSnapshots) {
                            addFriendToList(doc.getString(Constants.DB_FRIEND_KEY));
                        }
                    }
                });
        /*db.collection("Users/" + pre.getString(Constants.USER_KEY, "") +"/friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()){
                            for (DocumentSnapshot doc : task.getResult()) {
                                addFriendToList(doc.getString(Constants.DB_FRIEND_KEY));
                            }
                        }
                    }
                });*/
    }

    private void addFriendToList(String id) {
        db.document("Users/" + id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            UserChatList friend = new UserChatList();
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                friend.setId(document.getId());
                                friend.setAvatar(document.getString(Constants.DB_USER_AVATAR));
                                friend.setRecentUser(document.getString(Constants.DB_USER_FULL_NAME));
                                setNewChatMess(document.getId(), friend);
                            }
                        }
                    }
                });
    }

    private void setNewChatMess(String id, final UserChatList friend) {
        db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends")
                .whereEqualTo(Constants.DB_FRIEND_KEY, id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()){
                            for (final DocumentSnapshot document : task.getResult()) {
                                db.collection("Users/" + pre.getString(Constants.USER_KEY, "") + "/friends/"
                                        + document.getId() + "/chat")
                                        .orderBy(Constants.CHAT_TIME)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (!task.getResult().isEmpty())
                                                    {
                                                        DocumentSnapshot document = task.getResult().getDocuments().get(task.getResult().size()-1);
                                                        friend.setRecentChat(document.getString(Constants.CHAT_MESS));
                                                        friend.setTime(document.getLong(Constants.CHAT_TIME));
                                                    }
                                                    addUserToList(friend);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    private void  addUserToList(UserChatList userchat)
    {
        userChatLists.add(userchat);
        Collections.sort(userChatLists, new Comparator<UserChatList>() {
            @Override
            public int compare(UserChatList lhs, UserChatList rhs) {
                return rhs.getRecentUser().compareTo(lhs.getRecentUser());
            }
        });
        if (userChatListAdapter!=null)
            userChatListAdapter.notifyDataSetChanged();
    }
}