package com.ruitong.yuchuan.maptest.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.adapter.GpsListAdapate;
import com.ruitong.yuchuan.maptest.bean.MyGpsSatellite;
import com.ruitong.yuchuan.maptest.gps.AlxLocationService;
import com.ruitong.yuchuan.maptest.surface.SatellitesView;
import com.ruitong.yuchuan.maptest.ui.HorizontalListView;
import com.ruitong.yuchuan.maptest.utils.MathUtils;
import com.ruitong.yuchuan.maptest.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GPSFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ACTION_GPS_BROAD = "action_gps_broad";
    @BindView(R.id.gps_state)
    TextView mGpsState;
    @BindView(R.id.gps_jingdu)
    TextView mGpsJingdu;
    @BindView(R.id.gps_weidu)
    TextView mGpsWeidu;
    @BindView(R.id.gps_jingquedu)
    TextView mGpsJingquedu;
    Unbinder unbinder;

    private int minTime = 1000;
    private int minDistance = 0;
    private LocationManager locationManager;
    private SatellitesView satellitesView;
    private Bitmap bg, spots;//背景和圆点的bitmap图
    private List<GpsSatellite> satelliteList = new ArrayList<>();
    private static List<MyGpsSatellite> gpsList2 = new ArrayList<>();
    private List<MyGpsSatellite> gpsList = new ArrayList<>();
    private HorizontalListView gpslv;
    private GpsListAdapate gpsListAdapate;
    private Runnable runnable;
    private Activity mActivity;

    private Timer mTimer_Gps;

    public Handler handler = new Handler();


    public GPSFragment() {

    }

    public static GPSFragment newInstance(String param1, String param2) {
        GPSFragment fragment = new GPSFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this.getActivity();
        PermissionUtils.verifyStoragePermissions(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_gps, container, false);

        mTimer_Gps = new Timer();
        mTimer_Gps.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AlxLocationService.setGpsInfoListener(location -> {
//                    Logger.i(location.toString());
                    if (location != null) {
                        String lat = MathUtils.convertToSexagesimal(location.latitude);
                        String lng = MathUtils.convertToSexagesimal(location.longitude);
                        getActivity().runOnUiThread(() -> {
                            mGpsJingdu.setText(lng);
                            mGpsWeidu.setText(lat);
                            mGpsJingquedu.setText(location.accuracy + "");
                            if (location != null) {
                                mGpsState.setText("定位");
                                mGpsState.setTextColor(Color.DKGRAY);
                            } else {
                                mGpsState.setText("未定位");
                                mGpsState.setTextColor(Color.RED);
                            }
                        });
                    }
                });
            }
        }, 0, 5000);

        bg = BitmapFactory.decodeResource(getResources(),
                R.drawable.compass);
        spots = BitmapFactory.decodeResource(getResources(),
                R.drawable.satellite_mark);
//        gridView = (GridView) mView.findViewById(R.id.grid);
        gpslv = (HorizontalListView) mView.findViewById(R.id.gps_lv);
        gpslv.setHaveScrollbar(false);
        satellitesView = (SatellitesView) mView.findViewById(R.id.satellitesView);
        satellitesView.setBitmap(bg, spots);
        registerListener();
        satellitesView.setSatellites(satelliteList);
        if (gpsList.size() == 0) {

        }
        Collections.sort(gpsList2);
        gpsList.addAll(gpsList2);

        gpsListAdapate = new GpsListAdapate(gpsList, mActivity);

        if (gpsList != null) {
            gpslv.setAdapter(gpsListAdapate);
            startPush();
        }

        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    /**
     * 注册监听
     */
    private void registerListener() {
        if (locationManager == null) {
            locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        }
        //侦听位置信息(经纬度变化)
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                minTime, minDistance, locationListener);
        // 侦听GPS状态，主要是捕获到的各个卫星的状态
        locationManager.addGpsStatusListener(gpsStatusListener);
        //TODO:考虑增加监听传感器中的方位数据，以使罗盘的北能自动指向真实的北向

    }

    /**
     * 移除监听
     */
    private void unregisterListener() {
        if (locationManager != null) {
            locationManager.removeGpsStatusListener(gpsStatusListener);
            locationManager.removeUpdates(locationListener);
        }
    }

    static public boolean isShow = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isShow = true;
            if (mActivity != null) {
                hidShuRuFa(mActivity);
            }
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            if (mActivity != null) {
                hidShuRuFa(mActivity);
            }
            isShow = false;
        }
    }

    /*
   隐藏输入法
    */
    public void hidShuRuFa(Context context) {


        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen && mActivity.getCurrentFocus() != null)

        {
            if (mActivity != null) {
                imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }

    /**
     * 坐标位置监听
     */
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            int x = 1;
            StringBuffer sb = new StringBuffer();
            if (location != null) {
                x = 2;
            }
            int fmt = Location.FORMAT_DEGREES;
            sb.append(Location.convert(location.getLongitude(), fmt));
            sb.append(" ");
            sb.append(Location.convert(location.getLatitude(), fmt));
            Logger.d("GPS-NMEA", location.getLatitude() + "," + location.getLongitude() + "GPS界面");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {

                case LocationProvider.OUT_OF_SERVICE:
                    Logger.d("GPS-NMEA", "OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Logger.d("GPS-NMEA", " TEMPORARILY_UNAVAILABLE");
                    break;
                case LocationProvider.AVAILABLE:
                    Logger.d("GPS-NMEA", "" + provider + "");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };
    /**
     * Gps状态监听
     */
    private GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                case GpsStatus.GPS_EVENT_FIRST_FIX: {
                    // 第一次定位时间UTC gps可用
                    break;
                }
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS: {// 周期的报告卫星状态
                    // 得到所有收到的卫星的信息，包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）

                    try {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                    Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
                    satelliteList.clear();
                    gpsList2.clear();
                    if (satellites != null)
                        for (GpsSatellite satellite : satellites) {
                            // 包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
                            if (satellite.usedInFix()) {
                                if (satellite.getPrn() <= 32) {
                                    satelliteList.add(satellite);
                                    MyGpsSatellite my = new MyGpsSatellite();
                                    my.setmPrn(satellite.getPrn());
                                    my.setmAzimuth(satellite.getAzimuth());
                                    my.setmSnr(satellite.getSnr());
                                    my.setmElevation(satellite.getElevation());
                                    gpsList2.add(my);
                                }
                            }
                        }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public void initList() {
        mActivity.runOnUiThread(() -> {
            if (gpsList2.size() > 0) {
                gpsList.clear();
                Collections.sort(gpsList2);
                gpsList.addAll(gpsList2);

                gpsListAdapate.notifyDataSetChanged();
            }
        });
    }

    public void startPush() {

        if (handler == null) {
            handler = new Handler();
        }
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    satellitesView.setSatellites(satelliteList);
                    initList();
                    handler.postDelayed(this, 2000);

                }
            };
            handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerListener();
        satellitesView.setBitmap(bg, spots);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTimer_Gps != null) {
            mTimer_Gps.cancel();
        }
    }
    @Override
    public void onDestroy() {
        unregisterListener();
        unbinder.unbind();
        super.onDestroy();
    }
}
