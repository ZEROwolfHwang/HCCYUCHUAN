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
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
import com.ruitong.yuchuan.maptest.bean.PointInfoBean;
import com.ruitong.yuchuan.maptest.dialog.CollectDialog;
import com.ruitong.yuchuan.maptest.dialog.SaveLineDialog;
import com.ruitong.yuchuan.maptest.dialog.SelfDialog;
import com.ruitong.yuchuan.maptest.gps.AlxLocationManager;
import com.ruitong.yuchuan.maptest.gps.AlxLocationService;
import com.ruitong.yuchuan.maptest.helper.MainHelper;
import com.ruitong.yuchuan.maptest.litepalbean.SupportLine;
import com.ruitong.yuchuan.maptest.litepalbean.SupportLinePoint;
import com.ruitong.yuchuan.maptest.litepalbean.SupportPoint;
import com.ruitong.yuchuan.maptest.manager.GlobalManager;
import com.ruitong.yuchuan.maptest.map.BitmapProviderAssets3;
import com.ruitong.yuchuan.maptest.map.Constant;
import com.ruitong.yuchuan.maptest.tools.ActionBarTool;
import com.ruitong.yuchuan.maptest.tools.ToastUtils;
import com.ruitong.yuchuan.maptest.utils.ConvertUtils;
import com.ruitong.yuchuan.maptest.utils.DecimalCalculate;
import com.ruitong.yuchuan.maptest.utils.GetLongLati;
import com.ruitong.yuchuan.maptest.utils.LocationUtils;
import com.ruitong.yuchuan.maptest.utils.MathUtils;
import com.ruitong.yuchuan.maptest.utils.PermissionUtils;
import com.ruitong.yuchuan.maptest.utils.TimeUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ruitong.yuchuan.maptest.map.Constant.TimesNeed;
import static com.ruitong.yuchuan.maptest.utils.ActionUtil.isLongPressed;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, View.OnTouchListener {

    private static final String TAG = "MainActivity";
    private static final String ACTION_COLLECT_TO_MAIN = "action_collect_to_main";
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
    @BindView(R.id.main_text_distance)
    TextView mMainTextDistance;
    @BindView(R.id.ll_main_distance)
    LinearLayout mLlMainDistance;
    @BindView(R.id.ll_main_tool_main)
    LinearLayout mLlMainToolMain;
    @BindView(R.id.main_distance_sousuo)
    TextView mMainDistanceSousuo;
    @BindView(R.id.main_chexiao)
    TextView mMainChexiao;
    @BindView(R.id.main_qingkong)
    TextView mMainQingkong;
    @BindView(R.id.ll_main_tool_distance)
    LinearLayout mLlMainToolDistance;
    @BindView(R.id.main_nav_point)
    ImageView mMainNavPoint;
    @BindView(R.id.main_nav_line)
    ImageView mMainNavLine;
    @BindView(R.id.main_nav_save)
    ImageView mMainNavSave;
    @BindView(R.id.main_nav_sousuo)
    TextView mMainNavSousuo;
    @BindView(R.id.main_nav_start)
    TextView mMainNavStart;
    @BindView(R.id.main_nav_chexiao)
    TextView mMainNavChexiao;
    @BindView(R.id.main_nav_qingkong)
    TextView mMainNavQingkong;
    @BindView(R.id.ll_main_tool_navigation)
    LinearLayout mLlMainToolNavigation;
    @BindView(R.id.ll_main_util_navigation)
    LinearLayout mLlMainUtilNavigation;
    @BindView(R.id.main_appbar)
    AppBarLayout mMainAppbar;


    //显示标记和路径的点列表
    private ArrayList<double[]> points = new ArrayList<>();
    private ArrayList<Double[]> mJingWeiPoints;
    private ArrayList<double[]> mPointList;
    private ArrayList<PointInfoBean> mPointInfoList;
    private int mPageTag;
    private Bitmap mDistanceCircleRed;
    private Paint mDistancePathPaint;
    private double mAllDistance;
    private int mActionBarHeight;
    private ViewGroup.LayoutParams mAppBarParams;
    private TextView mTitleTextView;

    private String mSupport_line_name;
    private String mSupport_point_name;
    private int mCollect2MainTag;
    private String mIntentType;
    private List<SupportPoint> mSupportPointList;
    private double mLocationJd;
    private double mLocationWd;
    private ArrayList<double[]> mAll_points;


    {
        points.add(new double[]{TimesNeed * (120.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), TimesNeed * Constant.WpTimes * (GetLongLati.getY(24, 5) - Constant.minY5 * 256)});
        points.add(new double[]{TimesNeed * (118.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), TimesNeed * Constant.WpTimes * (GetLongLati.getY(23, 5) - Constant.minY5 * 256)});
        points.add(new double[]{TimesNeed * (116.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), TimesNeed * Constant.WpTimes * (GetLongLati.getY(22, 5) - Constant.minY5 * 256)});
        points.add(new double[]{TimesNeed * (120.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), TimesNeed * Constant.WpTimes * (GetLongLati.getY(23.5, 5) - Constant.minY5 * 256)});
        points.add(new double[]{TimesNeed * (118.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), TimesNeed * Constant.WpTimes * (GetLongLati.getY(23.2, 5) - Constant.minY5 * 256)});
        points.add(new double[]{TimesNeed * (116.0 - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), TimesNeed * Constant.WpTimes * (GetLongLati.getY(22.3, 5) - Constant.minY5 * 256)});

    }

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

    public static void actionStart(Context context, String name, String type) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setType(type);
        intent.setAction(ACTION_COLLECT_TO_MAIN);
        if (GlobalManager.LINE_NAME.equals(type)) {
            intent.putExtra(GlobalManager.LINE_NAME, name);
        } else {
            intent.putExtra(GlobalManager.POINT_NAME, name);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mActivity = this;

        LitePal.getDatabase();
        mPageTag = 0;
        mCollect2MainTag = 0;
        PermissionUtils.verifyStoragePermissions(mActivity);
        mContext = this;
        initToolbar();
        mTileView.setSaveEnabled(true);
        initTileView();
        getIntentData();
        initData();
        initView();


    }

    /**
     * 拿到从收藏中点击定位得到的点或者线
     */
    private void getIntentData() {
        Intent intent = getIntent();
        if (ACTION_COLLECT_TO_MAIN.equals(intent.getAction())) {
            mCollect2MainTag = 1;
            mIntentType = intent.getType();
            if (GlobalManager.LINE_NAME.equals(mIntentType)) {
                mSupport_line_name = intent.getStringExtra(GlobalManager.LINE_NAME);

            } else {
                mSupport_point_name = intent.getStringExtra(GlobalManager.POINT_NAME);

            }
        }
    }

    private void initData() {
        mPointList = new ArrayList<>();
        mJingWeiPoints = new ArrayList<>();
        mPointInfoList = new ArrayList<>();
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

        //若从收藏界面进入则需要初始化点或者线
        if (mCollect2MainTag == 1) {
            if (GlobalManager.LINE_NAME.equals(mIntentType)) {
                List<SupportLinePoint> supportLinePoints = DataSupport.where("line_name = ?", mSupport_line_name).find(SupportLinePoint.class);
                navigationMap();
                if (supportLinePoints.size() != 0) {
                    if (mPointList == null) {
                        mPointList = new ArrayList<>();
                    } else {
                        mPointList.clear();
                    }
                    if (mJingWeiPoints == null) {
                        mJingWeiPoints = new ArrayList<>();
                    } else {
                        mJingWeiPoints.clear();
                    }
                    for (int i = 0; i < supportLinePoints.size(); i++) {
                        double[] doubles = {MathUtils.getLocation_JD(supportLinePoints.get(i).getLongitude()), MathUtils.getLocation_WD(supportLinePoints.get(i).getLatitude())};
                        Double[] jingwei = new Double[]{supportLinePoints.get(i).getLongitude(), supportLinePoints.get(i).getLatitude()};
                        mPointList.add(doubles);
                        mJingWeiPoints.add(jingwei);
                    }
                    drawLinePoint(mPointList);
                    toMyLocation();
                }
            } else {
                mSupportPointList = DataSupport.where("point_name = ?", mSupport_point_name).find(SupportPoint.class);
            }
        }
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
                mTileView.addDetailLevel((float) (8.0000f * (Math.pow(2, (zls.get(i) - 9)))), "tiles/map2/9/%d_%d_9.png", Constant.wapianWidth, Constant.wapianWidth);
            }
        }

        mTileView.setBitmapProvider(new BitmapProviderAssets3());
        mTileView.setSize(TimesNeed * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1), TimesNeed * Constant.wapianWidth * (Constant.maxY5 - Constant.minY5 + 1));

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
                if (startScale != -1 && (TimesNeed * startScale * 2) % 2 == 0) {
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

        mTileView.setOnTouchListener(this);
        //  mTileView.setOnLongClickListener(this);

        mTileView.setGetScaleChangedListener(scale -> {
            for (CompositePathView.DrawablePath sp2 : yuan) {
                mTileView.getCompositePathView().removePath(sp2);
            }
        });
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
                    if (location != null) {
                        String lat = MathUtils.convertToSexagesimal(location.latitude);
                        String lng = MathUtils.convertToSexagesimal(location.longitude);
                        runOnUiThread(() -> {
                            mMainJingdu.setText(lng);
                            mMainWeidu.setText(lat);
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


        TypedValue tv = new TypedValue();
        if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            mActionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        mAppBarParams = mMainAppbar.getLayoutParams();

        mTitleTextView = ActionBarTool.getInstance(this, 991).getTitle_text_view();

        toolbar.setNavigationIcon(R.drawable.back_up_logo);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mPageTag != 0) {
            if (mPageTag == 1) {
                mLlMainDistance.setVisibility(View.GONE);
                mLlMainToolDistance.setVisibility(View.GONE);

            } else if (mPageTag == 2) {

                mLlMainToolNavigation.setVisibility(View.GONE);
                mLlMainUtilNavigation.setVisibility(View.GONE);
            }
            mMainTextInfoDialog.setVisibility(View.VISIBLE);
            mLlMainToolMain.setVisibility(View.VISIBLE);

            distanceClear();
            mAppBarParams.height = 0;
            mPageTag = 0;
            mCollect2MainTag = 0;
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
                searcher();
                break;
            case R.id.main_celiang:
                measureMap();
                break;
            case R.id.main_daohang:
                navigationMap();
                break;
            case R.id.main_shoucang:
                Intent intent = new Intent(this, CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.main_jia:
                expandView();
                break;
            case R.id.main_jian:
                shrinkView();
                break;
            case R.id.main_dingwei:
                toMyLocation();
                break;
            case R.id.main_text_info_dialog:

                break;
        }
    }

    /**
     * 点击导航按钮过后进入导航
     */
    private void navigationMap() {
        mPageTag = 2;
        mTitleTextView.setText("导航");
        mAppBarParams.height = mActionBarHeight;

        mMainTextInfoDialog.setVisibility(View.GONE);
        mLlMainToolMain.setVisibility(View.GONE);

        mLlMainToolNavigation.setVisibility(View.VISIBLE);
        mLlMainUtilNavigation.setVisibility(View.VISIBLE);

        if (mDistanceCircleRed == null) {
            mDistanceCircleRed = MainHelper.readBitmap(MainActivity.this, R.drawable.yuand3);
        }
        if (mDistancePathPaint == null) {
            mDistancePathPaint = mTileView.getDefaultPathPaint();
        }

        mMainNavSousuo.setOnClickListener(v -> {
            searcher();
        });

        mMainNavSave.setOnClickListener(v -> {
            if (mCollect2MainTag == 1) {
                ToastUtils.singleToast("该航线已保存");
            } else {
                SaveLineDialog saveLineDialog = new SaveLineDialog(this);
                saveLineDialog.setNoOnclickListener(null, () ->
                        saveLineDialog.dismiss()
                );
                saveLineDialog.setYesOnclickListener(null, () -> {
                    String line_name = saveLineDialog.getEt_name().getText().toString();
                    if (!TextUtils.isEmpty(line_name)) {
                        List<SupportLine> supportLines = DataSupport.where("line_name = ?", line_name).find(SupportLine.class);
                        if (supportLines.size() == 0) {
                            String currentTimeToDate = TimeUtil.getCurrentTimeToDate();
                            SupportLine supportLine = new SupportLine();
                            supportLine.setLine_time(currentTimeToDate);
                            ArrayList<SupportLinePoint> linePoints = new ArrayList<>();
                            for (int i = 0; i < mJingWeiPoints.size(); i++) {
                                SupportLinePoint linePoint = new SupportLinePoint();
                                linePoint.setLine_name(line_name);
                                linePoint.setLongitude(mJingWeiPoints.get(i)[0]);
                                linePoint.setLatitude(mJingWeiPoints.get(i)[1]);
                                linePoint.setPoint_name((i+1)+"");
                                linePoint.setPosition(i + 1);
                                linePoint.save();
                                linePoints.add(linePoint);
                            }
                            supportLine.setLinePointList(linePoints);
                            supportLine.setLine_name(line_name);
                            supportLine.save();
                            saveLineDialog.dismiss();
                            ToastUtils.singleToast("保存成功");
                        } else {
                            ToastUtils.singleToast("该航线名已存在");
                        }
                    } else {
                        ToastUtils.singleToast("航线名不能为空");
                    }
                });
                saveLineDialog.show();
            }
        });
        mMainNavPoint.setOnClickListener(v -> {
            CollectActivity.actionStart(this, GlobalManager.ENTERTYPE_POINT);
        });
        mMainNavLine.setOnClickListener(v -> {
            CollectActivity.actionStart(this, GlobalManager.ENTERTYPE_LINE);
        });
        mMainNavStart.setOnClickListener(v -> {
            mTileView.getMarkerLayout().removeAllViews();
            mTileView.getCompositePathView().clear();
            toMyLocation();
            drawLinePoint(mPointList);

            if (mAll_points == null) {
                mAll_points = new ArrayList<>();
            } else {
                mAll_points.clear();
            }
            mAll_points.add(new double[]{mLocationJd, mLocationWd});
            mAll_points.addAll(mPointList);
            mTileView.drawPath2(mAll_points.subList(0, mAll_points.size()), mDistancePathPaint);
        });
        mMainNavChexiao.setOnClickListener(v -> {
            if (mCollect2MainTag == 1) {
                //从航线列表中直接导入
                ToastUtils.singleToast("收藏航线无法撤销");
            } else {
                //点击主界面导航进入
                if (mPointList.size() != 0) {
                    mTileView.getMarkerLayout().removeAllViews();
                    mTileView.getCompositePathView().clear();
                    mPointList.remove(mPointList.size() - 1);
                    mJingWeiPoints.remove(mJingWeiPoints.size() - 1);
                    if (mPointInfoList.size() != 0) {
                        mPointInfoList.remove(mPointInfoList.size() - 1);
                    }
                    drawLinePoint(mPointList);
                }
            }
        });
        mMainNavQingkong.setOnClickListener(v -> {
            if (mCollect2MainTag == 1) {
                ToastUtils.singleToast("收藏的航线不可以清空");
            } else {
                distanceClear();
            }
        });

    }

    /**
     * 绘制航线上所有的点
     */
    private void drawLinePoint(ArrayList<double[]> mPointList) {
        for (int i = 0; i < mPointList.size(); i++) {
            double[] point = {mPointList.get(i)[0], mPointList.get(i)[1]};
            int pointIntX = (int) point[0];
            int pointIntY = (int) point[1];
            double sx = point[0] - pointIntX;
            double sy = point[1] - pointIntY;
            drawPoint(i, sx, sy);
            TextView textView = new TextView(MainActivity.this);
            textView.setText((i + 1) + "");
            textView.setTextColor(Color.BLACK);
            double k = (sx * mTileView.getDetailLevelManager().getScale()) / (120 + 0f);
            double k2 = (sy * mTileView.getDetailLevelManager().getScale()) / (80 + 0f);
            float kh = (float) (-0.5 + k);
            float khy = (float) (-0.5 + k2);
            mTileView.addMarker(textView, mPointList.get(i)[0], mPointList.get(i)[1], kh, khy);
        }
    }

    /**
     * 测量地图距离
     */

    private void measureMap() {
        mPageTag = 1;
        mTitleTextView.setText("测量");
        mAppBarParams.height = mActionBarHeight;

        mMainTextInfoDialog.setVisibility(View.GONE);
        mLlMainToolMain.setVisibility(View.GONE);

        mLlMainDistance.setVisibility(View.VISIBLE);
        mLlMainToolDistance.setVisibility(View.VISIBLE);
        if (mDistanceCircleRed == null) {
            mDistanceCircleRed = MainHelper.readBitmap(MainActivity.this, R.drawable.yuand3);
        }
        if (mDistancePathPaint == null) {
            mDistancePathPaint = mTileView.getDefaultPathPaint();
        }


        mMainDistanceSousuo.setOnClickListener(v -> {
            searcher();
        });
        mMainQingkong.setOnClickListener(v -> {
            distanceClear();
        });
        mMainChexiao.setOnClickListener(v -> {
            if (mPointList.size() != 0) {
                mTileView.getMarkerLayout().removeAllViews();
                mTileView.getCompositePathView().clear();
                mPointList.remove(mPointList.size() - 1);
                mJingWeiPoints.remove(mJingWeiPoints.size() - 1);
                if (mPointInfoList.size() != 0) {
                    mPointInfoList.remove(mPointInfoList.size() - 1);
                }
                if (mPointList.size() == 1) {
                    mMainTextDistance.setText("0.00海里");
                    mAllDistance = 0;
                }
                mAllDistance = 0;
                for (int i = 0; i < mPointList.size(); i++) {
                    double[] point = {mPointList.get(i)[0], mPointList.get(i)[1]};
                    int pointIntX = (int) point[0];
                    int pointIntY = (int) point[1];
                    double sx = point[0] - pointIntX;
                    double sy = point[1] - pointIntY;
                    if (i == 0) {
                        drawPoint(i, sx, sy);
                    } else {
                        drawPoint(i, sx, sy);
                        TextView textView = new TextView(MainActivity.this);
                        double[] doub1 = mPointList.get(i - 1);
                        double[] doub2 = mPointList.get(i);
                        double fwj = LocationUtils.gps2d(doub1[1], doub1[0], doub2[1], doub2[0]);
                        DecimalFormat df3 = new DecimalFormat("##0.00");
                        PointInfoBean pointInfoBean = mPointInfoList.get(i - 1);
                        mAllDistance = mAllDistance + pointInfoBean.getDistance();
                        mMainTextDistance.setText(df3.format(mAllDistance) + "海里");
                        if (fwj < 0) {
                            textView.setText("" + df3.format(pointInfoBean.getDistance()) + "海里" + "\n" + df3.format(pointInfoBean.getFangwei()) + "°");
                        } else if (fwj >= 0) {
                            textView.setText("" + df3.format(pointInfoBean.getDistance()) + "海里" + "\n" + df3.format(pointInfoBean.getFangwei()) + "°");
                        }
                        textView.setTextColor(Color.BLACK);
                        double k = (sx * mTileView.getDetailLevelManager().getScale()) / (120 + 0f);
                        double k2 = (sy * mTileView.getDetailLevelManager().getScale()) / (80 + 0f);
                        float kh = (float) (-0.5 + k);
                        float khy = (float) (-0.5 + k2);
                        mTileView.addMarker(textView, mPointList.get(i)[0], mPointList.get(i)[1], kh, khy);
                        mTileView.drawPath2(mPointList.subList(0, mPointList.size()), mDistancePathPaint);
                    }
                }
            }
        });
    }

    /**
     * 在测距的界面内绘制点
     */
    private void drawPoint(int i, double sx, double sy) {
        ImageView marker = new ImageView(MainActivity.this);
        marker.setTag(mPointList.get(i));
        marker.setImageBitmap(mDistanceCircleRed);

        double k = (sx * mTileView.getDetailLevelManager().getScale()) / (40 + 0f);
        double k2 = (sy * mTileView.getDetailLevelManager().getScale()) / (40 + 0f);
        float kh = (float) (-0.5 + k);
        float khy = (float) (-0.5 + k2);
        mTileView.addMarker(marker, mPointList.get(i)[0], mPointList.get(i)[1], kh, khy);
    }

    /**
     * 清空测距的相关参数
     */

    private void distanceClear() {
        mTileView.getMarkerLayout().removeAllViews();
        mTileView.getCompositePathView().clear();
        mPointList.clear();
        mJingWeiPoints.clear();
        mPointInfoList.clear();
        if (mPageTag == 1) {
            mMainTextDistance.setText("0.00海里");
            mAllDistance = 0;
        }
    }

    /**
     * 定位到当前的位置
     */
    private void toMyLocation() {
        AlxLocationService.setGpsInfoListener(location -> {
            if (location != null && location.getLatitude() != 0) {
                double jd = location.getLongitude();
                double wd = location.getLatitude();
                mLocationJd = MathUtils.getLocation_JD(jd);
                mLocationWd = MathUtils.getLocation_WD(wd);
                frameToWithScale(mLocationJd, mLocationWd,
                        mTileView.getDetailLevelManager().getScale());

                View view_location = getLayoutInflater().inflate(R.layout.layout_location, null);
                MarkerLayout.LayoutParams layout = new MarkerLayout.LayoutParams(40, 100);
                view_location.setLayoutParams(layout);

                mTileView.addMarker(view_location, mLocationJd, mLocationWd, -0.5f, -1f);

            } else {
                ToastUtils.singleToast("当前没有定位或正在定位中...");
            }
        });
    }

    /**
     * 放大地图界面
     */
    private void expandView() {
        float b = mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale();
        if (zls != null && zls.size() > 0) {
            if (b <= 8f * (Math.pow(2, (zls.get(zls.size() - 1)) - 9 - 1))) {
                ScaleAnimation myAnimation_Scale = new ScaleAnimation(0.5f, 0.8f, 0.5f, 0.8f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mTileView.setAnimation(myAnimation_Scale);
                mTileView.setAnimationDuration(800);
                mTileView.smoothScaleFromCenter(b * 2);

                getBilichi(b);
            }
        } else {
            if (b <= 4) {
                ScaleAnimation myAnimation_Scale = new ScaleAnimation(0.5f, 0.8f, 0.5f, 0.8f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mTileView.setAnimation(myAnimation_Scale);
                mTileView.setAnimationDuration(800);
                mTileView.smoothScaleFromCenter(b * 2);

                getBilichi(b);
            }
        }
    }

    /**
     * 缩小地图界面
     */
    private void shrinkView() {
        float b = mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale();

        if (b > 0.5) {
            ScaleAnimation myAnimation_Scale = new ScaleAnimation(1f, 0.1f, 1f, 0.1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mTileView.setAnimation(myAnimation_Scale);
            mTileView.setAnimationDuration(800);
            mTileView.smoothScaleFromCenter(b / 2);

            getBilichi(b);
        }
    }

    /**
     * 点击搜索按钮
     */
    private void searcher() {

        final SelfDialog dialog2 = new SelfDialog(this);

        dialog2.setNoOnclickListener(null, () -> dialog2.dismiss());
        dialog2.setYesOnclickListener(null, () -> {
            String w_du = dialog2.getEt_w_d().getText().toString();
            String w_fen = dialog2.getEt_w_f().getText().toString();
            String w_miao = dialog2.getEt_w_m().getText().toString();
            String j_du = dialog2.getEt_j_d().getText().toString();
            String j_fen = dialog2.getEt_j_f().getText().toString();
            String j_miao = dialog2.getEt_j_m().getText().toString();
            if (TextUtils.isEmpty(w_du)) {
                Toast.makeText(MainActivity.this, "纬度不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(j_du)) {
                Toast.makeText(MainActivity.this, "经度不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Math.abs(Integer.valueOf(w_du)) > 90) {
                Toast.makeText(MainActivity.this, "纬度范围在90到-90,请重输", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Math.abs(Integer.valueOf(j_du)) > 180) {
                Toast.makeText(MainActivity.this, "经度范围在180到-180,请重输", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Math.abs(Integer.valueOf(j_du)) == 180) {
                if (!TextUtils.isEmpty(j_miao) || !TextUtils.isEmpty(j_fen)) {
                    Toast.makeText(MainActivity.this, "经度范围在180到-180,请重输", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (Math.abs(Integer.valueOf(w_du)) == 90) {
                if (!TextUtils.isEmpty(w_miao) || !TextUtils.isEmpty(w_fen)) {
                    Toast.makeText(MainActivity.this, "纬度范围在90到-90,请重输", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            double weidu;
            double jingdu;
            if (TextUtils.isEmpty(w_fen)) {
                weidu = Double.parseDouble(w_du);

            } else if (TextUtils.isEmpty(w_miao)) {
                weidu = Double.parseDouble(w_du) + Double.parseDouble(w_fen) / (60 + 0.0);
            } else {
                weidu = Double.parseDouble(w_du) + Double.parseDouble(w_fen) / (60 + 0.0) + Double.parseDouble(w_miao) / (3600 + 0.0);
            }
            if (TextUtils.isEmpty(j_fen)) {
                jingdu = Double.parseDouble(j_du);
            } else if (TextUtils.isEmpty(j_miao)) {
                jingdu = Double.parseDouble(j_du) + Double.parseDouble(j_fen) / (60 + 0.0);
            } else {
                jingdu = Double.parseDouble(j_du) + Double.parseDouble(j_fen) / (60 + 0.0) + Double.parseDouble(j_miao) / (3600 + 0.0);
            }

            double location_x = Constant.TimesNeed * (jingdu - GetLongLati.getLong(Constant.minX5, 0, 5)) / ((GetLongLati.getLong((Constant.maxX5 + 1), 0, 5) - GetLongLati.getLong(Constant.minX5, 0, 5))) * Constant.wapianWidth * (Constant.maxX5 - Constant.minX5 + 1);
            double location_y = Constant.TimesNeed * Constant.WpTimes * (GetLongLati.getY(weidu, 5) - Constant.minY5 * 256);


            float scale = mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale();

            getLocationDilog(location_x, location_y, weidu, jingdu, scale);

            dialog2.dismiss();
        });

        dialog2.show();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        double fangwj = 0;
        double juli = 0;
        float scale2 = mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale();
        float fraction = mTileView.getDetailLevelManager().getScale();
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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xDown = event.getX();
            yDown = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
            //获取松开时的x坐标
            if (isLongClickModule) {
                isLongClickModule = false;
                isLongClicking = false;
            }
            xUp = event.getX();
            if (mPageTag != 0 && mCollect2MainTag == 0) {
                //按下和松开绝对值差当大于20时滑动，否则不显示
                if (10 > (xDown - xUp)) {
                    double jingdu = (GetLongLati.pixelToLng((((v.getScrollX() + event.getX()) / Constant.WpTimes) / (fraction / scale2) + Constant.minX5 * (256) * Constant.TimesNeed * scale2), zoom));
                    double weidu = (GetLongLati.pixelToLat((((v.getScrollY() + event.getY()) / Constant.WpTimes) / (fraction / scale2) + (Constant.minY5 * (256) * Constant.TimesNeed * scale2)), zoom));
                    Double[] jwpoint = {jingdu, weidu};
                    mTileView.getCompositePathView().clear();
                    PointInfoBean pointInfoBean = new PointInfoBean();
                    mJingWeiPoints.add(jwpoint);

                    ImageView marker = new ImageView(MainActivity.this);
                    double poinhx = DecimalCalculate.add(v.getScrollX(), event.getX());
                    double poinhy = DecimalCalculate.add(v.getScrollY(), event.getY());
                    double poinhxx = DecimalCalculate.div(poinhx, scale2);
                    double poinhyy = DecimalCalculate.div(poinhy, scale2);
                    mPointList.add(new double[]{(poinhxx), (poinhyy)});
                    double[] point = {poinhxx, poinhyy};
                    int pointIntX = (int) poinhxx;
                    int pointIntY = (int) poinhyy;
                    double sx = poinhxx - pointIntX;
                    double sy = poinhyy - pointIntY;
                    double k = (sx * scale2) / (40 + 0f);
                    double k2 = (sy * scale2) / (40 + 0f);
                    float kh = (float) (-0.5 + k);
                    float khy = (float) (-0.5 + k2);

                    marker.setTag(point);
                    marker.setImageBitmap(mDistanceCircleRed);
                    mTileView.addMarker(marker, point[0], point[1], kh, khy);
                    TextView textView = new TextView(MainActivity.this);
                    textView.setTextColor(Color.BLACK);
                    double k21 = (sx * scale2) / (120 + 0f);
                    double k22 = (sy * scale2) / (80 + 0f);
                    float khx = (float) (-0.5 + k21);
                    float khyy = (float) (-0.5 + k22);
                    if (mPointList.size() != 0) {
                        if (mPageTag != 0) {
                            pointInfoBean.setJingdu(jingdu);
                            pointInfoBean.setWeidu(weidu);
                            double fwj = 0;
                            double jwtext = 0;
                            if (mPointList.size() > 1) {
                                if (mPageTag == 1) {
                                    mTileView.drawPath2(mPointList.subList(0, mPointList.size()), mDistancePathPaint);
                                }
                                Double[] doub1 = mJingWeiPoints.get(mPointList.size() - 2);
                                Double[] doub2 = mJingWeiPoints.get(mPointList.size() - 1);
                                jwtext = LocationUtils.gps2m(doub1[1], doub1[0], doub2[1], doub2[0]);
                                fwj = LocationUtils.gps2d(doub1[1], doub1[0], doub2[1], doub2[0]);
                            }
                            DecimalFormat df3 = new DecimalFormat("##0.00");

                            if (fwj < 0) {
                                if (mPageTag == 1) {
                                    textView.setText("" + df3.format(jwtext / 1852) + "海里" + "\n" + df3.format((fwj - 180) * -1) + "°");
                                } else {
                                    textView.setText(mPointList.size() + "");
                                }
                                fangwj = (fwj - 180) * -1;
                                juli = jwtext / 1852;
                            } else if (fwj >= 0) {
                                if (mPageTag == 1) {
                                    textView.setText("" + df3.format(jwtext / 1852) + "海里" + "\n" + df3.format(fwj) + "°");
                                } else {
                                    textView.setText(mPointList.size() + "");
                                }
                                fangwj = fwj;
                                juli = jwtext / 1852;
                            }
                            if (mPageTag == 1) {
                                mAllDistance = mAllDistance + jwtext;
                                mMainTextDistance.setText("" + df3.format(mAllDistance / 1852) + "海里");
                            }
                            pointInfoBean.setDistance(juli);
                            pointInfoBean.setFangwei(fangwj);
                            mPointInfoList.add(pointInfoBean);
                        }
                        mTileView.addMarker(textView, point[0], point[1], khx, khyy);
                    }
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE)

        {
            //当滑动时背景为选中状态 //检测是否长按,在非长按时检测
            if (mPageTag == 0) {
                if (!isLongClickModule) {
                    isLongClickModule = isLongPressed(xDown, yDown, event.getX(),
                            event.getY(), event.getDownTime(), event.getEventTime(), 300);
                    if (viewLL != null) {
                        mTileView.getMarkerLayout().removeMarker(viewLL);
                        viewLL = null;
                    }
                }
                if (isLongClickModule && !isLongClicking) {
                    //处理长按事件
                    isLongClicking = true;
                    float scale = mTileView.getDetailLevelManager().getCurrentDetailLevel().getScale();
                    double poinhx = DecimalCalculate.add(v.getScrollX(), event.getX());
                    double poinhy = DecimalCalculate.add(v.getScrollY(), event.getY());
                    double poinhxx = DecimalCalculate.div(poinhx, scale);
                    double poinhyy = DecimalCalculate.div(poinhy, scale);

                    double wd_d = (GetLongLati.pixelToLat(((((v.getScrollY() + event.getY()) / (Constant.WpTimes + 0.0)) / (fraction / scale) + (Constant.minY5 * (256) * TimesNeed * scale))), zoom));
                    double jd_d = (GetLongLati.pixelToLng(((((v.getScrollX() + event.getX()) / (Constant.WpTimes + 0.0)) / (fraction / scale) + Constant.minX5 * (256) * TimesNeed * scale)), zoom));

                    getLocationDilog(poinhxx, poinhyy, wd_d, jd_d, scale);
                }
            }
        }
        return false;
    }


    /**
     * 得到经纬度弹出窗口
     *
     * @param loction_x 坐标x位置
     * @param loction_y 坐标y位置
     * @param weidu     坐标
     * @param jingdu    坐标
     * @param scale
     */
    private void getLocationDilog(double loction_x, double loction_y, double weidu, double jingdu, float scale) {
        viewLL = getLayoutInflater().inflate(R.layout.home_map_fav_float, null);
        ImageView iv_close = (ImageView) viewLL.findViewById(R.id.iv_close);
        ImageView iv_edit = (ImageView) viewLL.findViewById(R.id.iv_dialog_edit);
        iv_close.setOnClickListener(v1 -> {
            if (viewLL != null) {
                mTileView.getMarkerLayout().removeMarker(viewLL);
                viewLL = null;
            }
        });

        TextView wd = (TextView) viewLL.findViewById(R.id.tv_wd);
        TextView jd = (TextView) viewLL.findViewById(R.id.tv_jd);
        wd.setText("纬度:" + ConvertUtils.Latitude(weidu));
        jd.setText("经度:" + ConvertUtils.Longitude(jingdu));

        MarkerLayout.LayoutParams layout = new MarkerLayout.LayoutParams(40, 100);
        viewLL.setLayoutParams(layout);


        mTileView.addMarker(viewLL, loction_x, loction_y, -0.5f, -1f);
        if (scale <= 64) {
            frameToWithScale(loction_x, loction_y, scale);
        }

        iv_edit.setOnClickListener(v -> {
            mTileView.getMarkerLayout().removeMarker(viewLL);
            viewLL = null;
            CollectDialog cdDialog = new CollectDialog(MainActivity.this);
            cdDialog.setNoOnclickListener(null, () -> cdDialog.dismiss());
            cdDialog.setYesOnclickListener(null, () -> {
                String name = cdDialog.getEt_name().getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.singleToast("航点名不能为空");
                    return;
                }
                List<SupportPoint> pointBeanList = DataSupport.where("point_name = ?", name)
                        .find(SupportPoint.class);

                if (pointBeanList.size() != 0) {
                    ToastUtils.singleToast("航点名已存在，请重新命名");
                    return;
                }
                int position = cdDialog.getPosition();
                SupportPoint supportPoint = new SupportPoint();
                supportPoint.setLongitude(jingdu);
                supportPoint.setLatitude(weidu);
                supportPoint.setPointName(name);
                supportPoint.setImage(position);
                supportPoint.save();

                cdDialog.dismiss();
                ToastUtils.singleToast("航点收藏成功");
            });
            cdDialog.show();
        });
    }

    /**
     * 初始化比例尺以及改变比例尺的大小
     *
     * @param b
     */
    private void getBilichi(float b) {
        mTileView.setScaleFromCenter(b);
        double z = Math.log(TimesNeed * b) / Math.log(2);
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

    public void frameTo(final double x, final double y) {
        mTileView.post(() -> mTileView.scrollToAndCenter(x, y));
    }

    public void frameToWithScale(final double x, final double y, final float scale) {
        mTileView.post(() -> mTileView.slideToAndCenterWithScale(x, y, scale));

    }

    private void enterFourPageActivity(String entertype) {
        ViewPageActivity.actionStart(mContext, entertype);
    }


}
