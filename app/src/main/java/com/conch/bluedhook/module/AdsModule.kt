package com.conch.bluedhook.module

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import com.conch.bluedhook.common.HookConstant
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

/**
 * Created by Benjamin on 2017/11/23.
 */
class AdsModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun removeAds() {
        removeWelcomeAds()
        //removeNearbyAds()
        //removeSquareAds()
        //removeVipDoor()
        //removeMoney()
        //removeGameCenter()
        //removeVisitor()
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
     *   public static final int fl_ad = 2131624939;
     *   public static final int fl_ads = 2131624627;
     */
    private fun removeNearbyAds() {
        val baseViewHolder = XposedHelpers.findClass(HookConstant.baseViewHolder, loader)
        val userEntity = XposedHelpers.findClass(HookConstant.userFindResult, loader)
        //Grid UI
        XposedHelpers.findAndHookMethod(HookConstant.peopleGridQuickAdapter, loader, "c",
                baseViewHolder, userEntity, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                XposedBridge.log("This item's type is 11,so We do not need to perform this method")
                param!!.result = null
            }
        })
        //List UI
        //The PeopleListQuickAdapter extends PeopleGridQuickAdapter, so we don't need to hook PeopleListQuickAdapter.
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
     * remove vip door
     * ll_vip_bg
     * Maybe somebody needs vip.So we don't need to do this.
     */
    private fun removeVipDoor() {
        XposedHelpers.findAndHookMethod(HookConstant.mineFragment, loader, "j", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val classZ = param!!.thisObject.javaClass
                val nField = classZ.getDeclaredField("y")
                nField.isAccessible = true
                (nField.get(param.thisObject) as View).visibility = GONE
            }
        })
    }

    /**
     * remove GameCenter
     */
    private fun removeGameCenter() {
        XposedHelpers.findAndHookMethod(HookConstant.mineFragment, loader, "b", List::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val classZ = param!!.thisObject.javaClass
                val nField = classZ.getDeclaredField("T")
                nField.isAccessible = true
                val data = (nField.get(param.thisObject) as MutableList<*>)
                //remove game center
                val ext = data.filter {
                    //Because the id will be same,so i choice to use title.this will be wrong in english
                    val id = XposedHelpers.getObjectField(it, "id")
                    val title = XposedHelpers.getObjectField(it, "title")
                    if (title != null) {
                        if (TextUtils.equals("蓝调生活", title as String)) {
                            XposedHelpers.setBooleanField(it, "isLineBig", true)
                        }
                        TextUtils.equals("游戏中心", title)
                    } else {
                        false
                    }
                }
                data.removeAll(ext)
            }
        })
    }

    /**
     * ll_add
     */
    private fun removeMoney() {
        XposedHelpers.findAndHookMethod(HookConstant.mineFragment, loader, "onCreateView",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Bundle::class.java, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                val classZ = param!!.thisObject.javaClass
                val nField = classZ.getDeclaredField("c")
                nField.isAccessible = true
                //(nField.get(param.thisObject) as View).findViewById(2131625267).visibility = View.GONE
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
                val iterator = data.iterator()
                while (iterator.hasNext()) {
                    val item = iterator.next()
                    if (XposedHelpers.getIntField(item, "is_ads") == 1) {
                        XposedBridge.log("This is ads,So remove it")
                        iterator.remove()
                    }
                }
                param.args[0] = data
            }
        })
    }
}