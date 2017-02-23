package com.atguigu.shappingmall_1020.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.base.BaseFragment;
import com.atguigu.shappingmall_1020.home.adapter.HomeAdapter;
import com.atguigu.shappingmall_1020.home.bean.HomeBean;
import com.atguigu.shappingmall_1020.utils.Constants;
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

        rvHome.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "查看信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                Toast.makeText(mContext, "回到顶部", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
