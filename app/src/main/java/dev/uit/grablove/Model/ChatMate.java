package dev.uit.grablove.Model;

/**
 * Created by Jake on 10/24/2017.
 */

public class ChatMate {
    private String PhotoUrl1;
    private String UserName1;
    private String PhotoUrl2;
    private String UserName2;

    public ChatMate(){

    }

    public ChatMate(String photoUrl1, String userName1, String photoUrl2, String userName2) {
        PhotoUrl1 = photoUrl1;
        UserName1 = userName1;
        PhotoUrl2 = photoUrl2;
        UserName2 = userName2;
    }

    public String getPhotoUrl1() {
        return PhotoUrl1;
    }

    public void setPhotoUrl1(String photoUrl1) {
        PhotoUrl1 = photoUrl1;
    }

    public String getUserName1() {
        return UserName1;
    }

    public void setUserName1(String userName1) {
        UserName1 = userName1;
    }

    public String getPhotoUrl2() {
        return PhotoUrl2;
    }

    public void setPhotoUrl2(String photoUrl2) {
        PhotoUrl2 = photoUrl2;
    }

    public String getUserName2() {
        return UserName2;
    }

    public void setUserName2(String userName2) {
        UserName2 = userName2;
    }
}
