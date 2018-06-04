package com.chint.paylibrary;

/**
 * Project:PingPayDemo
 * Author:dyping
 * Date:2017/7/21 11:04
 */

public interface AliPayListener {
     void onPaySuccess(String resultInfo);
     void onPayFailure(String resultInfo);
     void onPayConfirmimg(String resultInfo);
     void onPayCheck(String status);
}
