package com.atguigu.shappingmall_1020.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.app.GoodsInfoActivity;
import com.atguigu.shappingmall_1020.home.adapter.HomeAdapter;
import com.atguigu.shappingmall_1020.home.bean.GoodsBean;
import com.atguigu.shappingmall_1020.type.bean.TypeBean;
import com.atguigu.shappingmall_1020.utils.Constants;
import com.atguigu.shappingmall_1020.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/3.
 */

public class TypeRightAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final List<TypeBean.ResultEntity.ChildEntity> child;
    /**
     * 热卖推荐
     */
    private static final int HOT = 0;
    /**
     * 常用分类
     */
    private static final int COMMON = 1;

    private int currentType = HOT;
    private final List<TypeBean.ResultEntity.HotProductListEntity> hot_product_list;
    private final LayoutInflater inflater;


    public TypeRightAdapter(Context mContext, List<TypeBean.ResultEntity> result) {
        this.mContext = mContext;
        //得到==常用分类
        child = result.get(0).getChild();
        hot_product_list = result.get(0).getHot_product_list();
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HOT) {

            return new HotViewHolder(inflater.inflate(R.layout.item_hot_right, null));
        } else if (viewType == COMMON) {

            return new CommonViewHolder(inflater.inflate(R.layout.item_common_right, null));
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == HOT) {

            HotViewHolder viewHolder = (HotViewHolder) holder;
            viewHolder.setData(hot_product_list);
        }else if(getItemViewType(position)==COMMON) {

            CommonViewHolder viewHolder = (CommonViewHolder) holder;

            int realPosition = position-1;
            viewHolder.setData(child.get(realPosition));
        }
    }

    /**
     * 根据位置得到不同的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (position == HOT) {

            currentType = HOT;
        } else {

            currentType = COMMON;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 1 + child.size();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_ordinary_right)
        ImageView ivOrdinaryRight;
        @InjectView(R.id.tv_ordinary_right)
        TextView tvOrdinaryRight;
        @InjectView(R.id.ll_root)
        LinearLayout llRoot;

        CommonViewHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);
        }

        public void setData(final TypeBean.ResultEntity.ChildEntity childEntity) {

            //请求图片
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE+childEntity.getPic())
                    .placeholder(R.drawable.new_img_loading_2).into(ivOrdinaryRight);

            //设置文本
            tvOrdinaryRight.setText(childEntity.getName());

            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
    }
    class HotViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @InjectView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        /**
         * 设置数据
         *
         * @param hot_product_list
         */
        public void setData(final List<TypeBean.ResultEntity.HotProductListEntity> hot_product_list) {

            for (int i = 0; i < hot_product_list.size(); i++) {

                TypeBean.ResultEntity.HotProductListEntity bean = hot_product_list.get(i);


                //外面的线性布局

                LinearLayout layout = new LinearLayout(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, -2);
                params.setMargins((DensityUtil.dip2px(mContext, 5)), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));

                layout.setGravity(Gravity.CENTER);//设置居中
                layout.setOrientation(LinearLayout.VERTICAL);//设置竖直方向

                //创建图片
                ImageView imageView = new ImageView(mContext);
                //设置图片的宽和高
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));

                //设置间距
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));

                //请求图片
                Glide.with(mContext).load(Constants.BASE_URL_IMAGE + bean.getFigure()).into(imageView);

                //把图片添加到线性布局
                layout.addView(imageView, ivParams);


                //创建文本--------
                //文字
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);

                textView.setTextColor(Color.parseColor("#ed3f3f"));
                textView.setText("￥" + bean.getCover_price());

                //把文本添加到线性布局
                layout.addView(textView, tvParams);

                //把每个线性布局添加到外部的线性布局中

                llHotRight.addView(layout, params);

                //设置item的点击事件
                layout.setTag(i);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Toast.makeText(mContext, "position==" + hot_product_list.get(position).getCover_price(), Toast.LENGTH_SHORT).show();
                        int position = (int) v.getTag();
                        String cover_price = hot_product_list.get(position).getCover_price();
                        String name = hot_product_list.get(position).getName();
                        String figure = hot_product_list.get(position).getFigure();
                        String product_id = hot_product_list.get(position).getProduct_id();


                        //创建商品Bean对象
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setName(name);
                        goodsBean.setFigure(figure);
                        goodsBean.setCover_price(cover_price);


                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra(HomeAdapter.GOODS_BEAN,goodsBean);
                        mContext.startActivity(intent);

                    }
                });
            }

        }
    }


}


