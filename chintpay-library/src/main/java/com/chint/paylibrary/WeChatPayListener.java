package com.chint.paylibrary;

/**
 * Project:PingPayDemo
 * Author:dyping
 * Date:2017/7/27 13:44
 */

public interface WeChatPayListener {

    void onPaySuccess(int errorCode);

    void onPayFailure(int errorCode);
}
