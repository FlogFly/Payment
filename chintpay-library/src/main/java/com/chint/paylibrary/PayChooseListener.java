package com.chint.paylibrary;

import com.chint.paylibrary.config.PayType;

/**
 * Project:PingPayDemo
 * Author:dyping
 * Date:2017/7/25 19:57
 */

public interface PayChooseListener {

      void chooseType(@PayType.PayFlavors String payType);
}
