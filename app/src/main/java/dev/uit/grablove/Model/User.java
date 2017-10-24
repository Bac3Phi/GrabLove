package dev.uit.grablove.Model;

/**
 * Created by Administrator on 10/12/2017.
 */

public class User {
    private String UserKey;
    private String Description;
    private String DoB;
    private String Location;
    private String Name;
    private String Password;
    private String Phone;
    private String Sex;
    private String UserName;

    public User(){

    }

    public User(String userKey, String description, String doB, String location, String name, String password, String phone, String sex, String userName) {
        UserKey = userKey;
        Description = description;
        DoB = doB;
        Location = location;
        Name = name;
        Password = password;
        Phone = phone;
        Sex = sex;
        UserName = userName;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
