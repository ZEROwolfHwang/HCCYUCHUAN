package com.ruitong.yuchuan.maptest.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ruitong.yuchuan.maptest.R;
import com.ruitong.yuchuan.maptest.map.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建自定义的dialog，主要学习其实现原理
 * Created by chengguo on 2016/3/22.
 */
public class CollectDialog extends Dialog {
    private  int position=0;
    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;
     private EditText et_name;
private Context context;
    private ImageView iv_tubiao;
    private ImageButton iv_update;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

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
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public CollectDialog(Context context) {

        super(context, R.style.MyDialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
//        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    public ImageView getIv_tubiao() {
        return iv_tubiao;
    }

    public void setIv_tubiao(ImageView iv_tubiao) {
        this.iv_tubiao = iv_tubiao;
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        titleTv = (TextView) findViewById(R.id.title);
        iv_tubiao = (ImageView) findViewById(R.id.iv_tubiao);
        iv_update = (ImageButton) findViewById(R.id.iv_update);
        titleTv = (TextView) findViewById(R.id.title);

        et_name=(EditText) findViewById(R.id.et_name);

        iv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGridViewDialog();
            }
        });
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

    public EditText getEt_name() {
        return et_name;
    }

    public void setEt_name(EditText et_name) {
        this.et_name = et_name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    //对话框title图片
    private ImageView iv_icon;
    private Dialog dialog;
    GridView mGridView;
//    Button gridOK,gridCancel;
    /**
     * 初始化第二个gridview dialog
     */
    public void initGridViewDialog() {

        dialog = new Dialog(context);
        //设置无titile
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(
                R.layout.changeicon, null);

        mGridView = (GridView) view.findViewById(R.id.changeicon_gridview);

        iv_icon=(ImageView)view.findViewById(R.id.iv_icon);

        initGridView();

        dialog.setContentView(view);
        dialog.show();
    }
    SimpleAdapter sim_adapter;

    private String[] from = { "image" };
    private int[] to = { R.id.image};
    /**
     * 初始化gridView
     */
    public void initGridView() {
        data_list = new ArrayList<Map<String, Object>>();
        getGridData();
        sim_adapter = new SimpleAdapter(context, data_list,
                R.layout.changeicon_griditem, from, to);

        mGridView.setAdapter(sim_adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parents, View view,
                                    int position, long arg3) {
//                gridPosition = position + 1;
//                Logger.i("aaa"+gridPosition);
                setPosition(position);
                iv_tubiao.setBackgroundResource(Constant.hdImage[position]);
                dialog.dismiss();
            }
        });
    }
    List<Map<String, Object>> data_list;
    /**
     * 加载gridView的数据
     */
    public List<Map<String, Object>> getGridData() {
        for (int i = 0; i < Constant.hdImage.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", Constant.hdImage[i]);
//            map.put("text", gridNames[i]);
            data_list.add(map);
        }
        return data_list;
    }
}
