package com.ruitong.yuchuan.maptest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.adapter.CollectViewPagerAdapter;
import com.ruitong.yuchuan.maptest.manager.GlobalManager;
import com.ruitong.yuchuan.maptest.tools.ActionBarTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CollectActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_collect)
    TabLayout mTabCollect;
    @BindView(R.id.view_pager_sure)
    ViewPager mViewPager;
    private Unbinder mUnbinder;
    private CollectViewPagerAdapter mPagerAdapter;
    private String mType;

    public static void actionStart(Context context, String type) {
        Intent intent = new Intent(context, CollectActivity.class);
        intent.setType(type);
        context.startActivity(intent);
    }
    /**
     * 得到从上一个activity中拿到的数据
     */
    private void getIntentData() {
        Intent intent = getIntent();
        mType = intent.getType();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecte);
        mUnbinder = ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
        getIntentData();
        initToolBar();
        initViewPagerAndTabs();

    }

    private void initToolBar() {
        TextView title_text_view = ActionBarTool.getInstance(this, 991).getTitle_text_view();
        title_text_view.setText("我的收藏");

        mToolbar.setNavigationIcon(R.drawable.back_up_logo);
        mToolbar.setNavigationOnClickListener((v -> onBackPressed()));

    }

    /**
     * 初始化ViewPager和TabLayout
     */
    private void initViewPagerAndTabs() {

        mViewPager.setOffscreenPageLimit(2);//设置viewpager预加载页面数

        mPagerAdapter = new CollectViewPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);  // 给Viewpager设置适配器
        if (GlobalManager.ENTERTYPE_LINE.equals(mType)) {
            mViewPager.setCurrentItem(0); // 设置当前显示在哪个页面}
        }
        if (GlobalManager.ENTERTYPE_POINT.equals(mType)) {
            mViewPager.setCurrentItem(1); // 设置当前显示在哪个页面}
        }
        mTabCollect.setupWithViewPager(mViewPager);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
