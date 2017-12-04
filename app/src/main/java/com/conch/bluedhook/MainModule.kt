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
     * 开始劫持
     */
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (isHookSuccessful || lpparam.appInfo == null || lpparam.packageName != HookConstant.processName) {
            return
        }
        XposedBridge.log("Voilà!we are in Blued!")
        ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
            isHookSuccessful = true
            //欢迎
            Toast.makeText(mContext, "欢迎使用Blued模块", Toast.LENGTH_SHORT).show()
            //消息模块
            ChatModule(mClassLoader, mContext).hookChat()
            //广告模块
            AdsModule(mClassLoader, mContext).removeAds()
        })
    }
}
