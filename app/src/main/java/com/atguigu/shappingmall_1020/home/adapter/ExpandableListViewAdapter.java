package com.atguigu.shappingmall_1020.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 张永卫on 2017/3/6.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final ArrayList<String> group;
    private final ArrayList<List<String>> child;
    private int childP;
    private int groupP;

    public ExpandableListViewAdapter(Context mContext, ArrayList<String> group, ArrayList<List<String>> child) {

        this.mContext = mContext;
        this.group = group;
        this.child = child;
    }

    /**
     * 返回多少个组
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return group.size();
    }

    /**
     * 得到某组孩子的个数
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    /**
     * 得到具体孩子
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.group_list_item, null);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置组名
        viewHolder.textView.setText(group.get(groupPosition));

        viewHolder.textView.setPadding(0, 10, 0, 10);

        //设置展开和非展开的状态

        if (isExpanded) {

            viewHolder.imageView.setImageResource(R.drawable.filter_list_selected);
        } else {

            viewHolder.imageView.setImageResource(R.drawable.filter_list_unselected);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.child_list_item, null);

            viewHolder = new ChildViewHolder(convertView);

            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        //设置文本
        if(groupPosition!=0) {

            viewHolder.textView.setText(child.get(groupPosition).get(childPosition));

        }

        //被点击的孩子
        if(childP == childPosition && groupP == groupPosition) {

            //把被点击的孩子的图片--显示
            viewHolder.childImageView.setVisibility(View.VISIBLE);
        }else{

            //把被点击的孩子的图片隐藏
            viewHolder.childImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        //接收位置
        childP = childPosition;
        groupP = groupPosition;

        return true;
    }



    static class ViewHolder {
        @InjectView(R.id.imageView)
        ImageView imageView;
        @InjectView(R.id.textView)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ChildViewHolder {
        @InjectView(R.id.textView)
        TextView textView;
        @InjectView(R.id.childImageView)
        ImageView childImageView;
        @InjectView(R.id.ll_child_root)
        LinearLayout llChildRoot;

        ChildViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
