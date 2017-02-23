package com.atguigu.shappingmall_1020.type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.base.BaseFragment;

/**
 * Created by 张永卫on 2017/2/22.
 */

public class TypeFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {

        Log.e("TAG", "分类视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "分类数据被初始化了");
        textView.setText("分类");
    }
}
