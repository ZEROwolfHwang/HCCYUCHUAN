package com.ruitong.yuchuan.maptest.litepalbean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */
public class SupportPoint extends DataSupport implements Serializable {

    private double latitude;
    private double longitude;
    private String point_name;
    int image=-1;
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public String getPointName() {
        return point_name;
    }

    public void setPointName(String name) {
        this.point_name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
