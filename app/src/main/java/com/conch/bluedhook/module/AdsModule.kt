package com.conch.bluedhook.module

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.conch.bluedhook.common.HookConstant
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

/**
 * Created by Benjamin on 2017/11/23.
 */
class AdsModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun removeAds() {
        XposedBridge.log("Now,Starting remove ads")
        removeWelcomeAds()
        removeNearbyAds()
        removeSquareAds()
        removeGameCenter()
    }

    /**
     * remove welcome ads
     */
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

    /**
     * remove nearby ads
     */
    private fun removeNearbyAds() {
        //Grid UI
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.distanceGrid4Manager, loader, "a", MutableList::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val data = param!!.args[0] as MutableList<Any>
                param.args[0] = removeAds(data)
            }
        })
        //List UI
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.distanceListManager, loader, "a", MutableList::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val data = param!!.args[0] as MutableList<Any>
                param.args[0] = removeAds(data)
            }
        })
    }

    /**
     * remove ads result
     * @param data old data
     * @return new data
     */
    private fun removeAds(data: MutableList<Any>): MutableList<Any> {
        data.removeAll {
            val field = it.javaClass.getField("is_ads")
            field.isAccessible = true
            field.get(it) == 1
        }
        return data
    }

    /**
     * remove discover square top's viewpager
     */
    private fun removeSquareAds() {
        val bluedEntityA = XposedHelpers.findClass(HookConstant.bluedEntityA, loader)
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.discoverSquareFragment, loader, "a", bluedEntityA, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                param!!.args[0] = null
            }
        })
    }

    /**
     * remove GameCenter
     */
    private fun removeGameCenter() {
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.homePageMore, loader, "a", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                param!!.args[0] = null
            }
        })
    }
}