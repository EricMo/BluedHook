package com.conch.bluedhook

import com.conch.bluedhook.common.SelfHookConstant
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by Benjamin on 2018/1/24.
 */
class SelfModule : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam!!.packageName == SelfHookConstant.processName) {
            XposedHelpers.findAndHookMethod(SelfHookConstant.mainActivity, lpparam.classLoader, "isActivated", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    param!!.result = true
                }
            })
        }
    }
}