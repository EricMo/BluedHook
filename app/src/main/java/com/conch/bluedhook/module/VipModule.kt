package com.conch.bluedhook.module

import android.content.Context
import com.conch.bluedhook.common.HookConstant
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

/**
 * @Description vip
 * @Author Benjamin
 * @CreateDate 2018-07-01 10:03
 **/
class VipModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun hookVip() {
        unlockVIP()
    }

    private fun unlockVIP() {
        XposedHelpers.findAndHookMethod(HookConstant.userInfo, loader, "p", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                //login info
                val loginResult = param!!.result
                //change to vip
                XposedHelpers.setIntField(loginResult, "vip_grade", 2)
            }
        })
    }
}