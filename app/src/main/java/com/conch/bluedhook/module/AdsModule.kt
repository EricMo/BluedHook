package com.conch.bluedhook.module

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.conch.bluedhook.common.HookConstant
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import junit.framework.Test

/**
 * Created by Benjamin on 2017/11/23.
 */
class AdsModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {

    fun removeAds() {
        XposedBridge.log("Now,Starting remove ads")
        removeWelcomeAds()
        removeNearbyAds()
    }

    private fun removeWelcomeAds() {
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.welcomeUI, loader, "a", Context::class.java, Boolean::class.java, Boolean::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                XposedBridge.log("Hook welcome Ads Successfully")
                if (param!!.args[1] as Boolean) {
                    //don't show Ads
                    param.args[1] = false
                }
            }
        })
    }

    private fun removeNearbyAds() {
        val userFindResult = XposedHelpers.findClass(HookConstant.processName +HookConstant.userFindResult, loader)
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.distanceUI, loader, "b", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                
            }
        })
    }
}