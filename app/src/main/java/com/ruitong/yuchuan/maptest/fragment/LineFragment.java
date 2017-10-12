package com.ruitong.yuchuan.maptest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ruitong.yuchuan.maptest.dialog.CourseLineDialog;
import com.ruitong.yuchuan.maptest.litepalbean.SupportLine;
import com.ruitong.yuchuan.maptest.litepalbean.SupportLinePoint;
import com.ruitong.yuchuan.maptest.manager.GlobalManager;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.crud.DataSupport.findAll;


public class LineFragment extends Fragment {

    private BaseAdapter<SupportLine> mPointBaseAdapter;
    private List<SupportLine> mLineList;
    private ImageView mLine_bg_Imageview;
    private RecyclerView mRecyclerView;

    public LineFragment() {
    }

    public static LineFragment newInstance() {
        LineFragment fragment = new LineFragment();
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
        mLine_bg_Imageview = (ImageView) view.findViewById(R.id.collect_line_bg);
        initData();

        // Set the adapter
        initView();

        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPointBaseAdapter = new BaseAdapter<SupportLine>(getContext(), R.layout.fragment_point_line, (ArrayList<SupportLine>) mLineList) {

            @Override
            public void convert(BaseViewHolder holder, int position, SupportLine supportLine) {
                TextView point_name = holder.getView(R.id.collect_text);
                ImageButton image_location = holder.getView(R.id.collect_location);
                ImageButton image_edit = holder.getView(R.id.collect_edit);
                ImageButton image_delete = holder.getView(R.id.collect_delete);

                point_name.setText(supportLine.getLine_name());
                image_location.setOnClickListener(v -> {
                    MainActivity.actionStart(getContext(), supportLine.getLine_name(), GlobalManager.LINE_NAME);
                });
                image_edit.setOnClickListener(v -> {
                    mLine_bg_Imageview.setVisibility(View.VISIBLE);
                    List<SupportLinePoint> linePointList = DataSupport.where("line_name = ?", supportLine.getLine_name()).find(SupportLinePoint.class);

                    List<String> ls2 = new ArrayList<>();
                    for (int i = 0; i < linePointList.size(); i++) {
                        ls2.add(i, linePointList.get(i).getPoint_name());
                    }
                    CourseLineDialog courseLineDialog = new CourseLineDialog(getActivity(), supportLine.getLine_name(), ls2, position);
                    courseLineDialog.setNoOnclickListener(null, () -> {
                        List<SupportLine> supportLineList = DataSupport.findAll(SupportLine.class);
                        mPointBaseAdapter.updataRecyclerView((ArrayList<SupportLine>) supportLineList);
                        mLine_bg_Imageview.setVisibility(View.GONE);
                        courseLineDialog.dismiss();
                    });
                    courseLineDialog.setCancelable(false);
                    courseLineDialog.show();

                });
                image_delete.setOnClickListener(v -> {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                    dialog.setTitle("是否确定删除该航线");
                    dialog.setPositiveButton("确定", (dialog1, which) -> {
                        mLineList.remove(supportLine);
                        mPointBaseAdapter.notifyDataSetChanged();
                        DataSupport.deleteAll(SupportLine.class, "line_name = ?", supportLine.getLine_name());
                    });
                    dialog.setNegativeButton("取消", (dialog1, which) -> dialog1.dismiss());
                    dialog.show();
                });
            }
        };
        mRecyclerView.setAdapter(mPointBaseAdapter);

    }

    private void initData() {
        mLineList = findAll(SupportLine.class);
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
