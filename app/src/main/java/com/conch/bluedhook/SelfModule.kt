package com.conch.bluedhook

import android.util.Log
import com.conch.bluedhook.common.SelfHookConstant
import com.conch.bluedhook.module.ApplicationModule
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by Benjamin on 2018/1/24.
 */
class SelfModule : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam!!.packageName == SelfHookConstant.processName) {
            ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
                active(mClassLoader)
            })
        }
    }

    private fun active(mClassLoader: ClassLoader) {
        XposedHelpers.findAndHookMethod(SelfHookConstant.mainActivity, mClassLoader, "isActivated", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                Log.d("fake", "just for vxp")
                param!!.result = true
            }
        })
    }
}