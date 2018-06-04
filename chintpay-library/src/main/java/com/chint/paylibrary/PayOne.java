package com.chint.paylibrary;

import android.app.Activity;
import android.widget.Toast;

import com.chint.paylibrary.config.PayType;
import com.chint.paylibrary.widget.BottomDialog;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

/**
 * Project:PingPayDemo
 * Author:dyping
 * Date:2017/7/20 11:11
 */

public class PayOne {

    private boolean isSandBox;
    private static PayOne mInstance;
    private String[] payChannels;
    private String[] gatherChannels;
    private Activity aty;
    private PayListener mPayListener;
    private GatherChooseListener gatherChooseListener;
    private PayChooseListener payChooseListener;
    private IWXAPI api;
    private final String DIALOG_FRAGEMTN_TAG = "pay_dialog";


    private static final Object mLock = new Object();

    public static PayOne getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new PayOne();
                }
            }
        }
        return mInstance;
    }

    public void enablePayChannels(@PayType.PayFlavors String... payType) {
        payChannels = payType;
      /*  payChannels = new String[payType.length];
        for (int i = 0; i < payChannels.length; i++) {
            payChannels[i] = payType[i];
        }*/
    }

    public void enableGatherChannels(@PayType.PayFlavors String... payType) {
        gatherChannels = payType;
        /*gatherChannels = new String[payType.length];
        for (int i = 0; i < gatherChannels.length; i++) {
            gatherChannels[i] = payType[i];
        }*/
    }

    public void showPaymentChannels(final Activity aty) {
        this.aty = aty;
        if (payChannels != null && payChannels.length > 0) {

            BottomDialog dialog = BottomDialog.newInstance(aty.getString(R.string.choose_pay_type), payChannels);
            dialog.show(aty.getFragmentManager(), DIALOG_FRAGEMTN_TAG);
            dialog.setListener(new BottomDialog.OnClickListener() {
                @Override
                public void click(int position) {
                    //payOrder(payChannels[position], infoStr);
                    if (payChooseListener != null) {
                        payChooseListener.chooseType(payChannels[position]);
                    }
                }
            });
        } else {
            Toast.makeText(aty, R.string.no_pay_channel, Toast.LENGTH_SHORT).show();
        }
    }

    public void showGatherChannels(final Activity aty) {
        this.aty = aty;
        if (gatherChannels != null && gatherChannels.length > 0) {
            BottomDialog dialog = BottomDialog.newInstance(aty.getString(R.string.choose_gather_type), gatherChannels);
            dialog.show(aty.getFragmentManager(), "gather_dialog");
            dialog.setListener(new BottomDialog.OnClickListener() {
                @Override
                public void click(int position) {
                    if (gatherChooseListener != null) {
                        gatherChooseListener.chooseType(gatherChannels[position]);
                    }
                }
            });
        } else {
            Toast.makeText(aty, R.string.no_gather_channel, Toast.LENGTH_SHORT).show();
        }
    }

    public void payOrder(@PayType.PayFlavors String type, String infoStr) {
        switch (type) {
            case PayType.ALI_PAY:
                aliPay(infoStr);
                break;
            case PayType.WX_PAY:
                wxPay(infoStr);
                break;
        }
    }

    public void payOrder(Activity aty, @PayType.PayFlavors String type, String infoStr) {
        this.aty = aty;
        switch (type) {
            case PayType.ALI_PAY:
                aliPay(infoStr);
                break;
            case PayType.WX_PAY:
                wxPay(infoStr);
                break;
        }
    }

    public void setPayListener(PayListener mPayListener) {
        this.mPayListener = mPayListener;
    }


    public void setPayChooseListener(PayChooseListener payChooseListener) {
        this.payChooseListener = payChooseListener;
    }


    public void setGatherChooseListener(GatherChooseListener gatherChooseListener) {
        this.gatherChooseListener = gatherChooseListener;
    }


    private void aliPay(String infoStr) {

        //发送支付宝支付请求
        AliPayReq aliPayReq = new AliPayReq.Builder()
                .with(aty)
                .setIsSandBox(isSandBox)
                .setOrderInfo(infoStr)
                .setListener(mPayListener)
                .create();

        PayAPI.getInstance().sendPayRequest(aliPayReq);
    }

    private void wxPay(String infoStr) {
        try {


            JSONObject json = new JSONObject(infoStr);
            api = WXAPIFactory.createWXAPI(aty, json.getString("api"));
            String appId = json.getString("appid");
            String partnerId = json.getString("partnerid");
            String prepayId = json.getString("prepayid");
            String nonceStr = json.getString("noncestr");
            String timeStamp = json.getString("timestamp");
            String packageValue = json.getString("package");
            String sign = json.getString("sign");
            String extData = "app data"; // optional


            WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                    .with(aty) //activity实例
                    .setAppId(appId) //微信支付AppID
                    .setPartnerId(partnerId)//微信支付商户号
                    .setPrepayId(prepayId)//预支付码
                    .setPackageValue(packageValue)//"Sign=WXPay"
                    .setNonceStr(nonceStr)
                    .setTimeStamp(timeStamp)//时间戳
                    .setSign(sign)//签名
                    .setListener(mPayListener)
                    .create();


        /*    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                    .with(aty) //activity实例
                    .setAppId("wxef73901fda517004") //微信支付AppID
                    .setPartnerId("1484861852")//微信支付商户号
                    .setPrepayId(prepayId)//预支付码
                    .setPackageValue("Sign=WXPay")//"Sign=WXPay"
                    .setNonceStr("")
                    .setTimeStamp(System.currentTimeMillis())//时间戳
                    .setSign(sign)//签名
                    .setListener(mPayListener)
                    .create();
*/

            PayAPI.getInstance().sendPayRequest(wechatPayReq);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(aty, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setSandBox(boolean sandBox) {
        isSandBox = sandBox;
    }

    public boolean isSandBox() {
        return isSandBox;
    }
}
