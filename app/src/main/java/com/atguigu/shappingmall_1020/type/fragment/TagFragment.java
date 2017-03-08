package com.atguigu.shappingmall_1020.type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.base.BaseFragment;
import com.atguigu.shappingmall_1020.type.bean.TagBean;
import com.atguigu.shappingmall_1020.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 张永卫on 2017/3/3.
 */

public class TagFragment extends BaseFragment {


    @InjectView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;

    private List<TagBean.ResultBean> result;


    private int[] colors = {
            Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };



    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.fragment_tag, null);

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
                .url(Constants.TAG_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败了" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "TagFragment的数据联网成功了==");

                        processData(response);
                    }
                });
    }

    private void processData(String json) {

        TagBean tagBean = JSON.parseObject(json, TagBean.class);
        result = tagBean.getResult();
        final String[] mVals = new String[result.size()];

        for (int i = 0; i <result.size() ; i++) {
            mVals[i] = result.get(i).getName();
            
        }
        if (result != null && result.size() > 0) {

            idFlowlayout.setAdapter(new TagAdapter<String>(mVals) {

                @Override
                public View getView(FlowLayout parent, int position, String s) {

                    TextView tv  = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,idFlowlayout,false);

                    //设置颜色
                    tv.setTextColor(colors[position%colors.length]);
                    tv.setText(s);
                    return tv;
                }
            });

            idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                   Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
                    return true;
                }
            });


            idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    getActivity().setTitle("choose:" + selectPosSet.toString());
                }
            });

//            //设置适配器
//            adapter = new TagGridViewAdapter(mContext,result);
//            gvTag.setAdapter(adapter);
//
//            //设置item的点击事件
//            gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    TagBean.ResultBean resultBean = result.get(position);
//
////                    Toast.makeText(mContext, ""+resultBean.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            });


//            //设置适配器
//            adapter  =new TagAdapter(result) {
//                @Override
//                public View getView(FlowLayout parent, int position, Object o) {
//                    View view = View.inflate(mContext,R.layout.item_tag_gridview,null);
//
//                    TextView textView = (TextView) view.findViewById(tv_tag);
//                    GradientDrawable bg = (GradientDrawable) textView.getBackground();
//                    textView.setText(result.get(position).getName());
//                    textView.setTextColor(Color.BLACK);
//                    bg.setColor(colors[(int) (colors.length * Math.random())]);
//
//                    return view;
//                }
//            };
//
//            idFlowlayout.setAdapter(adapter);
//
//            //设置点击事件
//
//            idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//                @Override
//                public boolean onTagClick(View view, int position, FlowLayout parent) {
//
//                    Toast.makeText(mContext, "" + result.get(position).getName(), Toast.LENGTH_SHORT).show();
//
//                    return true;
//                }
//            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
