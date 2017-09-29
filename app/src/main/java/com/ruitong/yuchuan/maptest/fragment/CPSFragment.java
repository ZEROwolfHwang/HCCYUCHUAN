package com.ruitong.yuchuan.maptest.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.compass.AzimuthProvider;
import com.ruitong.yuchuan.maptest.compass.CompassView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CPSFragment extends Fragment {

    @BindView(R.id.fragment_compass_view)
    CompassView compassView;
    @BindView(R.id.compass_title)
    TextView mCompassTitle;
    @BindView(R.id.compass_content)
    TextView mCompassContent;
    private Unbinder mUnbinder;
    private Timer mTimer;

    public CPSFragment() {

    }

    public static CPSFragment newInstance(String param1, String param2) {
        CPSFragment fragment = new CPSFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private final static String LOCATION_PARCELABLE_KEY = "location";

    private AzimuthProvider azimuthProvider;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribeProviders();
    }

    @Override
    public void onResume() {
        super.onResume();

        azimuthProvider.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        azimuthProvider.stop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compass, container, false);

        mUnbinder = ButterKnife.bind(this, v);
        final Handler handler = new Handler();
        //每隔2s更新一下经纬度结果
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {//每秒钟检查一下当前位置
            @Override
            public void run() {
                handler.post(() -> CompassView.setOnAngle(angle -> {
                    if (angle != 0) {
                        try {
//                            Logger.i("方向"+angle+"");
                            float mAngle = (-angle + 360);
                            if (mAngle > 360) {
                                mCompassContent.setText((mAngle - 360) + "");
                            } else {
                                mCompassContent.setText((int) mAngle + "°");
                            }
                            editTitle(mAngle);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }));

            }
        }, 0, 2000);
        return v;
    }

    private void editTitle(float angle) {
        if (angle >= 250 && angle < 295) {
            setTitle("西");
        } else if (angle >= 295 && angle < 335) {
            setTitle("西北");
        } else if ((angle >= 335 && angle <= 360) || (angle >= 0 && angle < 25)) {
            setTitle("北");
        } else if (angle >= 25 && angle < 75) {
            setTitle("东北");
        } else if (angle >= 75 && angle < 115) {
            setTitle("东");
        } else if (angle >= 115 && angle < 160) {
            setTitle("东南");
        } else if (angle >= 160 && angle < 205) {
            setTitle("南");
        } else if (angle >= 205 && angle < 250) {
            setTitle("西南");
        }
    }

    private void setTitle(String text) {
        mCompassTitle.setText(text);
    }


    private void subscribeProviders() {
        azimuthProvider
                .getObservable()
                .map(value -> -value)
                .map(Math::toDegrees)
                .map(Double::floatValue)
                .map(angle ->
                        angle < 0 ? angle + 360 : angle)
                .retry()
                .subscribe(compassView::setNeedleAngle);

    }


    private void initialize() {
        azimuthProvider = new AzimuthProvider(getActivity());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mTimer.cancel();
    }


}
