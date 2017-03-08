package com.atguigu.shappingmall_1020.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shappingmall_1020.R;
import com.atguigu.shappingmall_1020.app.MyApplication;
import com.atguigu.shappingmall_1020.dao.GreenBean;
import com.atguigu.shappingmall_1020.dao.GreenBeanDao;
import com.atguigu.shappingmall_1020.utils.JsonParser;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @InjectView(R.id.tv_search)
    EditText tvSearch;
    @InjectView(R.id.iv_search_voice)
    ImageView ivSearchVoice;
    @InjectView(R.id.tv_search_go)
    TextView tvSearchGo;
    @InjectView(R.id.ll_hot_search)
    LinearLayout llHotSearch;
    @InjectView(R.id.hsl_hot_search)
    HorizontalScrollView hslHotSearch;
    @InjectView(R.id.lv_search)
    ListView lvSearch;
    @InjectView(R.id.btn_clear)
    Button btnClear;
    @InjectView(R.id.ll_history)
    LinearLayout llHistory;
    @InjectView(R.id.activity_search)
    LinearLayout activitySearch;


    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private GreenBeanDao greenBeanDao;

    private ArrayList<String> list = new ArrayList<>();
    private List<GreenBean> greenBeen;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=587ad331");
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        greenBeanDao = MyApplication.getDaoInstant().getGreenBeanDao();
        //加载数据
        getData();

    }

    /**
     * 每次进来先获取数据 看数据库中有没有数据
     */
    private void getData() {

        greenBeen = greenBeanDao.loadAll();
        
        if(greenBeen!=null && greenBeen.size()>0) {

            for (int i = 0; i <greenBeen.size() ; i++) {

                list.add(greenBeen.get(i).getName());
            }

            adapter = new ArrayAdapter(this,android.R.layout.test_list_item,list);

            lvSearch.setAdapter(adapter);
        }else{

            btnClear.setVisibility(View.GONE);

        }
    }

    @OnClick({R.id.iv_search_voice, R.id.tv_search_go, R.id.btn_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_voice:

                showDialogVoice();

                break;
            case R.id.tv_search_go:

                String name = tvSearch.getText().toString();
                
                if(TextUtils.isEmpty(name)) {

                    Toast.makeText(SearchActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }else{

                    GreenBean greenBean = new GreenBean(null,name);

                    greenBeanDao.insert(greenBean);

                    list.add(greenBean.getName());

                    if(adapter!=null) {

                        adapter.notifyDataSetChanged();
                    }

                    btnClear.setVisibility(View.VISIBLE);

                    startActivity(new Intent(this,GoodsListActivity.class));

                }

                break;
            case R.id.btn_clear:

                List<GreenBean> greenBeen = greenBeanDao.loadAll();

                for (int i = 0; i <greenBeen.size() ; i++) {

                    greenBeanDao.deleteByKey(greenBeen.get(i).getId());
                }

                btnClear.setVisibility(View.GONE);

                list.clear();

                adapter.notifyDataSetChanged();

                break;
        }
    }

    private void showDialogVoice() {

        RecognizerDialog mDialog = new RecognizerDialog(this,new myInitListener());

        mDialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT,"mandarin");

        mDialog.setListener(new MyRecognizerDialogListener());
        mDialog.show();

    }
    class MyRecognizerDialogListener implements RecognizerDialogListener {

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            System.out.println(result);

            String text = JsonParser.parseIatResult(recognizerResult.getResultString());

            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            String reulst = resultBuffer.toString();
            reulst = reulst.replace("。","");
            tvSearch.setText(reulst);
            tvSearch.setSelection(tvSearch.length());

        }

        @Override
        public void onError(SpeechError speechError) {
            Toast.makeText(SearchActivity.this, "出错了哦", Toast.LENGTH_SHORT).show();
        }
    }


    class myInitListener implements InitListener {

        @Override
        public void onInit(int i) {

        }
    }


}
