package dev.uit.grablove.Model;

import java.sql.Time;

/**
 * Created by PhiPoz on 19/11/2017.
 */

public class UserChatList {
    private String avatar;
    private String recentUser;
    private String recentChat;
    private long time;

    public UserChatList(String avatar, String recentUser, String recentChat) {
        this.avatar = avatar;
        this.recentUser = recentUser;
        this.recentChat = recentChat;

    }

    public UserChatList() {

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRecentUser() {
        return recentUser;
    }

    public void setRecentUser(String recentUser) {
        this.recentUser = recentUser;
    }

    public String getRecentChat() {
        return recentChat;
    }

    public void setRecentChat(String recentChat) {
        this.recentChat = recentChat;
    }
}
