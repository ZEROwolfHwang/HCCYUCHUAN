package com.ruitong.yuchuan.maptest.tools;

import android.widget.Toast;

import com.ruitong.yuchuan.maptest.YuChuanApplication;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class ToastUtils {

    private static Toast mToast;

    /**
     * 单利土司
     *
     * @param content 土司的内容
     */
    public static void singleToast(String content) {

        Observable.just(content)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (mToast == null) {
                        mToast = Toast.makeText(YuChuanApplication.sApplication, null, Toast.LENGTH_SHORT);
                    }
                    mToast.setText(content);
                    mToast.show();
                });

    }

}
