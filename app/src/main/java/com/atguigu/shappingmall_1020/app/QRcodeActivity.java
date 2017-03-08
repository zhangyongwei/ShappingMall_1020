package com.atguigu.shappingmall_1020.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.home.adapter.HomeAdapter;
import com.atguigu.shappingmall_1020.home.bean.GoodsBean;
import com.atguigu.shappingmall_1020.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class QRcodeActivity extends AppCompatActivity {

    private ImageView iv_saoyisao;
    private GoodsBean goodsBean;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        iv_saoyisao = (ImageView) findViewById(R.id.iv_saoyisao);

        initData();
    }

    private void initData() {

        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODS_BEAN);
        final String s = goodsBean.getFigure() + "," + goodsBean.getName() + ","
                + goodsBean.getProduct_id()+ "," + goodsBean.getCover_price();

        Glide.with(this).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBitmap = CodeUtils.createImage(s, 400, 400, resource);
                        iv_saoyisao.setImageBitmap(mBitmap);
                    }
                });

    }
}
