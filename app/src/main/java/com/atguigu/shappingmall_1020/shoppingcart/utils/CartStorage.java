package com.atguigu.shappingmall_1020.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.shappingmall_1020.home.bean.GoodsBean;
import com.atguigu.shappingmall_1020.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张永卫on 2017/2/27.
 */

public class CartStorage {

    public static final String JSON_CART = "json_cart";

    private static CartStorage instance;
    private final Context mContext;
    /**
     * SparseArray代替HashMap
     */
    private SparseArray<GoodsBean> sparseArray;

    public CartStorage(Context mContext) {
        this.mContext = mContext;
        sparseArray = new SparseArray<>();
        //从本地获取数据
        listToSparseArray();
    }
    /**
     * 把List的数据转换成SparseArray
     */
    private void listToSparseArray() {

        List<GoodsBean> beanList = getAllData();
        for (int i = 0;i<beanList.size();i++){
            GoodsBean goodsBean = beanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }
    /**
     * 得到所有的数据
     *
     * @return
     */
    public List<GoodsBean> getAllData() {
        return getLocalData();
    }
    /**
     * 得到本地缓存的数据
     *
     * @return
     */
    private List<GoodsBean> getLocalData() {

        List<GoodsBean> goodsBeens = new ArrayList<>();
        //从本地获取数据
        String json = CacheUtils.getString(mContext, JSON_CART);

        if(!TextUtils.isEmpty(json)) {

            //把它转化成列表
            goodsBeens = new Gson().fromJson(json,new TypeToken<List<GoodsBean>>(){}.getType());
        }
        //把json数据解析成list数据

        return goodsBeens;
    }

    /**
     * 懒汉模式
     * @param context
     * @return
     */
    public static CartStorage getInstance(Context context){

        if(instance==null) {

            synchronized (CartStorage.class){

                if(instance==null) {

                    instance = new CartStorage(context);
                }
            }

        }
        return instance;
    }

    /**
     * 添加数据
     */
    public void addData(GoodsBean goodsBean){

        //1.数据添加到sparseArray
        GoodsBean tempGoodsBean = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        //已经保存
        if(tempGoodsBean!=null) {

            tempGoodsBean.setNumber(tempGoodsBean.getNumber()+goodsBean.getNumber());

        }else{

            //没有添加过
            tempGoodsBean = goodsBean;
        }

        //添加到集合中
        sparseArray.put(Integer.parseInt(tempGoodsBean.getProduct_id()),tempGoodsBean);

        //2.保存到本地
        saveLocal();

    }

    /**
     * 删除数据
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean){
        //1.删除数据
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //2.保存到本地
        saveLocal();

    }

    /**
     * 修改数据
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean){
        //1.修改数据
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        //2.保存到本地
        saveLocal();
    }

    /**
     * 保存到本地
     */
    private void saveLocal() {
        //1.把sparseArray转成list
        List<GoodsBean> goodsBeanList = sparseArrayToList();
        //2.使用Gson把list转成json的string类型数据
        String saveJson = new Gson().toJson(goodsBeanList);
        //3.使用CacheUtils缓存数据
        CacheUtils.setString(mContext,JSON_CART,saveJson);
    }

    /**
     * 把sparseArray转成list
     * @return
     */
    private List<GoodsBean> sparseArrayToList() {

        //列表数据
        List<GoodsBean> goodsBeanList = new ArrayList<>();

        for (int i = 0;i<sparseArray.size();i++){

            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }


        return goodsBeanList;
    }
}
