package com.ruitong.yuchuan.maptest.litepalbean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class SupportLine extends DataSupport {


    private List<SupportLinePoint> mLinePointList;
    private String line_name;
    private String line_time;

    public List<SupportLinePoint> getLinePointList() {
        return DataSupport.where("line_name = ?", line_name).find(SupportLinePoint.class);
    }

    public void setLinePointList(List<SupportLinePoint> linePointList) {
        mLinePointList = linePointList;
    }

    public String getLine_time() {
        return line_time;
    }

    public void setLine_time(String line_time) {
        this.line_time = line_time;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }


}
