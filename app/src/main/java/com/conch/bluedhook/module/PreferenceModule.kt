package com.conch.bluedhook.module

import android.content.Context
import com.conch.bluedhook.common.HookConstant
import com.conch.bluedhook.common.SettingHelper
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

/**
 * @Description location module
 * @Author Benjamin
 * @CreateDate 2018-02-07 16:43
 **/
class PreferenceModule(loader: ClassLoader, mContext: Context) : BaseModule(loader, mContext) {
    fun modifyPreference() {
        hookNearbyLocation()
    }

    /**
     * change user's location info
     */
    private fun hookNearbyLocation() {
        val classZ = XposedHelpers.findClass(HookConstant.preferences, loader)
        //latitude
        XposedHelpers.findAndHookMethod(classZ, "m", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                if (SettingHelper(mContext).getIsFakeLocation()) {
                    val latitude = SettingHelper(mContext).getLatitude()
                    param?.result = latitude
                }
            }
        })
        //longitude
        XposedHelpers.findAndHookMethod(classZ, "l", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                if (SettingHelper(mContext).getIsFakeLocation()) {
                    val longitude = SettingHelper(mContext).getLongitude()
                    param?.result = longitude
                }
            }
        })

    }
}