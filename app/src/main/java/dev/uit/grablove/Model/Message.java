package dev.uit.grablove.Model;

/**
 * Created by Jake on 10/24/2017.
 */

public class Message {
    private String msg;
    private String From;

    public Message(){

    }

    public Message(String msg, String from) {
        this.msg = msg;
        From = from;
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
}
