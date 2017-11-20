package dev.uit.grablove.Model;

/**
 * Created by Jake on 10/24/2017.
 */

public class Message {
    private String msg;
    private String From;
    private UserType userType;
    private long Time;
    public Message(){

    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
