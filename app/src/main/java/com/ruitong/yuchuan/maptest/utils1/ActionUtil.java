package com.ruitong.yuchuan.maptest.utils1;

/**
 * Created by Administrator on 2017/9/29.
 */

public class ActionUtil {
    /**
     * 判断是否有长按动作发生
     */
    public static boolean isLongPressed(float lastX, float lastY,
                                  float thisX, float thisY,
                                  long lastDownTime, long thisEventTime,
                                  long longPressTime) {
        float offsetX = Math.abs(thisX - lastX);
        float offsetY = Math.abs(thisY - lastY);
        long intervalTime = thisEventTime - lastDownTime;
        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
            return true;
        }
        return false;
    }
}
