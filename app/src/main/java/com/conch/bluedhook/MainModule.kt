package com.conch.bluedhook


import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.module.AdsModule
import com.conch.bluedhook.module.ApplicationModule
import com.conch.bluedhook.module.PreferenceModule
import com.conch.bluedhook.module.MessageModule
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Benjamin on 2017/7/3.
 */
class MainModule : IXposedHookLoadPackage {
    /**
     * start to hook
     */
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.appInfo == null || lpparam.packageName != HookConstant.processName) {
            return
        }
        ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
            //ad Module
            AdsModule(mClassLoader, mContext).removeAds()
            //Message Module
            MessageModule(mClassLoader, mContext).hookMessage()
            //Location Module
            PreferenceModule(mClassLoader, mContext).modifyPreference()
        })
    }

}
