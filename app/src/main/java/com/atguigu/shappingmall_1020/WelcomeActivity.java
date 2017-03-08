package com.atguigu.shappingmall_1020;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class WelcomeActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //发送延迟消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startMainActivity();
            }
        }, 2000);

    }

    /**
     * 跳转页面
     */
    private void startMainActivity() {

        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);

        startActivity(intent);
       //销毁当前页面
        finish();
    }

    /**
     * 触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN) {

            startMainActivity();

            return true;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除消息
        handler.removeCallbacksAndMessages(null);
    }
}
