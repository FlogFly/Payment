package com.chint.paylibrary.config;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Project:PingPayDemo
 * Author:dyping
 * Date:2017/7/21 14:06
 */

public class PayType {

    public static final String ALI_PAY = "ali";
    public static final String WX_PAY = "wx";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ALI_PAY, WX_PAY})
    public @interface PayFlavors {
    }

}
