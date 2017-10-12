package com.ruitong.yuchuan.maptest.bean;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class PointInfoBean {
    private double distance;//距离
    private double fangwei;//方位
    private double jingdu;//经度
    private double weidu;//纬度
    private double hangsu;//航速

    public PointInfoBean() {
    }

    public PointInfoBean(double jingdu, double weidu) {
        this.jingdu = jingdu;
        this.weidu = weidu;
    }

    public double getHangsu() {
        return hangsu;
    }

    public void setHangsu(double hangsu) {
        this.hangsu = hangsu;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getFangwei() {
        return fangwei;
    }

    public void setFangwei(double fangwei) {
        this.fangwei = fangwei;
    }

    public double getJingdu() {
        return jingdu;
    }

    public void setJingdu(double jingdu) {
        this.jingdu = jingdu;
    }

    public double getWeidu() {
        return weidu;
    }

    public void setWeidu(double weidu) {
        this.weidu = weidu;
    }

    @Override
    public String toString() {
        return "DistanceBean{" +
                "distance=" + distance +
                ", fangwei=" + fangwei +
                ", jingdu=" + jingdu +
                ", weidu=" + weidu +
                ", hangsu=" + hangsu +
                '}';
    }
}
