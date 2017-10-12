package com.ruitong.yuchuan.maptest.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.dialog.CourseLineDialog;
import com.ruitong.yuchuan.maptest.dialog.CoursePointDialog;
import com.ruitong.yuchuan.maptest.litepalbean.SupportLinePoint;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CourseLineAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;
    private String course_name;
    CourseLineDialog d2;

    public CourseLineAdapter(Context context, List<String> list, String course_name, CourseLineDialog dl) {
        this.context = context;
//        Collections.sort(list);
        this.list = list;
        this.course_name = course_name;
        this.d2 = dl;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    private CoursePointDialog dcp;

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_course_point, null);
            holder = new ViewHolder();
            holder.tv_point_name = (TextView) convertView.findViewById(R.id.tv_point_name);
            holder.ib = (ImageButton) convertView.findViewById(R.id.ib_update);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        String item = list.get(position);
        holder.tv_point_name.setText(item);
        holder.ib.setOnClickListener(v -> {
            List<SupportLinePoint> supportLinePoints = DataSupport.where("line_name = ? and position = ?", course_name,  (position+1)+"").find(SupportLinePoint.class);
            if (supportLinePoints.size() != 0) {
                SupportLinePoint linePoint = supportLinePoints.get(0);
                dcp = new CoursePointDialog(context, linePoint.getPoint_name(), "" + linePoint.getLongitude(), "" + linePoint.getLatitude());

                dcp.setNoOnclickListener(null, () -> dcp.dismiss());
                dcp.setYesOnclickListener(null, () -> {
                    String name2 = dcp.getEt_name().getText().toString();
                    String wd = dcp.getEt_wd().getText().toString();
                    String jd = dcp.getEt_jd().getText().toString();
                    if (TextUtils.isEmpty(name2)) {
                        Toast.makeText(context, "航点名不能为空", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    if (TextUtils.isEmpty(wd)) {
                        Toast.makeText(context, "纬度不能为空", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    if (TextUtils.isEmpty(jd)) {
                        Toast.makeText(context, "经度不能为空", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    if (Double.parseDouble(jd) > 180) {
                        Toast.makeText(context, "经度数值错误", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    if (Double.parseDouble(wd) > 90) {
                        Toast.makeText(context, "纬度数值错误", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    linePoint.setPoint_name(name2);
                    linePoint.setLongitude(Double.parseDouble(jd));
                    linePoint.setLatitude(Double.parseDouble(wd));
                    linePoint.updateAll("line_name = ? and position = ?", course_name, (position+1)+"" );
                    d2.updateList(position, name2);

                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();

                    dcp.dismiss();
                });
                dcp.show();
            }
        });
//        holder.itemText.setText(item.getLockName());
        /*
        代表为本船
         */


//        if (item.getDistance() != -1) {
//            DecimalFormat df3 = new DecimalFormat("##0.00");
//
//
//            holder.tv_distance.setText("距离:" + df3.format(item.getDistance() / (1852)) + " nm");
//        }

        return convertView;
    }

    static public class ViewHolder {


        TextView tv_point_name;
        ImageButton ib;
    }

}
