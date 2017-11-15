package dev.uit.grablove.Model;

/**
 * Created by Jake on 10/24/2017.
 */

public class Message {
    private String msg;
    private String From;
    private UserType userType;
    public Message(){

    }

    public Message(String msg, UserType userType) {
        this.msg = msg;
        this.userType = userType;


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
