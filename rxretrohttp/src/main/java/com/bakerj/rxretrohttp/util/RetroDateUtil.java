package com.bakerj.rxretrohttp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.bakerj.rxretrohttp.RxRetroHttp;

import java.util.Date;

public class RetroDateUtil {
    private static final String SP_NAME_DATE = "SP_NAME_DATE";
    private static final String SP_KEY_DATE_FIX = "SP_KEY_DATE_FIX";

    public static Date getFixedDate() {
        return new Date(System.currentTimeMillis() +
                getSP(SP_NAME_DATE).getLong(SP_KEY_DATE_FIX, 0L));
    }

    public static void setDateFix(String dateService) {
        getSP(SP_NAME_DATE).edit().putLong(SP_KEY_DATE_FIX,
                new Date(dateService).getTime() - System.currentTimeMillis()).apply();

    }

    private static SharedPreferences getSP(String spName) {
        return RxRetroHttp.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }
}
