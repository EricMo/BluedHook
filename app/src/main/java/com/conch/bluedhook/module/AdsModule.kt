package com.conch.bluedhook.module

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.ReflectionUtils
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

/**
 * Created by Benjamin on 2017/11/23.
 */
class AdsModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun removeAds() {
        XposedBridge.log("adsModule loads successfully")
        removeWelcomeAds()
        removeNearbyAds()
        removeSquareAds()
        removeMoney()
        removeGameCenter()
    }

    /**
     * remove welcome ads
     */
    private fun removeWelcomeAds() {
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.welcomeUI, loader, "a", Context::class.java, Boolean::class.java, Boolean::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                XposedBridge.log("Remove welcome's Ads Successfully")
                if (param!!.args[1] as Boolean) {
                    //don't show Ads
                    param.args[1] = false
                }
            }
        })
    }

    /**
     * remove nearby ads
     *   public static final int fl_ad = 2131559167;
     *   public static final int fl_ads = 2131558875;
     */
    private fun removeNearbyAds() {
        //Grid UI
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.distanceGrid4Adapter, loader, "getView", Int::class.java, View::class.java, ViewGroup::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val item = removeAds(param)
                param!!.result = item
            }
        })
        //List UI
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.distanceListAdapter, loader, "getView", Int::class.java, View::class.java, ViewGroup::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val item = removeAds(param)
                param!!.result = item
            }
        })
    }

    /**
     * filter the item
     * if(R.id.2131559167).visibility==VISIBLE,the item is a ads.so we need remove it
     */
    private fun removeAds(param: XC_MethodHook.MethodHookParam?): View {
        val item = param!!.result as View
        val ads = item.findViewById(2131559167)
        if (ads!!.visibility == View.VISIBLE) {
            ads.visibility = GONE
            XposedBridge.log("Remove nearby's Ads Successfully")
        }
        return item
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

    /**
     * remove Money
     */
    private fun removeMoney() {
        XposedHelpers.findAndHookMethod(HookConstant.processName + HookConstant.homePageMore, loader, "b", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val classZ = param!!.thisObject.javaClass

                val nField = classZ.getDeclaredField("n")
                nField.isAccessible = true
                (nField.get(param.thisObject) as View).visibility = GONE

                val qField = classZ.getDeclaredField("q")
                qField.isAccessible = true
                (qField.get(param.thisObject) as View).visibility = GONE
            }
        })
    }
}