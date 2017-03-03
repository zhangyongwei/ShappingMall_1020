package com.atguigu.shappingmall_1020.type.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.atguigu.shappingmall_1020.MainActivity;
import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张永卫on 2017/2/22.
 */

public class TypeFragment extends BaseFragment {

    @InjectView(R.id.tl_1)
    SegmentTabLayout tl1;
    @InjectView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @InjectView(R.id.fl_type)
    FrameLayout flType;
    private String[] titles = {"分类", "标签"};

    private ArrayList<BaseFragment> fragments;
    /**
     * 刚才被显示的fragment
     */
    private Fragment tempFragment;

    @Override
    public View initView() {

        Log.e("TAG", "发现视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_type, null);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "分类数据被初始化了");
        initFragment();
        initListener();


        switchFragment(fragments.get(0));

    }

    /**
     * 切换不同的fragment
     *
     * @param currFragment
     */
    private void switchFragment(BaseFragment currFragment) {
        //切换的不是同一个页面
        if (tempFragment != currFragment) {

            MainActivity activity = (MainActivity) mContext;
            //得到FragmentMager
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();

            //如果没有添加就添加
            if (!currFragment.isAdded()) {

                //添加
                ft.add(R.id.fl_type, currFragment);
            } else {
                //显示
                ft.show(currFragment);
            }
            //缓存的隐藏
            if (tempFragment != null) {

                ft.hide(tempFragment);
            }
            //事务提交
            ft.commit();

            //把当前的赋值成缓存的
            tempFragment = currFragment;
        }
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {

        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());

    }

    private void initListener() {
        tl1.setTabData(titles);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();

                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick(R.id.iv_type_search)
    public void onClick() {

    }
}
