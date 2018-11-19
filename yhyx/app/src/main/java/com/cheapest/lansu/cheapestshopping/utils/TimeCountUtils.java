package com.cheapest.lansu.cheapestshopping.utils;

import android.os.CountDownTimer;

/**
 * 倒计时
 *
 * @author Sunqk
 * @date 2016/1/16
 */
public class TimeCountUtils extends CountDownTimer {
    private CountDownListener mCountDownFinishListener;

    /**
     * 倒计时构造函数
     */
    public TimeCountUtils(long millisInFuture, long countDownInterval){
        super(millisInFuture, countDownInterval);
    }

    public void addCountDownListener(CountDownListener listener){
        mCountDownFinishListener = listener;
    }

    /**
     * 重写剩余时间
     */
    @Override
    public void onTick(long millisUntilFinished){
        mCountDownFinishListener.countDownOnTick(millisUntilFinished);
    }

    /**
     * 倒计时结束后执行
     */
    @Override
    public void onFinish(){
        mCountDownFinishListener.countDownFinish();
    }

    /**
     * 倒计时结束回调
     */
    public interface CountDownListener{
        void countDownOnTick(long millisUntilFinished);

        void countDownFinish();
    }
}