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

    /**
     * 开始劫持
     */
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != HookConstant.processName)
            return
        XposedBridge.log("Voilà!we are in Blued!")
        ApplicationModule.hookApplicationContext(lpparam, { mContext, mClassLoader ->
            //欢迎
            Toast.makeText(mContext, R.string.welcome, Toast.LENGTH_SHORT).show()
            //消息模块
            ChatModule(mClassLoader, mContext).hookChat()
            //广告模块
            AdsModule(mClassLoader, mContext).removeAds()
        })
    }
}
