package com.atguigu.shappingmall_1020.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.atguigu.shappingmall_1020.dao.DaoMaster;
import com.atguigu.shappingmall_1020.dao.DaoSession;
import com.atguigu.shappingmall_1020.dao.GreenBean;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 张永卫on 2017/2/22.
 */

public class MyApplication extends Application {
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        initOkhttpUtils();
        ZXingLibrary.initDisplayOpinion(this);

        //配置数据库
        setupDatabase();
        daoSession.loadAll(GreenBean.class);
    }

    private void initOkhttpUtils() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
