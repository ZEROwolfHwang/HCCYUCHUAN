package com.ruitong.yuchuan.maptest.gps;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyLocation implements Serializable{
    public double latitude;
    public double longitude;
    public long updateTime;//最后更新时间，用于做精确度择优
    public float accuracy;
    public float speed;
    private static MyLocation myLocation;
    MyLocation(){}

    public MyLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static MyLocation getInstance(){
        if(myLocation == null)myLocation = new MyLocation();
        return myLocation;
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", updateTime=" + updateTime +
                ", accuracy=" + accuracy +
                ", speed=" + speed +
                '}';
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public static MyLocation getMyLocation() {
        return myLocation;
    }

    public static void setMyLocation(MyLocation myLocation) {
        MyLocation.myLocation = myLocation;
    }
}
