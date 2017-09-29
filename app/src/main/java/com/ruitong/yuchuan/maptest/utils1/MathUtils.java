package com.ruitong.yuchuan.maptest.utils1;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MathUtils {

    //将小数转换为度分秒
    public static String convertToSexagesimal(double num) {

        int du = (int) Math.floor(Math.abs(num));    //获取整数部分
        double temp = getdPoint(Math.abs(num));
//        int fen = (int) Math.floor(temp); //获取整数部分
//        double miao = getdPoint(temp) * 60;
        if (num < 0)
            return "-" + du + "°" + temp + "′";

        return du + "°" + temp + "′" ;

    }

    //获取小数部分
    public static double getdPoint(double num) {
        double d = num;
        int fInt = (int) d;
        BigDecimal b1 = new BigDecimal(Double.toString(d));
        BigDecimal b2 = new BigDecimal(Integer.toString(fInt));
        double dPoint = b1.subtract(b2).floatValue();
        DecimalFormat df = new DecimalFormat("##0.000");
        Double aDouble = Double.valueOf(df.format(dPoint * 60));
        return aDouble;
    }
}
