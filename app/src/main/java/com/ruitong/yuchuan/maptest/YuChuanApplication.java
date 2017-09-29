package com.ruitong.yuchuan.maptest;

import android.app.Application;

import org.litepal.LitePal;


/**
 * @author sineom
 * @version 1.0
 * @time 2017/6/25 12:28
 * @des ${TODO}
 * @updateAuthor ${Author}
 * @updataTIme 2017/6/25
 * @updataDes ${描述更新内容}
 */

public class YuChuanApplication extends Application {
    public static YuChuanApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        LitePal.initialize(this);
    }
}
