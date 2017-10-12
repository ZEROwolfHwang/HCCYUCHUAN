package com.ruitong.yuchuan.maptest.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * @author sineom
 * @version 1.0
 * @time 2017/6/25 16:18
 * @des ${TODO}
 * @updateAuthor ${Author}
 * @updataTIme 2017/6/25
 * @updataDes ${描述更新内容}
 */

public  class BaseActivity extends AppCompatActivity{
    private static final String TAG = "YuChuan";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {

            finish();
            return;
        }
        Logger.init(TAG).logLevel(LogLevel.FULL);
//        Logger.init(TAG).logLevel(LogLevel.NONE);

      //  registerBroadcast();
    }

    public Context getContext() {
        return BaseActivity.this;
    }



   /* *//**
     * 广播接收器
     *
     * @author len
     *//*
    public BroadcastReceiver AisBroadcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String a = intent.getStringExtra("selectMMsi");
            if (a != null) {
                receiver(a);
            }
            Gpsbean gpsbean = (Gpsbean) intent.getSerializableExtra("gps");
            if (gpsbean != null) {
                gpsreceiver(gpsbean);
            }
            Jdbean jdbean = (Jdbean) intent.getSerializableExtra("pdop");
            if (jdbean != null) {
                hdopreceiver (jdbean);
            }

            String  toPager= intent.getStringExtra("toPager");
            if(toPager!=null&& !TextUtils.isEmpty(toPager)){

                int  isRuingBaoJing= intent.getIntExtra("isRuingBaoJing",-1);
                if(isRuingBaoJing==1) {
                    List<ShipBean> ls = (List<ShipBean>) intent.getSerializableExtra("baoJingShip");
                    toPager(toPager,isRuingBaoJing,ls);
                }else if(isRuingBaoJing==0){
                    toPager(toPager,isRuingBaoJing,null);
                }else{
                    toPager(toPager,-1,null);
                }

            }

        }
    };
    *//**
     * 注册广播
     *//*
    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.Aisaction);

        registerReceiver(AisBroadcast, intentFilter);// 注册接受消息广播


    }*/

}
