package dev.uit.grablove.Model;

/**
 * Created by Jake on 10/24/2017.
 */

public class ChatRoom {
    private ChatMate _chatmate;
    private Message _message;

    public ChatRoom(){

    }

    public ChatRoom(ChatMate _chatmate, Message _message) {
        this._chatmate = _chatmate;
        this._message = _message;
    }

    public ChatMate get_chatmate() {
        return _chatmate;
    }

    public void set_chatmate(ChatMate _chatmate) {
        this._chatmate = _chatmate;
    }

    public Message get_message() {
        return _message;
    }

    public void set_message(Message _message) {
        this._message = _message;
    }
}
