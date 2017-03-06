package com.atguigu.shappingmall_1020.community.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.base.BaseFragment;
import com.atguigu.shappingmall_1020.community.adapter.CommunityViewPagerAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张永卫on 2017/2/22.
 */

public class CommunityFragment extends BaseFragment {


    @InjectView(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @InjectView(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;

    private ArrayList<BaseFragment> fragments;

    private CommunityViewPagerAdapter adapter;

    @Override
    public View initView() {

        Log.e("TAG", "发现视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_community, null);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        initFragment();

        //设置适配器
        adapter = new CommunityViewPagerAdapter(getFragmentManager(),fragments);

        viewPager.setAdapter(adapter);

        //关联viewpager
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    private void initFragment() {

        fragments = new ArrayList<>();
        fragments.add(new NewPostFragment());
        fragments.add(new HotPostFragment());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ib_community_icon, R.id.ib_community_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_community_icon:
                Toast.makeText(mContext, "图像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_community_message:
                Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
