package com.ruitong.yuchuan.maptest.helper;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;


/**
 * Created by Administrator on 2017/10/11.
 */

public class KeyBoardHelper {
    public static void closeSoftKeybord(ImageButton btn_confirm, Context context) {
//        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btn_confirm.getWindowToken(), 0); //强制隐藏键盘

    }

}
