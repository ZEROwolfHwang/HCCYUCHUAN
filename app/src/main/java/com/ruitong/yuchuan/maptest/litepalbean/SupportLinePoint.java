package com.ruitong.yuchuan.maptest.litepalbean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */
public class SupportLinePoint extends DataSupport implements Serializable {

    private String line_name;
    private double latitude;
    private double longitude;
    private String point_name;
    private int position;

    @Override
    public String toString() {
        return "SupportLinePoint{" +
                "line_name='" + line_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", point_name='" + point_name + '\'' +
                ", position=" + position +
                '}';
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
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

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }
}
