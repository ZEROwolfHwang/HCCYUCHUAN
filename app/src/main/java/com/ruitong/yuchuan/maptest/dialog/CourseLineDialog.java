package com.ruitong.yuchuan.maptest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.adapter.CourseLineAdapter;
import com.ruitong.yuchuan.maptest.helper.KeyBoardHelper;
import com.ruitong.yuchuan.maptest.litepalbean.SupportLine;
import com.ruitong.yuchuan.maptest.litepalbean.SupportLinePoint;
import com.ruitong.yuchuan.maptest.tools.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建自定义的dialog，主要学习其实现原理
 * Created by chengguo on 2016/3/22.
 */
public class CourseLineDialog extends Dialog {

    //    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;
    private EditText et_name;
    private EditText et_jd;
    private EditText et_wd;
    private Context context;
    ListView ls7;
    String jd;
    String wd;
    String name;
    /*
    收藏界面的index航线
     */
    int index;
    CourseLineAdapter mCourseLineAdapter;
    List<String> points = new ArrayList<>();
    private ImageButton btn_confirm;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
//    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     */
    public CourseLineDialog(Context context, String course_name, List<String> ls, int index
    ) {


        super(context, R.style.MyDialog);
        this.context = context;
        this.name = course_name;

        this.points = ls;
        this.index = index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.course_point_dialog, null);

        setContentView(view);


        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView(view);
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        no.setOnClickListener(v -> {
            if (noOnclickListener != null) {
                noOnclickListener.onNoClick();
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        et_name.setText(name);
        if (points.size() > 0) {
            if (mCourseLineAdapter == null) {
                mCourseLineAdapter = new CourseLineAdapter(context, points, name, this);
                ls7.setAdapter(mCourseLineAdapter);
            }
        }
        btn_confirm.setOnClickListener(v -> {

            String course_name = et_name.getText().toString();

            if (TextUtils.isEmpty(course_name)) {
                Toast.makeText(context, "航线名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!course_name.equals(name)) {
                List<SupportLine> supportLines = DataSupport.where("line_name = ?", course_name).find(SupportLine.class);
                if (supportLines.size() != 0) {
                    Toast.makeText(context, "航线名已存在，请重新命名", Toast.LENGTH_SHORT).show();
                } else {
                    SupportLine supportLine = new SupportLine();
                    supportLine.setLine_name(course_name);
                    supportLine.updateAll("line_name = ?", name);

                    SupportLinePoint linePoint = new SupportLinePoint();
                    linePoint.setLine_name(course_name);
                    linePoint.updateAll("line_name = ?", name);
                    ToastUtils.singleToast("航线名修改成功");
                }
                KeyBoardHelper.closeSoftKeybord(btn_confirm, context);
            }
        });
    }

    /**
     * 初始化界面控件
     */
    private void initView(View view) {
        no = (Button) view.findViewById(R.id.no);
        titleTv = (TextView) view.findViewById(R.id.title);

        et_name = (EditText) view.findViewById(R.id.et_name);
        ls7 = (ListView) view.findViewById(R.id.ls_point);
        btn_confirm = (ImageButton) view.findViewById(R.id.ib_save);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }


    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    /*
  更新dialog修改后list的回调
   */
    public void updateList(int index, String name2) {
        points.remove(index);
        points.add(index, name2);
        mCourseLineAdapter.notifyDataSetChanged();
    }


}
