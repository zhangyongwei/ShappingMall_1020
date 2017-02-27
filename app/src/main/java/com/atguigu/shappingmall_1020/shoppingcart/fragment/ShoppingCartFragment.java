package com.atguigu.shappingmall_1020.shoppingcart.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.base.BaseFragment;
import com.atguigu.shappingmall_1020.home.bean.GoodsBean;
import com.atguigu.shappingmall_1020.shoppingcart.utils.CartStorage;

/**
 * Created by 张永卫on 2017/2/22.
 */

public class ShoppingCartFragment extends BaseFragment {

    private TextView textView;
    
    @Override
    public View initView() {

        Log.e("TAG", "购物车视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "购物车数据被初始化了");
        textView.setText("购物车");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setProduct_id("10659");
                goodsBean.setNumber(3);
                CartStorage.getInstance(mContext).updateData(goodsBean);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        for (int i = 0;i<CartStorage.getInstance(mContext).getAllData().size();i++){

            Log.e("TAG", ""+CartStorage.getInstance(mContext).getAllData().get(i).toString());
        }
    }
}
