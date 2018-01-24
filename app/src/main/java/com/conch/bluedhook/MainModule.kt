package com.conch.bluedhook


import android.widget.Toast
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.module.AdsModule
import com.conch.bluedhook.module.ApplicationModule
import com.conch.bluedhook.module.MessageModule
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage


/**
 * Created by Benjamin on 2017/7/3.
 */
class MainModule : IXposedHookLoadPackage {
        var isHookSuccessful = false
    /**
     * start to hook
     */
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (isHookSuccessful || lpparam.appInfo == null || lpparam.packageName != HookConstant.processName) {
            return
        }
        ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
            isHookSuccessful = true
            Toast.makeText(mContext, "Welcome to use Blued module", Toast.LENGTH_SHORT).show()
            //Message Module
            MessageModule(mClassLoader, mContext).hookMessage()
            //ad Module
            AdsModule(mClassLoader, mContext).removeAds()
        })
    }
}
