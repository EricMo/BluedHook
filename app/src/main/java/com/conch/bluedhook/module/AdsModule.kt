package com.conch.bluedhook.module

import android.content.Context
import android.view.View
import android.view.View.GONE
import com.conch.bluedhook.common.HookConstant
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
        XposedHelpers.findAndHookMethod(HookConstant.welcomeUI, loader, "a", Context::class.java, Boolean::class.java, Boolean::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
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
        XposedHelpers.findAndHookMethod(HookConstant.distanceGrid4Adapter, loader, "a", XposedHelpers.findClassIfExists(HookConstant.distanceGrid4AdapterHolder, loader), XposedHelpers.findClassIfExists(HookConstant.nearByWithAds, loader), Int::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                XposedBridge.log("This item's type is 2,so We do not need to perform this method")
                param!!.result = null
            }
        })
        //List UI
        XposedHelpers.findAndHookMethod(HookConstant.distanceListAdapter, loader, "a", XposedHelpers.findClassIfExists(HookConstant.distanceListAdapterHolder, loader), XposedHelpers.findClassIfExists(HookConstant.nearByWithAds, loader), Int::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                XposedBridge.log("This item's type is 2,so We do not need to perform this method")
                param!!.result = null
            }
        })
    }


    /**
     * remove discover square top's viewpager
     */
    private fun removeSquareAds() {
        val bluedEntityA = XposedHelpers.findClass(HookConstant.bluedEntityA, loader)
        XposedHelpers.findAndHookMethod(HookConstant.discoverSquareFragment, loader, "a", bluedEntityA, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                param!!.args[0] = null
            }
        })
    }

    /**
     * remove GameCenter
     */
    private fun removeGameCenter() {
        XposedHelpers.findAndHookMethod(HookConstant.homePageMore, loader, "a", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                param!!.args[0] = null
            }
        })
        XposedHelpers.findAndHookMethod(HookConstant.homePageMore, loader, "b", List::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                param!!.args[0] = null
            }
        })

    }

    /**
     * remove Money
     */
    private fun removeMoney() {
        XposedHelpers.findAndHookMethod(HookConstant.homePageMore, loader, "b", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val classZ = param!!.thisObject.javaClass
                val nField = classZ.getDeclaredField("o")
                nField.isAccessible = true
                (nField.get(param.thisObject) as View).visibility = GONE

                val qField = classZ.getDeclaredField("r")
                qField.isAccessible = true
                (qField.get(param.thisObject) as View).visibility = GONE
            }
        })
    }
}