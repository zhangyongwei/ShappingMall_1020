package com.atguigu.shappingmall_1020.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.type.bean.TagBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/3.
 */

public class TagGridViewAdapter extends BaseAdapter {


    private final Context mContext;
    private final List<TagBean.ResultBean> datas;

    private int[] colors = {
            Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };

    public TagGridViewAdapter(Context mContext, List<TagBean.ResultBean> result) {

        this.mContext = mContext;
        this.datas = result;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_tag_gridview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);


        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tvTag.setText(datas.get(position).getName());
        //设置不同的颜色
        viewHolder.tvTag.setTextColor(colors[position%colors.length]);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
