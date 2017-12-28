package com.conch.bluedhook


import android.widget.Toast
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.module.AdsModule
import com.conch.bluedhook.module.ApplicationModule
import com.conch.bluedhook.module.ChatModule
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
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
        XposedBridge.log("Voilà!we are in Blued!")
        ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
            isHookSuccessful = true
            Toast.makeText(mContext, "Welcome to use Blued module", Toast.LENGTH_SHORT).show()
            //Message Module
            ChatModule(mClassLoader, mContext).hookChat()
            //ad Module
            AdsModule(mClassLoader, mContext).removeAds()
        })
    }
}
