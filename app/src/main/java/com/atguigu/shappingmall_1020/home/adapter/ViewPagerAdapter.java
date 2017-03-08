package com.atguigu.shappingmall_1020.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.shappingmall_1020.home.bean.HomeBean;
import com.atguigu.shappingmall_1020.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 张永卫on 2017/2/24.
 */

public class ViewPagerAdapter extends PagerAdapter {


    private final Context mContext;
    private final List<HomeBean.ResultBean.ActInfoBean> act_info;

    public ViewPagerAdapter(Context mContext, List<HomeBean.ResultBean.ActInfoBean> act_info) {

        this.mContext = mContext;
        this.act_info = act_info;

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imageView = new ImageView(mContext);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);//拉伸
        //添加到容器中
        container.addView(imageView);
        //联网请求图片
        HomeBean.ResultBean.ActInfoBean actInfoBean = act_info.get(position);

        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE+actInfoBean.getIcon_url())
                .into(imageView);


        //设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null) {
                    listener.onItemClick(v,position);
                }
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return act_info.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //点击item的接口
    public interface OnItemClickListener{

         void onItemClick(View v,int position);
    }
    //定义接口字段
    private OnItemClickListener listener;

    /**
     * 设置item的点击事件
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
