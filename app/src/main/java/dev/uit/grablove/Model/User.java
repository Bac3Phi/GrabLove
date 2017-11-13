package dev.uit.grablove.Model;

/**
 * Created by Administrator on 10/12/2017.
 */

public class User {
    private String UserKey;
    private String avatar;
    private String Description;
    private int age;
    private double lat_location;
    private double long_location;
    private int distance;
    private String fullname;
    private String sex;

    public User(){

    }

    public User(String userKey, String avatar, String description, int age, double lat_location, double long_location, int distance, String fullname, String sex) {
        UserKey = userKey;
        this.avatar = avatar;
        Description = description;
        this.age = age;
        this.lat_location = lat_location;
        this.long_location = long_location;
        this.distance = distance;
        this.fullname = fullname;
        this.sex = sex;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getLat_location() {
        return lat_location;
    }

    public void setLat_location(double lat_location) {
        this.lat_location = lat_location;
    }

    public double getLong_location() {
        return long_location;
    }

    public void setLong_location(double long_location) {
        this.long_location = long_location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
