package com.atguigu.shappingmall_1020.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.base.BaseFragment;
import com.atguigu.shappingmall_1020.community.adapter.NewPostListViewAdapter;
import com.atguigu.shappingmall_1020.community.bean.NewPostBean;
import com.atguigu.shappingmall_1020.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 张永卫on 2017/3/4.
 */

public class NewPostFragment extends BaseFragment {


    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;

    private NewPostListViewAdapter adapter;
    @Override
    public View initView() {

        Log.e("TAG", "用户视图被初始化了");

        View view = View.inflate(mContext, R.layout.fragment_news_post, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                //联网地址
                .url(Constants.NEW_POST_URL)
                .id(100)//http,
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","联网失败=="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","新帖联网成功==");
                        processData(response);

                    }
                });
    }

    private void processData(String json) {

        NewPostBean bean = JSON.parseObject(json, NewPostBean.class);

        List<NewPostBean.ResultBean> result = bean.getResult();

        if(result!=null && result.size()>0) {

            //设置适配器
            adapter = new NewPostListViewAdapter(mContext,result);

            lvNewPost.setAdapter(adapter);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
