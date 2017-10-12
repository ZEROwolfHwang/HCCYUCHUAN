package com.ruitong.yuchuan.maptest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.activity.MainActivity;
import com.ruitong.yuchuan.maptest.adapter.BaseAdapter;
import com.ruitong.yuchuan.maptest.adapter.BaseViewHolder;
import com.ruitong.yuchuan.maptest.dialog.UpdatePointDialog;
import com.ruitong.yuchuan.maptest.litepalbean.SupportPoint;
import com.ruitong.yuchuan.maptest.manager.GlobalManager;
import com.ruitong.yuchuan.maptest.tools.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class PointFragment extends Fragment {

    private List<SupportPoint> mPointList;
    private BaseAdapter<SupportPoint> mPointBaseAdapter;
    private ImageView mPoint_bg_Imageview;
    private RecyclerView mRecyclerView;

    public PointFragment() {
    }

    public static PointFragment newInstance() {
        PointFragment fragment = new PointFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.line_point_recycler);
        mPoint_bg_Imageview = (ImageView) view.findViewById(R.id.collect_line_bg);

        initData();

        // Set the adapter
        initView();

        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPointBaseAdapter = new BaseAdapter<SupportPoint>(getContext(), R.layout.fragment_point_line, (ArrayList<SupportPoint>) mPointList) {

            @Override
            public void convert(BaseViewHolder holder, int position, SupportPoint supportPoint) {
                TextView point_name = holder.getView(R.id.collect_text);
                ImageButton image_location = holder.getView(R.id.collect_location);
                ImageButton image_edit = holder.getView(R.id.collect_edit);
                ImageButton image_delete = holder.getView(R.id.collect_delete);

                point_name.setText(supportPoint.getPointName());
                image_location.setOnClickListener(v -> {
                    MainActivity.actionStart(getContext(), supportPoint.getPointName(), GlobalManager.POINT_NAME);
                });
                image_edit.setOnClickListener(v -> {
                    mPoint_bg_Imageview.setVisibility(View.VISIBLE);
                    editPoint(supportPoint.getPointName());
                });
                image_delete.setOnClickListener(v -> {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                    dialog.setTitle("是否确定删除该航点");
                    dialog.setPositiveButton("确定", (dialog1, which) -> {
                        mPointList.remove(supportPoint);
                        mPointBaseAdapter.notifyDataSetChanged();
                        DataSupport.deleteAll(SupportPoint.class, "point_name = ?", supportPoint.getPointName());
                    });
                    dialog.setNegativeButton("取消", (dialog1, which) -> dialog1.dismiss());
                    dialog.show();
                });
            }
        };
        mRecyclerView.setAdapter(mPointBaseAdapter);

    }

    /**
     * 修改航点
     *
     * @param pointName
     */
    private void editPoint(String pointName) {
        List<SupportPoint> supportPoints = DataSupport.where("point_name = ?", pointName).find(SupportPoint.class);
        if (supportPoints.size() != 0) {
            SupportPoint supportPoint = supportPoints.get(0);
            UpdatePointDialog pointDialog = new UpdatePointDialog(getActivity(), supportPoint.getPointName(), "" + supportPoint.getLongitude(), "" + supportPoint.getLatitude(), supportPoint.getImage());
            pointDialog.setCancelable(false);
            pointDialog.setYesOnclickListener(null, () -> {
                String name2 = pointDialog.getEt_name().getText().toString();
                String wd = pointDialog.getEt_wd().getText().toString();
                String jd = pointDialog.getEt_jd().getText().toString();

                List<SupportPoint> list = DataSupport.where("point_name = ?", name2).find(SupportPoint.class);
                if (list.size() != 0) {
                    ToastUtils.singleToast("航点名已存在,请重新命名");
                    return;
                }
                if (TextUtils.isEmpty(name2)) {
                    ToastUtils.singleToast("航点名不能为空");
                    return;
                }

                if (TextUtils.isEmpty(wd)) {
                    ToastUtils.singleToast("纬度不能为空");
                    return;
                }
                if (TextUtils.isEmpty(jd)) {
                    ToastUtils.singleToast("经度不能为空");
                    return;
                }
                if (Math.abs(Double.valueOf(wd)) > 90) {
                    ToastUtils.singleToast("纬度范围在90到-90,请重输");
                    return;
                }
                if (Math.abs(Double.valueOf(jd)) > 180) {
                    ToastUtils.singleToast("经度范围在180到-180,请重输");
                    return;
                }
                SupportPoint point = new SupportPoint();
                point.setPointName(name2);
                point.setLatitude(Double.parseDouble(wd));
                point.setLongitude(Double.parseDouble(jd));
                point.setImage(pointDialog.getPosition());
                point.updateAll("point_name = ?", pointName);
                ToastUtils.singleToast("修改成功");
                mPoint_bg_Imageview.setVisibility(View.GONE);

                List<SupportPoint> supportPointList = DataSupport.findAll(SupportPoint.class);
                mPointBaseAdapter.updataRecyclerView((ArrayList<SupportPoint>) supportPointList);
                pointDialog.dismiss();
            });
            pointDialog.setNoOnclickListener(null, () -> {
                mPoint_bg_Imageview.setVisibility(View.GONE);
                pointDialog.dismiss();
            });

            pointDialog.show();
        }
    }

    private void initData() {
        mPointList = DataSupport.findAll(SupportPoint.class);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
