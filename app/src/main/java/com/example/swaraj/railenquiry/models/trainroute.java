package com.example.swaraj.railenquiry.models;

/**
 * Created by swaraj on 24/10/16.
 */

public class trainroute {

    private int no;
    private int distance;
    private String Sta;
    private String city;
    private String dep;
    private String arr;


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getSta() {
        return Sta;
    }

    public void setSta(String sta) {
        Sta = sta;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }
}
