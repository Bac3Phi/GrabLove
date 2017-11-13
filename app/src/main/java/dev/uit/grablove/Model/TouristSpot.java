package dev.uit.grablove.Model;

/**
 * Created by Phi Poz on 10/18/2017.
 */

public class TouristSpot {
    private String name;
    private String age;
    private String distance;
    private String url;

    public TouristSpot(String name, String age, String distance, String url) {
        this.name = name;
        this.age = age;
        this.distance = distance;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}