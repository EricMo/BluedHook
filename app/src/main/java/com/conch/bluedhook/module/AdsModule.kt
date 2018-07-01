package com.conch.bluedhook.module

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
        removeWelcomeAds()
        removeNearbyAds()
        removeSquareAds()
        removeMoney()
        removeGameCenter()
        removeVisitor()
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
        XposedHelpers.findAndHookMethod(HookConstant.distanceGrid4Adapter, loader, "a", XposedHelpers.findClass(HookConstant.distanceGrid4AdapterHolder, loader), XposedHelpers.findClass(HookConstant.nearByWithAds, loader), Int::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                XposedBridge.log("This item's type is 2,so We do not need to perform this method")
                param!!.result = null
            }
        })
        //List UI
        XposedHelpers.findAndHookMethod(HookConstant.distanceListAdapter, loader, "a", XposedHelpers.findClass(HookConstant.distanceListAdapterHolder, loader), XposedHelpers.findClass(HookConstant.nearByWithAds, loader), Int::class.java, object : XC_MethodHook() {
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
     * ll_charge
     * ll_rich_rank
     */
    private fun removeMoney() {
        XposedHelpers.findAndHookMethod(HookConstant.homePageMore, loader, "c", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val classZ = param!!.thisObject.javaClass
                val nField = classZ.getDeclaredField("p")
                nField.isAccessible = true
                (nField.get(param.thisObject) as View).visibility = GONE

                val qField = classZ.getDeclaredField("s")
                qField.isAccessible = true
                (qField.get(param.thisObject) as View).visibility = GONE
            }
        })
    }

    /**
     * remove visitor
     *
     */
    private fun removeVisitor() {
        XposedHelpers.findAndHookMethod(HookConstant.visitorAdapter, loader, "a", List::class.java, Int::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val data = param!!.args[0] as MutableList<*>
                (data).forEachIndexed { index, it ->
                    if (XposedHelpers.getIntField(it, "is_ads") == 1) {
                        XposedBridge.log("This is ads,So remove it")
                        data.removeAt(index)
                    }
                }
                param.args[0] = data
            }
        })
    }
}