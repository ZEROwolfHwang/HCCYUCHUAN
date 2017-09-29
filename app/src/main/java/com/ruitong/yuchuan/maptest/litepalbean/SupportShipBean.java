package com.ruitong.yuchuan.maptest.litepalbean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/7/30.
 */
public class SupportShipBean extends DataSupport implements Parcelable {
    private String AISList_1;
    private String AISList_2;
    private String AISList_3;
    private String AISList_4;
    private String AISList_5_MMSI;
    private String AISList_6;
    private String AISList_7;
    private String AISList_8;
    private String AISList_9;
    private String AISList_10;
    private String AISList_11;
    private String AISList_12;
    private String AISList_13;
    private String AISList_14;
    private String AISList_15;
    private String AISList_16;
    private String AISList_17;
    private String AISList_18;
    private String AISList_19;
    private String AISList_20;
    private String AISList_21;

    public SupportShipBean() {
    }

    protected SupportShipBean(Parcel in) {
        AISList_1 = in.readString();
        AISList_2 = in.readString();
        AISList_3 = in.readString();
        AISList_4 = in.readString();
        AISList_5_MMSI = in.readString();
        AISList_6 = in.readString();
        AISList_7 = in.readString();
        AISList_8 = in.readString();
        AISList_9 = in.readString();
        AISList_10 = in.readString();
        AISList_11 = in.readString();
        AISList_12 = in.readString();
        AISList_13 = in.readString();
        AISList_14 = in.readString();
        AISList_15 = in.readString();
        AISList_16 = in.readString();
        AISList_17 = in.readString();
        AISList_18 = in.readString();
        AISList_19 = in.readString();
        AISList_20 = in.readString();
        AISList_21 = in.readString();
    }

    public static final Creator<SupportShipBean> CREATOR = new Creator<SupportShipBean>() {
        @Override
        public SupportShipBean createFromParcel(Parcel in) {
            return new SupportShipBean(in);
        }

        @Override
        public SupportShipBean[] newArray(int size) {
            return new SupportShipBean[size];
        }
    };

    @Override
    public String toString() {
        return "SupportShipBean{" +
                "AISList_1='" + AISList_1 + '\'' +
                ", AISList_2='" + AISList_2 + '\'' +
                ", AISList_3='" + AISList_3 + '\'' +
                ", AISList_4='" + AISList_4 + '\'' +
                ", AISList_5_MMSI='" + AISList_5_MMSI + '\'' +
                ", AISList_6='" + AISList_6 + '\'' +
                ", AISList_7='" + AISList_7 + '\'' +
                ", AISList_8='" + AISList_8 + '\'' +
                ", AISList_9='" + AISList_9 + '\'' +
                ", AISList_10='" + AISList_10 + '\'' +
                ", AISList_11='" + AISList_11 + '\'' +
                ", AISList_12='" + AISList_12 + '\'' +
                ", AISList_13='" + AISList_13 + '\'' +
                ", AISList_14='" + AISList_14 + '\'' +
                ", AISList_15='" + AISList_15 + '\'' +
                ", AISList_16='" + AISList_16 + '\'' +
                ", AISList_17='" + AISList_17 + '\'' +
                ", AISList_18='" + AISList_18 + '\'' +
                ", AISList_19='" + AISList_19 + '\'' +
                ", AISList_20='" + AISList_20 + '\'' +
                ", AISList_21='" + AISList_21 + '\'' +
                '}';
    }

    public String getAISList_1() {
        return AISList_1;
    }

    public void setAISList_1(String AISList_1) {
        this.AISList_1 = AISList_1;
    }

    public String getAISList_2() {
        return AISList_2;
    }

    public void setAISList_2(String AISList_2) {
        this.AISList_2 = AISList_2;
    }

    public String getAISList_3() {
        return AISList_3;
    }

    public void setAISList_3(String AISList_3) {
        this.AISList_3 = AISList_3;
    }

    public String getAISList_4() {
        return AISList_4;
    }

    public void setAISList_4(String AISList_4) {
        this.AISList_4 = AISList_4;
    }

    public String getAISList_5_MMSI() {
        return AISList_5_MMSI;
    }

    public void setAISList_5_MMSI(String AISList_5_MMSI) {
        this.AISList_5_MMSI = AISList_5_MMSI;
    }

    public String getAISList_6() {
        return AISList_6;
    }

    public void setAISList_6(String AISList_6) {
        this.AISList_6 = AISList_6;
    }

    public String getAISList_7() {
        return AISList_7;
    }

    public void setAISList_7(String AISList_7) {
        this.AISList_7 = AISList_7;
    }

    public String getAISList_8() {
        return AISList_8;
    }

    public void setAISList_8(String AISList_8) {
        this.AISList_8 = AISList_8;
    }

    public String getAISList_9() {
        return AISList_9;
    }

    public void setAISList_9(String AISList_9) {
        this.AISList_9 = AISList_9;
    }

    public String getAISList_10() {
        return AISList_10;
    }

    public void setAISList_10(String AISList_10) {
        this.AISList_10 = AISList_10;
    }

    public String getAISList_11() {
        return AISList_11;
    }

    public void setAISList_11(String AISList_11) {
        this.AISList_11 = AISList_11;
    }

    public String getAISList_12() {
        return AISList_12;
    }

    public void setAISList_12(String AISList_12) {
        this.AISList_12 = AISList_12;
    }

    public String getAISList_13() {
        return AISList_13;
    }

    public void setAISList_13(String AISList_13) {
        this.AISList_13 = AISList_13;
    }

    public String getAISList_14() {
        return AISList_14;
    }

    public void setAISList_14(String AISList_14) {
        this.AISList_14 = AISList_14;
    }

    public String getAISList_15() {
        return AISList_15;
    }

    public void setAISList_15(String AISList_15) {
        this.AISList_15 = AISList_15;
    }

    public String getAISList_16() {
        return AISList_16;
    }

    public void setAISList_16(String AISList_16) {
        this.AISList_16 = AISList_16;
    }

    public String getAISList_17() {
        return AISList_17;
    }

    public void setAISList_17(String AISList_17) {
        this.AISList_17 = AISList_17;
    }

    public String getAISList_18() {
        return AISList_18;
    }

    public void setAISList_18(String AISList_18) {
        this.AISList_18 = AISList_18;
    }

    public String getAISList_19() {
        return AISList_19;
    }

    public void setAISList_19(String AISList_19) {
        this.AISList_19 = AISList_19;
    }

    public String getAISList_20() {
        return AISList_20;
    }

    public void setAISList_20(String AISList_20) {
        this.AISList_20 = AISList_20;
    }

    public String getAISList_21() {
        return AISList_21;
    }

    public void setAISList_21(String AISList_21) {
        this.AISList_21 = AISList_21;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AISList_1);
        dest.writeString(AISList_2);
        dest.writeString(AISList_3);
        dest.writeString(AISList_4);
        dest.writeString(AISList_5_MMSI);
        dest.writeString(AISList_6);
        dest.writeString(AISList_7);
        dest.writeString(AISList_8);
        dest.writeString(AISList_9);
        dest.writeString(AISList_10);
        dest.writeString(AISList_11);
        dest.writeString(AISList_12);
        dest.writeString(AISList_13);
        dest.writeString(AISList_14);
        dest.writeString(AISList_15);
        dest.writeString(AISList_16);
        dest.writeString(AISList_17);
        dest.writeString(AISList_18);
        dest.writeString(AISList_19);
        dest.writeString(AISList_20);
        dest.writeString(AISList_21);
    }
}
