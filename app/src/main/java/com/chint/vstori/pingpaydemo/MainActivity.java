package com.chint.vstori.pingpaydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chint.paylibrary.GatherChooseListener;
import com.chint.paylibrary.PayChooseListener;
import com.chint.paylibrary.PayListener;
import com.chint.paylibrary.PayOne;
import com.chint.paylibrary.config.PayType;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {


    @NotEmpty(message = "请输入商品名称")
    @BindView(R.id.et_goods_title)
    EditText etGoodsTitle;
    @BindView(R.id.et_goods_content)
    EditText etGoodsContent;
    @NotEmpty(message = "请输入金额")
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.pay_btn)
    Button payButton;
    @BindView(R.id.gather_btn)
    Button gatherBtn;

    Validator validator;
    private boolean isValideData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);


        PayOne.getInstance().enablePayChannels(PayType.ALI_PAY, PayType.WX_PAY);//设置付款的种类
        PayOne.getInstance().enableGatherChannels(PayType.ALI_PAY, PayType.WX_PAY);//设置收款的种类
        PayOne.getInstance().setSandBox(false);//支付宝支付沙箱环境

        PayOne.getInstance().setPayListener(new PayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayFailure() {
                Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        });

        PayOne.getInstance().setPayChooseListener(new PayChooseListener() {
            @Override
            public void chooseType(String payType) {
                switch (payType) {
                    case PayType.ALI_PAY:
                        Toast.makeText(MainActivity.this, "选择了支付宝方式付款", Toast.LENGTH_SHORT).show();
                        PayOne.getInstance().payOrder(PayType.ALI_PAY, getOrderInfo());
                        break;
                    case PayType.WX_PAY:
                        Toast.makeText(MainActivity.this, "选择了微信方式付款", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;

                }
            }
        });

        PayOne.getInstance().setGatherChooseListener(new GatherChooseListener() {
            @Override
            public void chooseType(String payType) {
                switch (payType) {
                    case PayType.ALI_PAY:
                        Toast.makeText(MainActivity.this, "选择了支付宝方式收款", Toast.LENGTH_SHORT).show();

                        break;
                    case PayType.WX_PAY:
                        Toast.makeText(MainActivity.this, "选择了微信方式收款", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;

                }
            }
        });
    }

    private String getOrderInfo2() {
        return "app_id=2017092008836325&biz_content=%7B%22extend_params%22%3A%22%7B%5C%22sys_service_provider_id%5C%22%3A%5C%222088421887484208%5C%22%7D%22%2C%22out_trade_no%22%3A%2220180326155149108JAy%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22abc%22%2C%22timeout_express%22%3Anull%2C%22body%22%3A%2211%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.92.126.178%3A8083%2FChintAggPay%2Falipay%2Fnotify.do&sign=dFpfgNyxnataUY3waQEJZgUJsa%2Bf8rJNrDqOAEZjVfmJx1Zp8Yd3oZJ7kgBqUytpbWzszD3UwDmz3UBI0jt8uPkdVbPYlP%2F40dikmxW7dyrvTn%2B5ILNlBlXaXkC4LIP17xn2mrwLmFvv%2B%2F7GdVR5AAbR%2FF57LYF7JQmG2blCZFBCJeCJYlrfWluC1jnXA0xJ1vTwrY5hQg1olBFp7LWILexKrjraOmQbs4kbVOM5lNX50mnm9cyRPN60PPhAlOQMWFpCxcdxU5x8w98DG34gFxmh2wf4ywd1VEDKGAsjvcFG%2Fj0bH%2BM2lwCUU6RVydiYrZFU%2FA5C4fZDiyqpPuRfDQ%3D%3D&sign_type=RSA2&timestamp=2018-03-26+15%3A51%3A49&version=1.0";
        //return "app_id=2017092008836325&biz_content=%7B%22extend_params%22%3A%22%7B%5C%22sys_service_provider_id%5C%22%3A%5C%222088421887484208%5C%22%7D%22%2C%22out_trade_no%22%3A%22A001201711021032%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22abc%22%2C%22timeout_express%22%3Anull%2C%22body%22%3A%2211%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.92.126.178%3A8084%2FChintAggPay%2Falipay%2Fnotify.do&sign=wA9fL3vp5bCKQ2%2FiNPUwiIvHfQegP%2FkBIsw2VujRcxDOgv9ktigxl%2BcwYXIqNGEo%2F9aUzCwgvKE9vuvp25xN7r4UKzROGW0P5LVykHtNCiB5k2UsyoO4j02xWspN9aWpLhNA%2BqDENNV%2BQbTKYD2gdOC00VNfL42Rpx8jSDpiTzmCJlIriGk3bCiQiBFAUNp02dV%2FvQr5Mn6OopxR6yyJfxWtOY24DOOqoDh9adx37uguWNSrCDVM5yZ1CRqxvLy5oM1KEVDPPnGWu6fB17%2FRP5DcR9yfnZT7oHS3TQ3ij9hHHLY9z8L26VqEVsIIUxfYhujFzSviqRnj%2BNFP2DU44w%3D%3D&sign_type=RSA2&timestamp=2017-11-02+10%3A32%3A56&version=1.0";
        //  return  "app_id=2017092008836325&biz_content=%7B%22extend_params%22%3A%22%7B%5C%22sys_service_provider_id%5C%22%3A%5C%222088421887484208%5C%22%7D%22%2C%22out_trade_no%22%3A%22A001201710271630%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22abc%22%2C%22timeout_express%22%3Anull%2C%22body%22%3A%2211%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.92.126.178%3A8084%2FChintAggPay%2Falipay%2Fnotify.do&sign=aatvYa1Uv5Saz5xpm59bapRZi%2BpNfbIT7J%2BUjeF2ThOy2iaUs3qLjEuMwxEvoYHaIwv%2BV5mn0ElPgl7%2F84lDAOCf4tzzg1RWgsl7Ig9DiK1Xbgeeaekp0ur4pPzOR4w78h%2FrhCXIDeWpI7IGqY5o7uzCFvgWoVK%2B2cT7gHe0vygLK02Az%2FiKAVzeeIdvMZnlXEqX5R6PYVEkeCkWps1fKULzNVDwj4GbDUj9cCwRADZMOhTlRRX0JWbyjZ4upwVmVXyIXaIRg5ymNNI2bR1zcFc9bixscJYOFqwNMVmKik4CvOnogfjU1WqOpZMg2yxo7lqDKpglIA2RWQy7YnobvA%3D%3D&sign_type=RSA2&timestamp=2017-10-27+16%3A30%3A21&version=1.0";
    }


    /**
     * 此处模拟从后台请求获取后的订单信息，实际业务中，请从后台接口获取。
     */
    private String getOrderInfo() {
        //客服的id
        String APPID = "2016080400168251";

        /** 支付宝账户登录授权业务：入参pid值 */
        String PID = "";
        /** 支付宝账户登录授权业务：入参target_id值 */
        String TARGET_ID = "";
        //客服的密钥
        String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDAyHvRscycaj1iNz3OTw9rBmChs4ZsUn6AY0A/e34hee591nGgyZ5+n6HeATaJAOGvNW9rmUXrOtGyLxrfWyhp+oikjd2XVHw+za2tGAywUAk5wlnG7xreXKLDJC9FD5Ujb+0T7WPPxsf80cr48Gygw5+pupxG4LW1D/z88oqyeDGxqBUnslJoM5hIiqhVepMiWg9+zIGhsR0+yn6vTdo04sLhy7SrZsCAhmVAFMwVqqeqxHgH2em0gwExZReRm7Dx36Z7Zxy2bjkkdvN7zRWp0GksdOwDxlxmeiYeONjz2EXVJHLvlc0RVefK08cnuMvZsF3y2HEiIx5ti6kdivFrAgMBAAECggEAF5XvoB4SnGhbDjMX+q+tgTiMhfwCzCnjlC2QZLPlII9cYETmLfe8zl0VXqXjWCulcNwmv5FG/pp5oUUmEoGClkSyhv9cRvFtKfnb77trgf6owkpG1ZHXRbErsJgyuE5JvsYoyTFX7smxGftIy+AOSjTkyc0s5XcEhBxqpT+/PEpF1o5DOC/15/Y1p1oYy70DAhTfiQ3NKIO7frJ09w7OBrjKDAP6nmDiZMvo/3K47U3UOJgwtGciD5LwgJLD3AgWqHBf/VxpI9o78U3rLGronQRbHhQBxn3FdkQKgiBksBZqi/OYB3ANCMnRerKS4mX6WhvD0PDiaWGZ/3/DwVQM4QKBgQDlrkXlTq5sH7azN9PfAk2J/MgbjO05BGi4KiJThQkW+QcwteU8siELZwmebfXO0Qi+/9Fc15c9m1FqetP5PqlYqa65o/E6ydqQO5F7k2h9nWzF1B0lQ8fWpZfIgYURpw8LCOTBYS2th+okSDOQWp9GFU+ZR/RhTSj9aUg8xaR8eQKBgQDW38/2jeHszIn/Y1ogTW9bzkhGYro9fki2vT04VcMZhNHGw240+h5IjdLs5k7/T4cat7UwHUzf3QJVJfSoGbupbTHei9eAufVuLTogafRt59UbQN8oDRVSF65MTTx4CfUDzkmn4YuMYpGvkhPOry/52uphijzoGtXg3qjZ0nJcAwKBgQDVxRcu7sVuwzGjqpFa3eTXSlvhMJBWYiEpT6X1QDwOkqc7kgCTNmOFHliYiVWgMXRHeQgetlYAs//Z8Ao80DKD4CJMjCbohZkUZyzn7HHzgEMN+XdLCMQFpsgXiV0V6fwZVCS9S7pc6cRmEoFTmNTnQMx+KLdunIdLHONsFLQcWQKBgH09GgNEkyfn147pI4CsYXmK9AlCRfbNgigwiFwrcHmRNou5IKT0G26Cayv02Jpqif6CkLKogUQBlvh2FPFdfkm0AVsK+uJmgKxk4/o8h2D/vPATkX3Qklq0vrxuUA5PD2XWeIvHJAUA9pT0eWMALOBePjn3zqQh8AMPBC9hP5JtAoGALfAr/wrlPnHGSSBqXD5KFbwGSSKLAQEtqEP41Gtd4AFU4RD1qGF31wx2u7uwUSYYT02uScnRzRh+ojNRVVV+LfG7nJylYQdu9az93oSR+1UaHan3G9/a5kHqUkUgf7QpIJbZRpWCy1Hj5l0Ahg81XExvkoCxzgZI1jIPAfJlh3c=";

        String RSA_PRIVATE = "";

        int SDK_PAY_FLAG = 1;
        int SDK_AUTH_FLAG = 2;


        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID,
                rsa2,
                etGoodsTitle.getText().toString(),
                etGoodsContent.getText().toString(),
                etMoney.getText().toString()
        );
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);

        final String orderInfo = orderParam + "&" + sign;
        Log.v("dyp", "orderInfo:" + orderInfo);
        return orderInfo;
    }

    @Override
    public void onValidationSucceeded() {
        isValideData = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        isValideData = false;
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick({R.id.pay_btn, R.id.gather_btn, R.id.ali_btn, R.id.wx_btn})
    public void onViewClicked(View view) {
      /*  validator.validate();
        if (!isValideData) {
            return;
        }*/
        switch (view.getId()) {
            case R.id.pay_btn:
                PayOne.getInstance().showPaymentChannels(MainActivity.this);
                break;
            case R.id.gather_btn:
                PayOne.getInstance().showGatherChannels(MainActivity.this);
                break;
            case R.id.ali_btn:

                PayOne.getInstance().payOrder(MainActivity.this, PayType.ALI_PAY, getOrderInfo2());
                break;
            case R.id.wx_btn:
                break;
        }
    }
}
