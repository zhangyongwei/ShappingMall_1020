package com.atguigu.shappingmall_1020.shoppingcart.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.R;

/**
 * Created by 张永卫on 2017/2/28.
 */

public class AddSubView extends LinearLayout {


    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");//要转换成String
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //把布局文件实例化成view，并且添加到AddSubView.this类中，成为他的子视图
        View.inflate(context, R.layout.add_sub_view,AddSubView.this);
        iv_sub = (ImageView)findViewById(R.id.iv_sub);
        iv_add = (ImageView)findViewById(R.id.iv_add);
        tv_value = (TextView)findViewById(R.id.tv_value);

        //设置点击事件
        iv_sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //不能小于最小值
                if(value>minValue) {

                    value--;

                }

                setValue(value);

                if(listener!=null) {

                    listener.onNumberChanger(value);
                }
            }
        });

        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(value<maxValue) {

                    value++;

                }

                setValue(value);

                if(listener!=null) {

                    listener.onNumberChanger(value);
                }
            }
        });

        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                iv_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                iv_sub.setImageDrawable(subDrawable);
            }
        }
    }

    /**
     * 自定义接口
     */
    public interface OnNumberChangerListener{

        public void onNumberChanger(int value);
    }

    /**
     * 定义字段
     *
     */

    public OnNumberChangerListener listener;

    /**
     * set方法
     * 设置数字的改变监听，在activity或者适配器等中使用
     * @param listener
     */
    public void setOnNumberChangerListener(OnNumberChangerListener listener) {
        this.listener = listener;
    }
}
