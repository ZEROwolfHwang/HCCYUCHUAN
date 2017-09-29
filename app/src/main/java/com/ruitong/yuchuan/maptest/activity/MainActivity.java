package com.ruitong.yuchuan.maptest.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;
import com.qozix.tileview.paths.CompositePathView;
import com.qozix.tileview.widgets.ZoomPanLayout;
import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.dialog.SelfDialog;
import com.ruitong.yuchuan.maptest.gps.AlxLocationManager;
import com.ruitong.yuchuan.maptest.gps.AlxLocationService;
import com.ruitong.yuchuan.maptest.manager.GlobalManager;
import com.ruitong.yuchuan.maptest.map.BitmapProviderAssets3;
import com.ruitong.yuchuan.maptest.map.CollectPointBean;
import com.ruitong.yuchuan.maptest.map.Constant;
import com.ruitong.yuchuan.maptest.map.DBManager;
import com.ruitong.yuchuan.maptest.utils1.ActionUtil;
import com.ruitong.yuchuan.maptest.utils1.ConvertUtils;
import com.ruitong.yuchuan.maptest.utils1.DecimalCalculate;
import com.ruitong.yuchuan.maptest.utils1.GetLongLati;
import com.ruitong.yuchuan.maptest.utils1.LocationUtils;
import com.ruitong.yuchuan.maptest.utils1.MathUtils;
import com.ruitong.yuchuan.maptest.utils1.PermissionUtils;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_jingdu)
    TextView mMainJingdu;
    @BindView(R.id.main_weidu)
    TextView mMainWeidu;
    @BindView(R.id.main_hangsu)
    TextView mMainHangsu;
    @BindView(R.id.main_hangxiang)
    TextView mMainHangxiang;
    @BindView(R.id.main_jia)
    ImageView mMainJia;
    @BindView(R.id.main_dingwei)
    ImageView mMainDingwei;
    @BindView(R.id.main_jian)
    ImageView mMainJian;
    @BindView(R.id.main_text_bilichi)
    TextView mMainTextBilichi;
    @BindView(R.id.main_view_bilichi)
    TextView mMainViewBilichi;
    @BindView(R.id.main_sousuo)
    TextView mMainSousuo;
    @BindView(R.id.main_celiang)
    TextView mMainCeliang;
    @BindView(R.id.main_daohang)
    TextView mMainDaohang;
    @BindView(R.id.main_shoucang)
    TextView mMainShoucang;
    @BindView(R.id.tile_view)
    TileView mTileView;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_text_info_dialog)
    LinearLayout mMainTextInfoDialog;


    //显示标记和路径的点列表
    private ArrayList<double[]> points = new ArrayList<>();

    {
        points.add(new double[]{Constant.TimesNeed * (120.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(24, 5) - Constant.minY5 * 256)});
        points.add(new double[]{Constant.TimesNeed * (118.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(23, 5) - Constant.minY5 * 256)});
        points.add(new double[]{Constant.TimesNeed * (116.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(22, 5) - Constant.minY5 * 256)});
        points.add(new double[]{Constant.TimesNeed * (120.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(23.5, 5) - Constant.minY5 * 256)});
        points.add(new double[]{Constant.TimesNeed * (118.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(23.2, 5) - Constant.minY5 * 256)});
        points.add(new double[]{Constant.TimesNeed * (116.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(22.3, 5) - Constant.minY5 * 256)});

    }

    List<View> ls_view_mark = new ArrayList<>();

    double wd_d;
    double jd_d;

    float startScale = -1;
    float endScale = -1;
    List<Integer> zls = new ArrayList<>();
    /**
     * 经纬度弹窗
     */
    private View viewLL;

    List<CompositePathView.DrawablePath> yuan = new ArrayList<>();

    private float windowWidth, windowHeight;
    private double CirX = 0;
    private double CirY = 0;
    float xDown, yDown, xUp;
    boolean isLongClickModule = false;
    boolean isLongClicking = false;
    private MainActivity mActivity;
    private Context mContext;
    private Timer mTimer_Gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mActivity = this;
        LitePal.getDatabase();
        PermissionUtils.verifyStoragePermissions(mActivity);
        mContext = this;
        initToolbar();
        mTileView.setSaveEnabled(true);
        initView();
        initTileView();


    }

    private void initView() {
        mMainTextInfoDialog.setOnClickListener(this);
        mMainJia.setOnClickListener(this);
        mMainJian.setOnClickListener(this);
        mMainDingwei.setOnClickListener(this);
        mMainSousuo.setOnClickListener(this);
        mMainCeliang.setOnClickListener(this);
        mMainDaohang.setOnClickListener(this);
        mMainShoucang.setOnClickListener(this);
    }


    /**
     * 初始化海图
     */
    public void initTileView() {

        mTileView.setBackgroundColor(0xFFe7e7e7);
        mTileView.addDetailLevel(0.5f, "tiles/map2/5/%d_%d_5.png", Constant.wapianWidth, Constant.wapianWidth);
        mTileView.addDetailLevel(1f, "tiles/map2/6/%d_%d_6.png", Constant.wapianWidth, Constant.wapianWidth);

        mTileView.addDetailLevel(2f, "tiles/map2/7/%d_%d_7.png", Constant.wapianWidth, Constant.wapianWidth);
        mTileView.addDetailLevel(4f, "tiles/map2/8/%d_%d_8.png", Constant.wapianWidth, Constant.wapianWidth);

        mTileView.addDetailLevel(8.0000f, "tiles/map2/9/%d_%d_9.png", Constant.wapianWidth, Constant.wapianWidth);
        if (zls != null && zls.size() > 0) {
            for (int i = 0; i < zls.size(); i++) {
                Logger.i("djfjksk" + 8.0000f * (Math.pow(2, (zls.get(i) - 9))));
                mTileView.addDetailLevel((float) (8.0000f * (Math.pow(2, (zls.get(i) - 9)))), "tiles/map2/9/%d_%d_9.png", Constant.wapianWidth, Constant.wapianWidth);
            }
        }

        mTileView.setBitmapProvider(new BitmapProviderAssets3());
        mTileView.setSize(Constant.TimesNeed * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.wapianWidth * (Constant.maxY5 - Constant.minY5 + 1));

        mTileView.setMarkerAnchorPoints(-0.5f, -1.0f);

        //获取编程DP指标
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Paint paint = mTileView.getDefaultPathPaint();

        double x = 0;
        double y = 0;
        for (double[] point : points) {
            x = x + point[0];
            y = y + point[1];
        }
        int size = points.size();
        x = x / size;
        y = y / size;
        frameTo(x, y);
        paint.setShadowLayer(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, metrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, metrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, metrics),
                0x66000000
        );
        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, metrics));
        paint.setPathEffect(
                new CornerPathEffect(
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, metrics)
                )
        );
        windowWidth = getResources().getDisplayMetrics().widthPixels;
        windowHeight = getResources().getDisplayMetrics().heightPixels;
        CirX = windowWidth / 2;
        CirY = windowHeight / 2;
        Canvas canvas = new Canvas();
        paint.setColor(Color.GREEN);
        canvas.drawCircle((float) CirX, (float) CirY, (float) 20 * 15, paint);

        mTileView.draw(canvas);

        if (zls != null && zls.size() > 0) {
            //试验高于1
            mTileView.setScaleLimits(0, (float) ((8f * (Math.pow(2, (zls.get(zls.size() - 1) - 9))))));
        } else {
            //试验高于1
            mTileView.setScaleLimits(0, 8f);
        }

        //启动小，允许变焦
        mTileView.setScale(0.5f);

        mTileView.addZoomPanListener(new ZoomPanLayout.ZoomPanListener() {
            @Override
            public void onPanBegin(int x, int y, Origination origin) {
                Logger.i("shgsdgB:" + x + "Y:" + y);
            }

            @Override
            public void onPanUpdate(int x, int y, Origination origin) {
                Logger.i("shgsdgU:" + x + "Y:" + y);
            }

            @Override
            public void onPanEnd(int x, int y, Origination origin) {
                Logger.i("shgsdgE:" + x + "Y:" + y);
            }

            @Override
            public void onZoomBegin(float scale, Origination origin) {
                Logger.i("sgsdg" + scale);
                startScale = mTileView.getDetailLevelManager().getScale();
            }

            @Override
            public void onZoomUpdate(float scale, Origination origin) {
                Logger.i("sgsdg2" + scale);
            }

            @Override
            public void onZoomEnd(float scale, Origination origin) {
                float maxScale = 16f;
                if (zls != null && zls.size() > 0) {
                    //试验高于1
                    maxScale = (float) (8f * (Math.pow(2, (zls.get(zls.size() - 1) - 9))));
                } else {
                    //试验高于1
                    maxScale = 8f;
                }
                Logger.i("sgsdg3" + scale);
                endScale = scale;
                if (startScale != -1 && (Constant.TimesNeed * startScale * 2) % 2 == 0) {
                    if ((endScale - startScale) >= 0.5 * startScale && startScale <= maxScale) {
                        float b = 2 * startScale;
                        getBilichi(b);
                    } else if ((startScale - endScale) >= (startScale / 2) && startScale >= 1) {
                        float b = startScale / 2;
                        getBilichi(b);
                    } else {
                        float b = startScale;
                        getBilichi(b);
                    }
                    startScale = -1;
                }

            }
        });
        float b = mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale();
        getBilichi(b);

        //用填充，我们可能是足够快，以创造一个无缝的图像的错觉
        mTileView.setViewportPadding(Constant.wapianWidth);

        //我们从资产，应该是相当快速的解码，去渲染ASAP
        mTileView.setShouldRenderWhilePanning(true);
        mTileView.setOnTouchListener((v, event) -> {
            float fraction = mTileView.getDetailLevelManager().getScale();
            Logger.d("getScalegjgj" + fraction);
            Logger.d("getPivotX" + mTileView.getPivotX() + "   y" + mTileView.getPivotY());
            Logger.d("getX" + mTileView.getX() + "   y" + mTileView.getY());
            Logger.d("getScaleX" + mTileView.getOffsetX() + "   y" + mTileView.getOffsetY());
            Logger.d("getLeft" + mTileView.getRight() + "   y" + mTileView.getBottom());
            Logger.d("getScaledHeight  x" + mTileView.getScaledHeight() + "   y" + mTileView.getScaledWidth());
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                xDown = event.getX();
                yDown = event.getY();
                Log.v("OnTouchListener", "Down");
            } else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
                //获取松开时的x坐标
                if (isLongClickModule) {
                    isLongClickModule = false;
                    isLongClicking = false;
                }
                xUp = event.getX();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //当滑动时背景为选中状态 //检测是否长按,在非长按时检测
                if (!isLongClickModule) {
                    isLongClickModule = ActionUtil.isLongPressed(xDown, yDown, event.getX(),
                            event.getY(), event.getDownTime(), event.getEventTime(), 300);
                    if (viewLL != null) {
                        mTileView.getMarkerLayout().removeMarker(viewLL);
                        viewLL = null;
                    }
                }
                if (isLongClicking) {
                    isLongClicking = true;
                    viewLL = getLayoutInflater().inflate(R.layout.home_map_fav_float, null);
                    ImageView iv = (ImageView) viewLL.findViewById(R.id.iv_close);
                    iv.setOnClickListener(v1 -> {
                        if (viewLL != null) {
                            mTileView.getMarkerLayout().removeMarker(viewLL);
                            viewLL = null;
                        }
                    });
                    float scale2 = mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale();
                    int zoom = 5;
                    if (mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 0.5) {
                        zoom = 5;
                    } else if (mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 1.0) {
                        zoom = 6;
                    } else if (mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 2.0) {
                        zoom = 7;
                    } else if (mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 4.0) {
                        zoom = 8;
                    } else if (mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 8.0) {
                        zoom = 9;
                    }
                    if (zls != null && zls.size() > 0) {
                        for (int i = 0; i < zls.size(); i++) {
                            if (mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == (float) (8.0000f * (Math.pow(2, (zls.get(i) - 9))))) {
                                zoom = zls.get(i);
                            }
                        }
                    }
                    TextView wd = (TextView) viewLL.findViewById(R.id.tv_wd);
                    TextView jd = (TextView) viewLL.findViewById(R.id.tv_jd);
                    wd_d = (GetLongLati.pixelToLat(((((v.getScrollY() + event.getY()) / (Constant.WpTimes + 0.0)) / (fraction / scale2) + (Constant.minY5 * (256) * Constant.TimesNeed * scale2))), zoom));
                    jd_d = (GetLongLati.pixelToLng(((((v.getScrollX() + event.getX()) / (Constant.WpTimes + 0.0)) / (fraction / scale2) + Constant.minX5 * (256) * Constant.TimesNeed * scale2)), zoom));
                    Logger.i("dskdsgkg" + (v.getScrollY() + event.getY()) + "dsh" + (v.getScrollX() + event.getX()) + "sg" + scale2 + "bb" + zoom + "ffg" + wd_d + "dsg" + jd_d);

                    wd.setText("纬度:" + ConvertUtils.Latitude(wd_d));
                    jd.setText("经度:" + ConvertUtils.Longitude(jd_d));

                    TileView tile = mTileView;
                    MarkerLayout.LayoutParams layout = new MarkerLayout.LayoutParams(40, 100);
                    viewLL.setLayoutParams(layout);
                    Logger.i("sgssgs" + Constant.WpTimes * (GetLongLati.getY(wd_d, 5)) + "sgsg" + (Constant.WpTimes * (GetLongLati.getY(wd_d, 5) - Constant.minY5 * 256)) + "afa" + GetLongLati.getY(wd_d, 5) + "dfhh" + GetLongLati.getY(wd_d, 6) + "dsg" + GetLongLati.getX(jd_d, 5) + "dfsg" + GetLongLati.getY(wd_d, 6) + "asdgs" + GetLongLati.getX(jd_d, 6));

                    double poinhx = DecimalCalculate.add(v.getScrollX(), event.getX());
                    double poinhy = DecimalCalculate.add(v.getScrollY(), event.getY());
                    double poinhxx = DecimalCalculate.div(poinhx, scale2);
                    double poinhyy = DecimalCalculate.div(poinhy, scale2);

                    double[] point = {poinhxx, poinhyy};
                    int pointIntX = (int) poinhxx;
                    int pointIntY = (int) poinhyy;
                    double sx = poinhxx - pointIntX;
                    double sy = poinhyy - pointIntY;
                    for (View view3 : ls_view_mark) {
                        mTileView.getMarkerLayout().removeMarker(view3);
                    }
                    ls_view_mark.clear();

                    ls_view_mark.add(viewLL);
                    double k = (sx * scale2) / (550 + 0f);
                    double k2 = (sy * scale2) / (180 + 0f);
                    float kh = (float) (-0.5 + k);
                    float khy = (float) (-1 + k2);
                    tile.addMarker(viewLL, point[0], point[1], kh, khy);
                    if (scale2 <= 64) {
                        frameToWithScale(point[0], point[1], scale2);
                    }
                }
            }
            return false;
        });
        mTileView.setGetScaleChangedListener(scale -> {
            for (CompositePathView.DrawablePath sp2 : yuan) {
                mTileView.getCompositePathView().removePath(sp2);
            }
        });
    }

    private void getBilichi(float b) {
        mTileView.setScaleFromCenter(b);
        double z = Math.log(Constant.TimesNeed * b) / Math.log(2);
        double s = Constant.gongliMaxBl / Math.pow(2, z);
        double[] point = {(120 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.WpTimes * (GetLongLati.getY(24, 5) - Constant.minY5 * 256)};
        double jd2 = LocationUtils.doLngDegress((long) (1852 * Constant.gongliMaxBl / 1000), 24);
        double x2 = ((jd2 + 120) - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1);
        double s2 = Math.abs(x2 - point[0]);
        ViewGroup.LayoutParams lp = mMainViewBilichi
                .getLayoutParams();
        lp.width = (int) (s2 / 2);
        mMainViewBilichi.setLayoutParams(lp);
        DecimalFormat df3 = new DecimalFormat("##0");
        mMainTextBilichi.setText("" + df3.format(s) + "海里");
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        BitmapFactory.Options options = new BitmapFactory.Options();
//
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        options.inPurgeable = true;// 允许可清除

        options.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果

        options.inSampleSize = 2;

        super.onResume();
        mTileView.resume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        AlxLocationManager.onCreateGPS(getApplication(), this);
        mTimer_Gps = new Timer();
        mTimer_Gps.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AlxLocationService.setGpsInfoListener(location -> {
//                    Logger.i(location.toString());
                    if (location != null) {
                        String lat = MathUtils.convertToSexagesimal(location.latitude);
                        String lng = MathUtils.convertToSexagesimal(location.longitude);
                        runOnUiThread(() -> {
                            mMainJingdu.setText(lat);
                            mMainWeidu.setText(lng);
                            mMainHangsu.setText(location.speed + "");
                        });
                    }
                });
            }
        }, 0, 5000);
    }

    @Override
    public void finish() {
        super.finish();
    }

    // 点击返回 回到home界面
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);

            home.addCategory(Intent.CATEGORY_HOME);

            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void frameTo(final double x, final double y) {
        mTileView.post(() -> mTileView.scrollToAndCenter(x, y));
    }

    public void frameToWithScale(final double x, final double y, final float scale) {
        mTileView.post(() -> mTileView.slideToAndCenterWithScale(x, y, scale));

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ais) {
            enterFourPageActivity(GlobalManager.ENTERTYPE_AIS);
            // Handle the camera action
        } else if (id == R.id.nav_gps) {
            enterFourPageActivity(GlobalManager.ENTERTYPE_GPS);
        } else if (id == R.id.nav_compass) {
            enterFourPageActivity(GlobalManager.ENTERTYPE_COMPASS);
        } else if (id == R.id.nav_setting) {
            enterFourPageActivity(GlobalManager.ENTERTYPE_SETTING);

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_update) {

        } else if (id == R.id.nav_backup) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void enterFourPageActivity(String entertype) {
        ViewPageActivity.actionStart(mContext, entertype);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTileView.pause();
        if (mTimer_Gps != null) {
            mTimer_Gps.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTileView.destroy();

//        AlxLocationManager.stopGPS();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_sousuo:
                searcher(v);
                break;
            case R.id.main_celiang:

                break;
            case R.id.main_daohang:

                break;
            case R.id.main_shoucang:

                break;
            case R.id.main_jia:

                break;
            case R.id.main_jian:

                break;
            case R.id.main_dingwei:

                break;
            case R.id.main_text_info_dialog:

                break;

        }
    }

    private void searcher(View view) {

        final SelfDialog dialog2 = new SelfDialog(this);

        dialog2.setNoOnclickListener(null, () -> dialog2.dismiss());
        dialog2.setYesOnclickListener(null, () -> {
            String wd = dialog2.getEt_w_d().getText().toString();
            String wf = dialog2.getEt_w_f().getText().toString();
            String wm = dialog2.getEt_w_m().getText().toString();
            String jd = dialog2.getEt_j_d().getText().toString();
            String jf = dialog2.getEt_j_f().getText().toString();
            String jm = dialog2.getEt_j_m().getText().toString();
            if (TextUtils.isEmpty(wd)) {
                Toast.makeText(MainActivity.this, "纬度不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(jd)) {
                Toast.makeText(MainActivity.this, "经度不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (Math.abs(Integer.valueOf(wd)) > 90) {
                Toast.makeText(MainActivity.this, "纬度范围在90到-90,请重输", Toast.LENGTH_SHORT).show();
                return;
            } else if (Math.abs(Integer.valueOf(jd)) > 180) {
                Toast.makeText(MainActivity.this, "经度范围在180到-180,请重输", Toast.LENGTH_SHORT).show();
                return;
            } else if (Math.abs(Integer.valueOf(jd)) == 180) {
                if (!TextUtils.isEmpty(jm) || !TextUtils.isEmpty(jf)) {
                    Toast.makeText(MainActivity.this, "经度范围在180到-180,请重输", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (Math.abs(Integer.valueOf(wd)) == 90) {
                if (!TextUtils.isEmpty(wm) || !TextUtils.isEmpty(wf)) {
                    Toast.makeText(MainActivity.this, "纬度范围在90到-90,请重输", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            double dwd;
            double djd;
            if (TextUtils.isEmpty(wf)) {
                dwd = Double.parseDouble(wd);

            } else if (TextUtils.isEmpty(wm)) {
                dwd = Double.parseDouble(wd) + Double.parseDouble(wf) / (60 + 0.0);
            } else {
                dwd = Double.parseDouble(wd) + Double.parseDouble(wf) / (60 + 0.0) + Double.parseDouble(wm) / (3600 + 0.0);
            }
            if (TextUtils.isEmpty(jf)) {
                djd = Double.parseDouble(jd);
            } else if (TextUtils.isEmpty(jm)) {
                djd = Double.parseDouble(jd) + Double.parseDouble(jf) / (60 + 0.0);
            } else {
                djd = Double.parseDouble(jd) + Double.parseDouble(jf) / (60 + 0.0) + Double.parseDouble(jm) / (3600 + 0.0);
            }

            frameToWithScale(Constant.TimesNeed * (djd - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(dwd, 5) - Constant.minY5 * 256), 0.5f);
            viewLL = getLayoutInflater().inflate(R.layout.home_map_fav_float, null);
//                       float fraction=tileView.getDetailLevelManager().getScale();
            ImageView iv = (ImageView) viewLL.findViewById(R.id.iv_close);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TileView tileView = getTileView();
                    if (viewLL != null) {
                        tileView.getMarkerLayout().removeMarker(viewLL);
                        viewLL = null;
                    }
                }
            });
            viewLL.setOnClickListener(v -> {

                cdDialog = new CollectDialog(MainActivity.this);
                cdDialog.setNoOnclickListener(null, new CollectDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        cdDialog.dismiss();

                    }
                });
                cdDialog.setYesOnclickListener(null, new CollectDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        String name = cdDialog.getEt_name().getText().toString();
                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(MainActivity.this, "航点名不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        DBManager db = new DBManager(MainActivity.this);
                        CollectPointBean cp = db.getCollect(name);
                        if (cp != null) {
                            Toast.makeText(MainActivity.this, "航点名已存在，请重新命名", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        CollectPointBean a = new CollectPointBean();
                        a.setLongitude(jd_d);
                        a.setLatitude(wd_d);
                        a.setName(name);
                        a.setType(0);
                        db.addCollectPoint(a);
                        cdDialog.dismiss();
                        Toast.makeText(MainActivity.this, "航点收藏成功", Toast.LENGTH_SHORT).show();
                    }
                });
//                               Toast.makeText(MainActivity.this,"加入收藏",Toast.LENGTH_SHORT).show();

                cdDialog.show();


            });
            float scale2 = tileView.getDetailLevelManager().getCurrentDetailLevel().getScale();
            int zoom = 5;
            if (tileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 0.5) {
                zoom = 5;
            } else if (tileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 1) {
                zoom = 6;
            } else if (tileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 2.0) {
                zoom = 7;
            } else if (tileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 4.0) {
                zoom = 8;
            } else if (tileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == 8.0) {
                zoom = 9;
            }
            if (zls != null && zls.size() > 0) {
                for (int i = 0; i < zls.size(); i++) {
                    if (tileView.getDetailLevelManager().getCurrentDetailLevel().getScale() == (float) (8.0000f * (Math.pow(2, (zls.get(i) - 9))))) {
                        zoom = zls.get(i);
                    }
                }
            }
            TextView wd2 = (TextView) viewLL.findViewById(R.id.tv_wd);
            TextView jd2 = (TextView) viewLL.findViewById(R.id.tv_jd);
            wd2.setText("纬度:" + ConvertUtils.Latitude(dwd));
            jd2.setText("经度:" + ConvertUtils.Longitude(djd));

            TileView tile = getTileView();
            MarkerLayout.LayoutParams layout = new MarkerLayout.LayoutParams(40, 100);
            viewLL.setLayoutParams(layout);
            double[] point = {(djd - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), Constant.WpTimes * (GetLongLati.getY(dwd, 5) - Constant.minY5 * 256)};
            for (View view3 : ls_view_mark) {
                tileView.getMarkerLayout().removeMarker(view3);
            }
            ls_view_mark.clear();
            ls_view_mark.add(viewLL);
            tile.addMarker(viewLL, Constant.TimesNeed * point[0], Constant.TimesNeed * point[1], -0.5f, -1f);

            dialog2.dismiss();
        });

        dialog2.show();

    }
}
}
