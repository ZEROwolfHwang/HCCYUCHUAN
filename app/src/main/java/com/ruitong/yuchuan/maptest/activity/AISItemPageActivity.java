package com.ruitong.yuchuan.maptest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.litepalbean.SupportShipBean;

import butterknife.ButterKnife;

public class AISItemPageActivity extends AppCompatActivity {
    private static String ARG_AIS_ITEM = "arg_ais_item";
/*
    @BindView(R.id.text_table_1)
    TextView mTextTable1;
    @BindView(R.id.text_table_2)
    TextView mTextTable2;
    @BindView(R.id.text_table_1)
    TextView mTextTable3;
    @BindView(R.id.text_table_2)
    TextView mTextTable4;
    @BindView(R.id.text_table_1)
    TextView mTextTable5;
    @BindView(R.id.text_table_2)
    TextView mTextTable6;
    @BindView(R.id.text_table_1)
    TextView mTextTable7;
    @BindView(R.id.text_table_2)
    TextView mTextTable8;
    @BindView(R.id.text_table_1)
    TextView mTextTable9;
    @BindView(R.id.text_table_2)
    TextView mTextTable10;
    @BindView(R.id.text_table_1)
    TextView mTextTable11;
    @BindView(R.id.text_table_2)
    TextView mTextTable12;
    @BindView(R.id.text_table_1)
    TextView mTextTable13;
    @BindView(R.id.text_table_2)
    TextView mTextTable14;
    @BindView(R.id.text_table_1)
    TextView mTextTable15;
    @BindView(R.id.text_table_2)
    TextView mTextTable16;
    @BindView(R.id.text_table_1)
    TextView mTextTable17;
    @BindView(R.id.text_table_2)
    TextView mTextTable18;
*/

    private static String[] AISTitles = {"船名", "纬度", "呼号", "经度", "MMSI", "航首向", "IMO",
            "航迹向", "航籍", "航速", "类型", "货物类型", "状态", "目的地",
            "船长", "预到时间", "船宽", "最后时间"};
    private TextView[] mTitleTextviews;
    private TextView[] mContentTextviews;
    private SupportShipBean mShipBean;


    public static void actionStart(Context context, SupportShipBean supportShipBean) {
        Intent intent = new Intent(context, AISItemPageActivity.class);
        intent.putExtra(ARG_AIS_ITEM, supportShipBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ais_item_page);
        ButterKnife.bind(this);

        getIntentData();
        initView();

    }

    private void getIntentData() {
        mShipBean = getIntent().getParcelableExtra(ARG_AIS_ITEM);
    }

    private void initView() {
        LinearLayout page01 = (LinearLayout) findViewById(R.id.ll_pager_01);
        LinearLayout page02 = (LinearLayout) findViewById(R.id.ll_pager_02);
        LinearLayout page03 = (LinearLayout) findViewById(R.id.ll_pager_03);
        LinearLayout page04 = (LinearLayout) findViewById(R.id.ll_pager_04);
        LinearLayout page05 = (LinearLayout) findViewById(R.id.ll_pager_05);
        LinearLayout page06 = (LinearLayout) findViewById(R.id.ll_pager_06);
        LinearLayout page07 = (LinearLayout) findViewById(R.id.ll_pager_07);
        LinearLayout page08 = (LinearLayout) findViewById(R.id.ll_pager_08);
        LinearLayout page09 = (LinearLayout) findViewById(R.id.ll_pager_09);

        TextView mAisTitleLeft1 = (TextView) page01.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight1 = (TextView) page01.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft2 = (TextView) page02.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight2 = (TextView) page02.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft3 = (TextView) page03.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight3 = (TextView) page03.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft4 = (TextView) page04.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight4 = (TextView) page04.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft5 = (TextView) page05.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight5 = (TextView) page05.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft6 = (TextView) page06.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight6 = (TextView) page06.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft7 = (TextView) page07.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight7 = (TextView) page07.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft8 = (TextView) page08.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight8 = (TextView) page08.findViewById(R.id.ais_title_right);
        TextView mAisTitleLeft9 = (TextView) page09.findViewById(R.id.ais_title_left);
        TextView mAisTitleRight9 = (TextView) page09.findViewById(R.id.ais_title_right);

        TextView AisContent1 = (TextView) page01.findViewById(R.id.text_table_1);
        TextView AisContent2 = (TextView) page01.findViewById(R.id.text_table_2);
        TextView AisContent3 = (TextView) page02.findViewById(R.id.text_table_1);
        TextView AisContent4 = (TextView) page02.findViewById(R.id.text_table_2);
        TextView AisContent5 = (TextView) page03.findViewById(R.id.text_table_1);
        TextView AisContent6 = (TextView) page03.findViewById(R.id.text_table_2);
        TextView AisContent7 = (TextView) page04.findViewById(R.id.text_table_1);
        TextView AisContent8 = (TextView) page04.findViewById(R.id.text_table_2);
        TextView AisContent9 = (TextView) page05.findViewById(R.id.text_table_1);
        TextView AisContent10 = (TextView) page05.findViewById(R.id.text_table_2);
        TextView AisContent11 = (TextView) page06.findViewById(R.id.text_table_1);
        TextView AisContent12 = (TextView) page06.findViewById(R.id.text_table_2);
        TextView AisContent13 = (TextView) page07.findViewById(R.id.text_table_1);
        TextView AisContent14 = (TextView) page07.findViewById(R.id.text_table_2);
        TextView AisContent15 = (TextView) page08.findViewById(R.id.text_table_1);
        TextView AisContent16 = (TextView) page08.findViewById(R.id.text_table_2);
        TextView AisContent17 = (TextView) page09.findViewById(R.id.text_table_1);
        TextView AisContent18 = (TextView) page09.findViewById(R.id.text_table_2);


        mTitleTextviews = new TextView[]{mAisTitleLeft1, mAisTitleRight1, mAisTitleLeft2, mAisTitleRight2,
                mAisTitleLeft3, mAisTitleRight3, mAisTitleLeft4, mAisTitleRight4,
                mAisTitleLeft5, mAisTitleRight5, mAisTitleLeft6, mAisTitleRight6,
                mAisTitleLeft7, mAisTitleRight7, mAisTitleLeft8, mAisTitleRight8,
                mAisTitleLeft9, mAisTitleRight9
        };
        mContentTextviews = new TextView[]{AisContent1, AisContent2, AisContent3, AisContent4,
                AisContent5, AisContent6, AisContent7, AisContent8,
                AisContent9, AisContent10, AisContent11, AisContent12,
                AisContent13, AisContent14, AisContent15, AisContent16,
                AisContent17, AisContent18
        };

        for (int i = 0; i < mTitleTextviews.length; i++) {
            mTitleTextviews[i].setText(AISTitles[i]);
        }
        AisContent1.setText(mShipBean.getAISList_1());
        AisContent2.setText(mShipBean.getAISList_2());
        AisContent3.setText(mShipBean.getAISList_3());
        AisContent4.setText(mShipBean.getAISList_4());
        AisContent5.setText(mShipBean.getAISList_5_MMSI());
        AisContent6.setText(mShipBean.getAISList_6());
        AisContent7.setText(mShipBean.getAISList_7());
        AisContent8.setText(mShipBean.getAISList_8());
        AisContent9.setText(mShipBean.getAISList_9());
        AisContent10.setText(mShipBean.getAISList_10());
        AisContent11.setText(mShipBean.getAISList_11());
        AisContent12.setText(mShipBean.getAISList_12());
        AisContent13.setText(mShipBean.getAISList_13());
        AisContent14.setText(mShipBean.getAISList_14());
        AisContent15.setText(mShipBean.getAISList_15());
        AisContent16.setText(mShipBean.getAISList_16());
        AisContent17.setText(mShipBean.getAISList_17());
        AisContent18.setText(mShipBean.getAISList_18());

    }


}
