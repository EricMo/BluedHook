package com.conch.bluedhook


import android.util.Log
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.SelfHookConstant
import com.conch.bluedhook.module.*
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Benjamin on 2017/7/3.
 */
class MainModule : IXposedHookLoadPackage {

    /**
     * hook class
     */
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.appInfo == null) {
            return
        }
        //active self
        if (lpparam.packageName == SelfHookConstant.processName) {
            ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
                XposedHelpers.findAndHookMethod(SelfHookConstant.mainActivity, mClassLoader, "isActivated", object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam?) {
                        Log.d("fake", "just for vxp")
                        param!!.result = true
                    }
                })
            })
        }
        //hook other
        if (lpparam.packageName == HookConstant.processName) {
            ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
                //ad Module
                AdsModule(mClassLoader, mContext).removeAds()
                //Message Module
                MessageModule(mClassLoader, mContext).hookMessage()
                //layout
                DynamicLayoutModule(mClassLoader, mContext).layout()
                //vip
                VipModule(mClassLoader,mContext).hookVip()
            })
        }
    }
}
