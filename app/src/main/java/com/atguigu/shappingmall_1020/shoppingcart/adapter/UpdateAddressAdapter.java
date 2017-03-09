package com.atguigu.shappingmall_1020.shoppingcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.shoppingcart.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张永卫on 2017/3/8.
 */

public class UpdateAddressAdapter extends BaseAdapter {


    private List<AddressBean> list;			//地址数据集合
    private LayoutInflater inflater;		//布局填充器
    public UpdateAddressAdapter(Context context, ArrayList<AddressBean> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressBean bean = list.get(position);
        ViewHolder holder = null;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.updateaddress_item, null);
            holder.addresseeItemName = (TextView)
                    convertView.findViewById(R.id.addresseeItemName);
            holder.smearedItemAddress = (TextView)
                    convertView.findViewById(R.id.smearedItemAddress);
            holder.detailItemAddress = (TextView)
                    convertView.findViewById(R.id.detailItemAddress);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.addresseeItemName.setText(bean.getName());
        holder.smearedItemAddress.setText(bean.getSmearedAddress());
        holder.detailItemAddress.setText(bean.getDetailAddress());

        return convertView;
    }

    private final class ViewHolder{
        public TextView addresseeItemName;	//收件人姓名
        public TextView smearedItemAddress;	//区地址
        public TextView detailItemAddress;	//详细地址
    }
}
