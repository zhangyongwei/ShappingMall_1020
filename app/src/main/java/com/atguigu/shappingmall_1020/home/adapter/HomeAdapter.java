package com.atguigu.shappingmall_1020.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.app.GoodsInfoActivity;
import com.atguigu.shappingmall_1020.app.WebViewActivity;
import com.atguigu.shappingmall_1020.home.activity.GoodsListActivity;
import com.atguigu.shappingmall_1020.home.bean.GoodsBean;
import com.atguigu.shappingmall_1020.home.bean.HomeBean;
import com.atguigu.shappingmall_1020.home.bean.WebViewBean;
import com.atguigu.shappingmall_1020.home.view.MyGridView;
import com.atguigu.shappingmall_1020.utils.Constants;
import com.atguigu.shappingmall_1020.utils.DensityUtil;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by 张永卫on 2017/2/23.
 */

public class HomeAdapter extends RecyclerView.Adapter {


    public static final String GOODS_BEAN ="goodsBean" ;
    public static String WEBVIEW_BEAN = "webview_bean";
    private final Context mContext;
    private final HomeBean.ResultBean result;
    //用他来加载布局
    private final LayoutInflater inflater;




    private ViewPagerAdapter adapter;



    public HomeAdapter(Context mContext, HomeBean.ResultBean result) {
        this.mContext = mContext;
        this.result = result;
        inflater = LayoutInflater.from(mContext);
    }


    /**
     * 六种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;

    /**
     * 当前类型
     */
    public int currentType = BANNER;

    /**
     * 根据位置得到对应的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {

            currentType = BANNER;

        } else if (position == CHANNEL) {

            currentType = CHANNEL;

        } else if (position == ACT) {

            currentType = ACT;

        } else if (position == SECKILL) {

            currentType = SECKILL;

        } else if (position == RECOMMEND) {

            currentType = RECOMMEND;

        } else if (position == HOT) {

            currentType = HOT;
        }

        return currentType;
    }

    /**
     * @param parent
     * @param viewType 当前的类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {

            return new BannerViewHolder(mContext, inflater.inflate(R.layout.banner_viewpager, null));

        } else if (viewType == CHANNEL) {

            return new ChannelViewHolder(mContext, inflater.inflate(R.layout.channel_item, null));

        } else if (viewType == ACT) {

            return new ActViewHolder(mContext, inflater.inflate(R.layout.act_item, null));


        } else if (viewType == SECKILL) {

            return new SeckillViewHolder(mContext, inflater.inflate(R.layout.seckill_item, null));

        } else if (viewType == RECOMMEND) {

            return new RecommendViewHolder(mContext, inflater.inflate(R.layout.recommend_item, null));

        } else if (viewType == HOT) {

            return new HotViewHolder(mContext, inflater.inflate(R.layout.hot_item, null));
        }

        return null;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == BANNER) {

            BannerViewHolder viewHolder = (BannerViewHolder) holder;

            viewHolder.setData(result.getBanner_info());

        } else if (getItemViewType(position) == CHANNEL) {

            ChannelViewHolder viewHolder = (ChannelViewHolder) holder;

            viewHolder.setData(result.getChannel_info());

        } else if (getItemViewType(position) == ACT) {

            ActViewHolder viewHolder = (ActViewHolder) holder;

            viewHolder.setData(result.getAct_info());

        } else if (getItemViewType(position) == SECKILL) {

            SeckillViewHolder viewHolder = (SeckillViewHolder) holder;

            viewHolder.setData(result.getSeckill_info());

        } else if (getItemViewType(position) == RECOMMEND) {

            RecommendViewHolder viewHolder = (RecommendViewHolder) holder;

            viewHolder.setData(result.getRecommend_info());

        } else if (getItemViewType(position) == HOT) {

            HotViewHolder viewHolder = (HotViewHolder) holder;

            viewHolder.setData(result.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        //所有的类型写完后改成6
        return 6;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_more_hot)
        TextView tvMoreHot;
        @InjectView(R.id.gv_hot)
        MyGridView gvHot;
        HotGridViewAdapter adapter;

        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }

        public void setData(final List<HomeBean.ResultBean.HotInfoBean> hot_info) {

            //设置适配器
            adapter = new HotGridViewAdapter(mContext,hot_info);
            gvHot.setAdapter(adapter);

            //设置点击事件
            gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HomeBean.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    //商品新的Bean对象
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setCover_price(hotInfoBean.getCover_price());


                    Intent intent = new Intent(mContext,GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    mContext.startActivity(intent);

                }
            });
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_more_recommend)
        TextView tvMoreRecommend;
        @InjectView(R.id.gv_recommend)
        GridView gvRecommend;
        RecommendGridViewAdapter adapter;

        public RecommendViewHolder(Context mContext, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {

            //设置适配器
            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gvRecommend.setAdapter(adapter);
            //设置点击事件

            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  //  Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();

                    HomeBean.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                    //传递数据
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());


                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    mContext.startActivity(intent);

                }
            });
        }
    }

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.countdownview)
        CountdownView countdownview;
        @InjectView(R.id.tv_more_seckill)
        TextView tvMoreSeckill;
        @InjectView(R.id.rv_seckill)
        RecyclerView rvSeckill;

        SeckillRecyclerViewAdapter adapter;

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setData(final HomeBean.ResultBean.SeckillInfoBean seckill_info) {

            adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info);
            rvSeckill.setAdapter(adapter);

            //设置布局管理器
            rvSeckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            //设置点击事件
            adapter.setOnItemClickListener(new SeckillRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {

                    HomeBean.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);

                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(listBean.getName());
                    goodsBean.setFigure(listBean.getFigure());
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setProduct_id(listBean.getProduct_id());


                    Intent intent = new Intent(mContext,GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN,goodsBean);
                    mContext.startActivity(intent);

                }
            });

            //设置秒杀时间

            countdownview.setTag("test1");
            long duration = Long.parseLong(seckill_info.getEnd_time()) - Long.parseLong(seckill_info.getStart_time());
            countdownview.start(duration);

        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        @InjectView(R.id.viewpager)
        ViewPager viewpager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<HomeBean.ResultBean.ActInfoBean> act_info) {

            adapter = new ViewPagerAdapter(mContext, act_info);
            viewpager.setAdapter(adapter);
            //设置间距
            viewpager.setPageMargin(DensityUtil.dip2px(mContext, 20));


            //设置点击事件
            adapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    HomeBean.ResultBean.ActInfoBean actInfoBean = act_info.get(position);

                    WebViewBean webViewBean = new WebViewBean();
                    webViewBean.setName(actInfoBean.getName());
                    webViewBean.setIcon_url(actInfoBean.getIcon_url());
                    webViewBean.setUrl(actInfoBean.getUrl());


                    Intent intent = new Intent(mContext, WebViewActivity.class);

                    intent.putExtra(WEBVIEW_BEAN,webViewBean);

                    mContext.startActivity(intent);
                }
            });

        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        @InjectView(R.id.gv_channel)
        GridView gvChannel;

        ChannelAdapter channelAdapter;

        public ChannelViewHolder(Context mContext, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            this.mContext = mContext;
        }

        public void setData(List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            //设置GrideView的适配器
            channelAdapter = new ChannelAdapter(mContext, channel_info);
            gvChannel.setAdapter(channelAdapter);

            //设置item的点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    
                    if(position<9) {

                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position",position);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<HomeBean.ResultBean.BannerInfoBean> banner_info) {

            //1.得到数据
            //2.设置Banner的数据
            List<String> images = new ArrayList<>();

            for (int i = 0; i < banner_info.size(); i++) {

                images.add(Constants.BASE_URL_IMAGE + banner_info.get(i).getImage());

            }

            //简单使用
            banner.setImages(images)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {

                            //具体方法内容可以选择，此方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
                            Glide.with(mContext)
                                    .load(path)
                                    .crossFade()
                                    .into(imageView);


                        }
                    })
                    .start();

            //设置样式
            banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
            //3.设置Banner的点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                    int realPostion = position;
                    if (realPostion < banner_info.size()) {
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        String image = "";
                        if (realPostion == 0) {
                            product_id = "627";
                            cover_price = "32.00";
                            name = "剑三T恤批发";
                        } else if (realPostion == 1) {
                            product_id = "21";
                            cover_price = "8.00";
                            name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                        } else {
                            product_id = "1341";
                            cover_price = "50.00";
                            name = "【蓝诺】《天下吾双》 剑网3同人本";
                        }

                        image = banner_info.get(position).getImage();

                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setName(name);
                        goodsBean.setCover_price(cover_price);
                        goodsBean.setFigure(image);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);

                        intent.putExtra(GOODS_BEAN,goodsBean);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
