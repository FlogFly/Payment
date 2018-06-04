package com.chint.paylibrary;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.chint.paylibrary.alipay.PayResult;

import java.util.Map;

/**
 * Project:PingPayDemo
 * Author:dyping
 * Date:2017/7/19 20:00
 */

public class AliPayReq {

    /**
     * ali pay sdk flag
     */
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private Activity mActivity;


    private String orderInfo;
    //支付宝支付监听
    private PayListener mPayListener;
    //是否是沙箱环境
    private boolean isSandBox;

    private Handler mHandler;

    public AliPayReq() {
        super();
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();

                        if (TextUtils.equals(resultStatus, "9000") || TextUtils.equals(resultStatus, "8000")) {
                            if (mPayListener != null) {
                                mPayListener.onPaySuccess();
                            }
                        } else {
                            if (mPayListener != null) {
                                mPayListener.onPayFailure();
                            }
                        }
                        break;
                    }
                    case SDK_AUTH_FLAG: {
                        if (mPayListener != null) {
                            mPayListener.onPayFailure();
                        }
                        break;
                    }
                    default:
                        break;
                }
            }

        };
    }

    /**
     * 发送支付宝支付请求
     */
    public void send() {

        final String orderInfo = this.orderInfo;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                if (isSandBox) {
                    EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                }

                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    public static class Builder {
        //上下文
        private Activity activity;
        //是否处于沙箱环境下
        private boolean isSandBox;

        private String orderInfo;
        private PayListener listener;

        public Builder() {
            super();
        }

        public Builder with(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
            return this;
        }

        public Builder setIsSandBox(boolean isSandBox) {
            this.isSandBox = isSandBox;
            return this;
        }

        public Builder setListener(PayListener listener) {
            this.listener = listener;
            return this;
        }


        public AliPayReq create() {
            AliPayReq aliPayReq = new AliPayReq();
            aliPayReq.mActivity = this.activity;
            aliPayReq.orderInfo = this.orderInfo;
            aliPayReq.mPayListener = this.listener;
            aliPayReq.isSandBox = this.isSandBox;
            return aliPayReq;
        }

    }
}
