package com.atguigu.shappingmall_1020.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.app.GoodsInfoActivity;
import com.atguigu.shappingmall_1020.base.BaseFragment;
import com.atguigu.shappingmall_1020.home.activity.SearchActivity;
import com.atguigu.shappingmall_1020.home.adapter.HomeAdapter;
import com.atguigu.shappingmall_1020.home.bean.GoodsBean;
import com.atguigu.shappingmall_1020.home.bean.HomeBean;
import com.atguigu.shappingmall_1020.utils.Constants;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 张永卫on 2017/2/22.
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    private HomeAdapter adapter;
    private static final int REQUEST_CODE = 111;
    private Intent intent;

    @Override
    public View initView() {

        Log.e("TAG", "主页视图被初始化了");

        View view = View.inflate(mContext, R.layout.fragment_home, null);
        //让当前类和视图绑定
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getDataFromNet();
    }

    private void getDataFromNet() {

        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String s, int i) {

                        Log.e("TAG", "联网成功" + s);
                        processData(s);
                    }

                });
    }

    /**
     * 1.解析数据
     * 2.设置适配器
     * @param s
     */
    private void processData(String s) {
        //使用fastjson解析json数据
        HomeBean homeBean = JSON.parseObject(s, HomeBean.class);
        Log.e("TAG", "解析数据成功=="+homeBean.getResult().getHot_info().get(0).getName());

        //设置RecyclerView的适配器
        adapter = new HomeAdapter(mContext,homeBean.getResult());

        rvHome.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(mContext,1);


       // rvHome.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        rvHome.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if(position<=3) {

                    //按钮隐藏
                    ibTop.setVisibility(View.GONE);
                }else{

                    //按钮显示
                    ibTop.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top,R.id.ll_main_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "查看信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                //Toast.makeText(mContext, "回到顶部", Toast.LENGTH_SHORT).show();
               rvHome.scrollToPosition(0);

                break;

            case R.id.ll_main_scan:
                /**
                 * 扫一扫
                 */
                intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(mContext, "解析结果:" + result, Toast.LENGTH_LONG).show();

                    GoodsBean goodsBean = new GoodsBean();

                    String[] s = result.split(",");

                    goodsBean.setFigure(s[0]);
                    goodsBean.setName(s[1]);
                    goodsBean.setProduct_id(s[2]);
                    goodsBean.setCover_price(null);


                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(HomeAdapter.GOODS_BEAN,goodsBean);
                    startActivity(intent);


                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
