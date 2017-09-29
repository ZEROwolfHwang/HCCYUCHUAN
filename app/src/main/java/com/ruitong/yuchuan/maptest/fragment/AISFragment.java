package com.ruitong.yuchuan.maptest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.activity.AISItemPageActivity;
import com.ruitong.yuchuan.maptest.adapter.BaseAdapter;
import com.ruitong.yuchuan.maptest.adapter.BaseViewHolder;
import com.ruitong.yuchuan.maptest.litepalbean.SupportShipBean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AISFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler_ais)
    RecyclerView mRecyclerAis;
    Unbinder unbinder;


    private String mParam1;
    private String mParam2;
    private List<SupportShipBean> mAISList;


    public AISFragment() {

    }

    public static AISFragment newInstance(String param1, String param2) {
        AISFragment fragment = new AISFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ais, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();

        initAISRecyclerView();

        return view;
    }

    private void initData() {
//        mAISList = new ArrayList<>();

        DataSupport.deleteAll(SupportShipBean.class);
        for (int i = 0; i < 10; i++) {
            SupportShipBean bean = new SupportShipBean();
            bean.setAISList_1("1"+i);
            bean.setAISList_2("2"+i);
            bean.setAISList_3("3"+i);
            bean.setAISList_4("4"+i);
            bean.setAISList_5_MMSI("5");
            bean.setAISList_6("6");
            bean.setAISList_7("7");
            bean.setAISList_8("8");
            bean.setAISList_9("9");
            bean.setAISList_10("10");
            bean.setAISList_11("11");
            bean.setAISList_12("12");
            bean.setAISList_13("13");
            bean.setAISList_14("14");
            bean.setAISList_15("15");
            bean.setAISList_16("16");
            bean.setAISList_17("17");
            bean.setAISList_18("18");
            bean.save();

        }
    }

    private void initAISRecyclerView() {

        List<SupportShipBean> shipBeanList = DataSupport.findAll(SupportShipBean.class);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        BaseAdapter<SupportShipBean> adapter = new BaseAdapter<SupportShipBean>(getContext(), R.layout.layout_ais_fragment_list_item, (ArrayList<SupportShipBean>) shipBeanList) {
            @Override
            public void convert(BaseViewHolder holder, int position, SupportShipBean supportShipBean) {
                TextView ais_item_1 = holder.getView(R.id.ais_item_1);
                TextView ais_item_2 = holder.getView(R.id.ais_item_2);
                TextView ais_item_3 = holder.getView(R.id.ais_item_3);
                TextView ais_item_4 = holder.getView(R.id.ais_item_4);
//                Logger.i(supportShipBean.getAISList_1());
//                Logger.i(supportShipBean.getAISList_2());
//                Logger.i(supportShipBean.getAISList_3());
//                Logger.i(supportShipBean.getAISList_4());
                ais_item_1.setText(supportShipBean.getAISList_1());
                ais_item_2.setText(supportShipBean.getAISList_2());
                ais_item_3.setText(supportShipBean.getAISList_3());
                ais_item_4.setText(supportShipBean.getAISList_4());

                holder.itemView.setOnClickListener(v -> {
                    AISItemPageActivity.actionStart(getContext(),supportShipBean);
                });
            }
        };
        mRecyclerAis.setLayoutManager(manager);
        mRecyclerAis.setAdapter(adapter);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
